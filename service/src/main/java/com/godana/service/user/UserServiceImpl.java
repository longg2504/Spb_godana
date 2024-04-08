package com.godana.service.user;

import com.godana.domain.dto.user.UserRegisterReqDTO;
import com.godana.domain.entity.*;
import com.godana.domain.enums.ERole;
import com.godana.domain.enums.EUserStatus;
import com.godana.exception.DataInputException;
import com.godana.repository.role.RoleRepository;
import com.godana.repository.user.UserRepository;
import com.godana.repository.userAvatar.UserAvatarRepository;
import com.godana.service.upload.IUploadService;
import com.godana.utils.UploadUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@Transactional
public class UserServiceImpl implements IUserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private UploadUtils uploadUtils;
    @Autowired
    private IUploadService iUploadService;
    @Autowired
    private UserAvatarRepository userAvatarRepository;
    @Autowired
    private IUserService iUserService;


    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public Boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    @Override
    public User getByUsername(String username) {
        return userRepository.getByUsername(username);
    }

    @Override
    public Optional<User> findByName(String userName) {
        return userRepository.findByUsername(userName);
    }

    @Override
    public User create(UserRegisterReqDTO userRegisterReqDTO) {
        User user = new User();
        Optional<Role> roleOptional = roleRepository.findById(1L);
        if(userRegisterReqDTO.getUserAvatar() == null){
            user = userRepository.save(userRegisterReqDTO.toUser(roleOptional.get(), EUserStatus.OFFLINE));
        }
        MultipartFile file = userRegisterReqDTO.getUserAvatar();
        UserAvatar userAvatar = new UserAvatar();
        userAvatar = userAvatarRepository.save(userAvatar);
        uploadAndSaveUserImage(file, userAvatar);
        user = iUserService.save(userRegisterReqDTO.toUser(roleOptional.get(),userAvatar, EUserStatus.OFFLINE));
        return user;
    }

    public void uploadAndSaveUserImage(MultipartFile image, UserAvatar userAvatar) {

        try {
            Map mapList = iUploadService.uploadImage(image, uploadUtils.buildImageUploadParamsUser(userAvatar));

            String fileUrl = (String) mapList.get("secure_url");
            String fileFormat = (String) mapList.get("format");
            int width = (int) mapList.get("width");
            int height = (int) mapList.get("height");

            userAvatar.setFileName(userAvatar.getId() + "." + fileFormat);
            userAvatar.setFileUrl(fileUrl);
            userAvatar.setFileFolder(UploadUtils.IMAGE_UPLOAD_FOLDER_USER);
            userAvatar.setCloudId(userAvatar.getFileFolder() + "/" + userAvatar.getId());
            userAvatar.setWidth(width);
            userAvatar.setHeight(height);
            userAvatarRepository.save(userAvatar);

        } catch (IOException e) {
            e.printStackTrace();
            throw new DataInputException("Upload hình ảnh thất bại");
        }
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> userOptional = userRepository.findByUsername(username);
        if (!userOptional.isPresent()) {
            throw new UsernameNotFoundException(username);
        }
        return UserPrinciple.build(userOptional.get());
    }

    @Override
    public User save(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public void delete(User user) {
        userRepository.delete(user);
    }

    @Override
    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }


}
