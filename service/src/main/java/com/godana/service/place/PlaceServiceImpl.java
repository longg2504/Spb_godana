package com.godana.service.place;

import com.godana.domain.dto.place.*;
import com.godana.domain.dto.placeAvatar.PlaceAvatarDTO;
import com.godana.domain.entity.*;
import com.godana.domain.enums.EPlaceStatus;
import com.godana.exception.DataInputException;
import com.godana.repository.category.CategoryRepository;
import com.godana.repository.contact.ContactRepository;
import com.godana.repository.locationRegion.LocationRegionRepository;
import com.godana.repository.place.PlaceRepository;
import com.godana.repository.placeAvatar.PlaceAvatarRepository;
import com.godana.repository.user.UserRepository;
import com.godana.service.category.ICategoryService;
import com.godana.service.placeAvatar.IPlaceAvatarService;
import com.godana.service.upload.IUploadService;
import com.godana.utils.UploadUtils;
import com.godana.utils.ValidateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    @Autowired
    private ValidateUtils validateUtils;
    @Autowired
    private IPlaceService iPlaceService;
    @Autowired
    private ICategoryService iCategoryService;

    @Override
    public List<Place> findAll() {
        return placeRepository.findAll();
    }


    @Override
    public Optional<PlaceDTO> findPlaceById(Long id) {
        return placeRepository.findPlaceById(id);
    }

    @Override
    public Page<PlaceDTO> findAllByCategoryAndSearch(Category category, String search,Pageable pageable) {
        Page<PlaceDTO> placeDTOS = placeRepository.findAllByCategoryAndSearch(category, search,pageable);
        List<PlaceAvatarDTO> placeAvatarDTOS = new ArrayList<>();
        for(PlaceDTO placeDTO : placeDTOS){
            Long placeId = placeDTO.getId();
            Optional<Place> placeOptional = placeRepository.findById(placeId);
            List<PlaceAvatar> placeAvatars = placeAvatarRepository.findAllByPlace(placeOptional.get());
            placeAvatarDTOS = toAvatarDTOList(placeAvatars);

            placeDTO.setPlaceAvatar(placeAvatarDTOS);

        }

        return placeDTOS;
    }

    @Override
    public Optional<Place> findPlaceByIdAndDeletedFalse(Long id) {
        return placeRepository.findPlaceByIdAndDeletedFalse(id);
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
        Contact contact = placeCreReqDTO.toContact();
        contact = contactRepository.save(contact);
        Place place = new Place()
                .setTitle(placeCreReqDTO.getPlaceTitle())
                .setContent(placeCreReqDTO.getContent())
                .setLongitude(placeCreReqDTO.getLongitude())
                .setLatitude(placeCreReqDTO.getLatitude())
                .setCategory(category)
                .setLocationRegion(locationRegion)
                .setUser(user)
                .setContact(contact)
                .setStatus(EPlaceStatus.DONE);
        place = placeRepository.save(place);

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

    @Override
    public PlaceUpResDTO update(String placeIdStr, PlaceUpReqDTO placeUpReqDTO) {
        if (!validateUtils.isNumberValid(placeIdStr)) {
            throw new DataInputException("Mã địa điểm không hợp lệ !!!");
        }

        Long placeId = Long.parseLong(placeIdStr);
        Optional<Place> optionalPlace = iPlaceService.findPlaceByIdAndDeletedFalse(placeId);
        if (!optionalPlace.isPresent()) {
            throw new DataInputException("Địa điểm này không tồn tại !!!");
        }
        Optional<Category> optionalCategory = iCategoryService.findByIdAndDeletedFalse(placeUpReqDTO.getCategoryId());
        if (!optionalCategory.isPresent()) {
            throw new DataInputException("Category này không tồn tại !!!");
        }
        Optional<User> userOptional = userRepository.findById(placeUpReqDTO.getUserId());
        if (userOptional.isPresent()) {
            new DataInputException(("User không tồn tại vui lòng xem lại !!!"));
        }
        User user = userOptional.get();

        Category category = optionalCategory.get();

        Long locationRegionId = optionalPlace.get().getLocationRegion().getId();
        LocationRegion locationRegion = placeUpReqDTO.toLocationRegion(locationRegionId);
        locationRegion = locationRegionRepository.save(locationRegion);

        Long contactId = optionalPlace.get().getContact().getId();
        Contact contact = placeUpReqDTO.toContact(contactId);
        contact = contactRepository.save(contact);

        Place place = placeUpReqDTO.toPlace(placeId, category, locationRegion, user, contact);
        place.setStatus(optionalPlace.get().getStatus());
        PlaceUpResDTO placeUpResDTO = new PlaceUpResDTO();
        if (placeUpReqDTO.getPlaceAvatar() == null) {
            place.setPlaceAvatarList(optionalPlace.get().getPlaceAvatarList());
            place = placeRepository.save(place);
            placeUpResDTO = place.toPlaceUpResDTO();

        } else {
            List<PlaceAvatar> placeAvatars = new ArrayList<>();
            for (MultipartFile image : placeUpReqDTO.getPlaceAvatar()) {
                PlaceAvatar placeAvatar = new PlaceAvatar();
                placeAvatar.setPlace(place);
                placeAvatar = placeAvatarRepository.save(placeAvatar);
                uploadAndSavePlaceImage(image, placeAvatar);

                placeAvatars.add(placeAvatar);
            }
            placeUpResDTO = place.toPlaceUpResDTO(placeAvatars);
        }
        return placeUpResDTO;
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

    public List<PlaceAvatarDTO> toAvatarDTOList(List<PlaceAvatar> placeAvatars){
        List<PlaceAvatarDTO> dtoList = new ArrayList<>();
        for (PlaceAvatar placeAvatar : placeAvatars) {
            dtoList.add(placeAvatar.toPlaceAvatarDTO());
        }
        return dtoList;
    }
}
