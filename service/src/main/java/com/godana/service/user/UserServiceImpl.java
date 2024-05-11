package com.godana.service.user;

import com.godana.domain.dto.user.UserCountDTO;
import com.godana.domain.dto.user.UserRegisterReqDTO;
import com.godana.domain.dto.user.UserReqUpDTO;
import com.godana.domain.dto.user.UserResDTO;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    public Boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
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
        Optional<Role> roleOptional = roleRepository.findById(2L);
        if (userRegisterReqDTO.getUserAvatar() == null){
            User user = userRegisterReqDTO.toUser(roleOptional.get() ,EUserStatus.OFFLINE);
            user.setPassword(passwordEncoder.encode(userRegisterReqDTO.getPassword()));
            return userRepository.save(user);
        }
        else {
            MultipartFile file = userRegisterReqDTO.getUserAvatar();
            UserAvatar userAvatar = new UserAvatar();
            userAvatar.setHeight(600);
            userAvatar.setWidth(600);
            userAvatar = userAvatarRepository.save(userAvatar);
            uploadAndSaveUserImage(file, userAvatar);
            User user = userRegisterReqDTO.toUser(roleOptional.get(), userAvatar, EUserStatus.OFFLINE);
            user.setPassword(passwordEncoder.encode(userRegisterReqDTO.getPassword()));
            return userRepository.save(user);
        }
    }

    @Override
    public User update(Long userId, UserReqUpDTO userReqUpDTO) {
        Optional<User> userOptional = iUserService.findById(userId);
        if(!userOptional.isPresent()) {
            throw new DataInputException("User này không tồn tại");
        }

        User user = userOptional.get();

        if(userReqUpDTO.getAvatar() == null){
            user.setEmail(userReqUpDTO.getEmail());
            user.setFullName(userReqUpDTO.getFullname());

            return user = save(user);
        }
        else {

            if(user.getUserAvatar() == null) {
                MultipartFile file = userReqUpDTO.getAvatar();
                UserAvatar userAvatar = new UserAvatar();
                userAvatar.setHeight(600);
                userAvatar.setWidth(600);
                userAvatar = userAvatarRepository.save(userAvatar);
                uploadAndSaveUserImage(file, userAvatar);

                user.setEmail(userReqUpDTO.getEmail());
                user.setFullName(userReqUpDTO.getFullname());
                user.setUserAvatar(userAvatar);
                return user = save(user);
            }
            else {
                Optional<UserAvatar> userAvatarOptional = userAvatarRepository.findById(user.getUserAvatar().getId());
                userAvatarRepository.delete(userAvatarOptional.get());
                MultipartFile file = userReqUpDTO.getAvatar();
                UserAvatar userAvatar = new UserAvatar();
                userAvatar.setHeight(600);
                userAvatar.setWidth(600);
                userAvatar = userAvatarRepository.save(userAvatar);
                uploadAndSaveUserImage(file, userAvatar);

                user.setEmail(userReqUpDTO.getEmail());
                user.setFullName(userReqUpDTO.getFullname());
                user.setUserAvatar(userAvatar);
                return user = save(user);
            }


        }
    }

    @Override
    public Page<UserResDTO> findUserBySearch(String search, Pageable pageable) {
        return userRepository.findUserBySearch(search, pageable);
    }

    @Override
    public Page<UserResDTO> findUserBanBySearch(String search, Pageable pageable) {
        return userRepository.findUserBanBySearch(search, pageable);
    }

    @Override
    public UserCountDTO countUser() {
        return userRepository.countUser();
    }

    @Override
    public void unban(User user) {
        user.setDeleted(false);
        userRepository.save(user);
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
        return userRepository.save(user);
    }

    @Override
    public void delete(User user) {
        user.setDeleted(true);
        userRepository.save(user);
    }

    @Override
    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }


}
