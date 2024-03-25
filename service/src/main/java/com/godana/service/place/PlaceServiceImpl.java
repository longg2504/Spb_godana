package com.godana.service.place;

import com.godana.domain.dto.place.PlaceCreReqDTO;
import com.godana.domain.dto.place.PlaceCreResDTO;
import com.godana.domain.entity.*;
import com.godana.domain.enums.EPlaceStatus;
import com.godana.exception.DataInputException;
import com.godana.repository.category.CategoryRepository;
import com.godana.repository.contact.ContactRepository;
import com.godana.repository.locationRegion.LocationRegionRepository;
import com.godana.repository.place.PlaceRepository;
import com.godana.repository.placeAvatar.PlaceAvatarRepository;
import com.godana.repository.user.UserRepository;
import com.godana.service.upload.IUploadService;
import com.godana.utils.UploadUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import javax.xml.crypto.Data;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Transactional
@Service
public class PlaceServiceImpl implements IPlaceService {
    @Autowired
    private PlaceRepository placeRepository;
    @Autowired
    private LocationRegionRepository locationRegionRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private ContactRepository contactRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PlaceAvatarRepository placeAvatarRepository;
    @Autowired
    private UploadUtils uploadUtils;
    @Autowired
    private IUploadService iUploadService;

    @Override
    public List<Place> findAll() {
        return placeRepository.findAll();
    }

    @Override
    public Optional<Place> findById(Long id) {
        return placeRepository.findById(id);
    }

    @Override
    public Place save(Place place) {
        return placeRepository.save(place);
    }

    @Override
    public void delete(Place place) {
        placeRepository.delete(place);
    }

    @Override
    public void deleteById(Long id) {
        placeRepository.deleteById(id);
    }

    @Override
    public PlaceCreResDTO create(PlaceCreReqDTO placeCreReqDTO) {
        LocationRegion locationRegion = placeCreReqDTO.toLocationRegion();
        locationRegionRepository.save(locationRegion);

        Optional<Category> categoryOptional = categoryRepository.findById(placeCreReqDTO.getCategoryId());
        if (categoryOptional.isPresent()) {
            new DataInputException(("Category không tồn tại vui lòng xem lại !!!"));
        }
        Category category = categoryOptional.get();
        Optional<User> userOptional = userRepository.findById(placeCreReqDTO.getUserId());
        if (userOptional.isPresent()) {
            new DataInputException(("User không tồn tại vui lòng xem lại !!!"));
        }
        User user = userOptional.get();
        Place place = new Place()
                .setTitle(placeCreReqDTO.getPlaceTitle())
                .setContent(placeCreReqDTO.getContent())
                .setLongitude(placeCreReqDTO.getLongitude())
                .setLatitude(placeCreReqDTO.getLatitude())
                .setCategory(category)
                .setLocationRegion(locationRegion)
                .setUser(user)
                .setStatus(EPlaceStatus.DONE);
        place = placeRepository.save(place);
        Contact contact = placeCreReqDTO.toContact(place);
        contact = contactRepository.save(contact);
        PlaceCreResDTO placeCreResDTO = new PlaceCreResDTO();
        if (placeCreReqDTO.getPlaceAvatar() == null) {
            placeCreResDTO = place.toPlaceCreResDTO();
        } else {
            List<PlaceAvatar> placeAvatars = new ArrayList<>();
            for (MultipartFile image : placeCreReqDTO.getPlaceAvatar()) {
                // Lưu ảnh vào hệ thống file
                // avatar.setImageUrl(savedImageUrl);

                PlaceAvatar placeAvatar = new PlaceAvatar();

                placeAvatar.setPlace(place);
                placeAvatar = placeAvatarRepository.save(placeAvatar);

                uploadAndSavePlaceImage(image, placeAvatar);

                // Đặt các thuộc tính của avatarResDTO từ avatar
//            avatars.add(avatar.toAvatarResDTO());
                placeAvatars.add(placeAvatar);
            }
            placeCreResDTO = place.toPlaceCreResDTO(placeAvatars);
        }
        placeCreResDTO.setContact(contact.toContactDTO());
        return placeCreResDTO;

    }

    public void uploadAndSavePlaceImage(MultipartFile image, PlaceAvatar placeAvatar) {

        try {
            Map mapList = iUploadService.uploadImage(image, uploadUtils.buildImageUploadParamsPlace(placeAvatar));

            String fileUrl = (String) mapList.get("secure_url");
            String fileFormat = (String) mapList.get("format");
            int width = (int) mapList.get("width");
            int height = (int) mapList.get("height");

            placeAvatar.setFileName(placeAvatar.getId() + "." + fileFormat);
            placeAvatar.setFileUrl(fileUrl);
            placeAvatar.setFileFolder(UploadUtils.IMAGE_UPLOAD_FOLDER_PLACE);
            placeAvatar.setCloudId(placeAvatar.getFileFolder() + "/" + placeAvatar.getId());
            placeAvatar.setWidth(width);
            placeAvatar.setHeight(height);
            placeAvatarRepository.save(placeAvatar);

        } catch (IOException e) {
            e.printStackTrace();
            throw new DataInputException("Upload hình ảnh thất bại");
        }
    }
}
