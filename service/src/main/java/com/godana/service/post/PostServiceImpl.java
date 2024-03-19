package com.godana.service.post;

import com.godana.domain.dto.avatar.AvatarResDTO;
import com.godana.domain.dto.post.PostCreReqDTO;
import com.godana.domain.dto.post.PostCreResDTO;
import com.godana.domain.entity.Avatar;
import com.godana.domain.entity.Category;
import com.godana.domain.entity.Post;
import com.godana.domain.entity.User;
import com.godana.exception.DataInputException;
import com.godana.repository.avatar.AvatarRepository;
import com.godana.repository.category.CategoryRepository;
import com.godana.repository.post.PostRepository;
import com.godana.repository.user.UserRepository;
import com.godana.service.upload.IUploadService;
import com.godana.utils.UploadUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Transactional
@Service
public class PostServiceImpl implements IPostService{
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private AvatarRepository avatarRepository;
    @Autowired
    private UploadUtils uploadUtils;
    @Autowired
    private IUploadService iUploadService;
    @Override
    public List<Post> findAll() {
        return postRepository.findAll();
    }

    @Override
    public Optional<Post> findById(Long id) {
        return postRepository.findById(id);
    }

    @Override
    public Post save(Post post) {
        return postRepository.save(post);
    }

    @Override
    public void delete(Post post) {
        postRepository.delete(post);
    }

    @Override
    public void deleteById(Long id) {
        postRepository.deleteById(id);
    }

    @Override
    public PostCreResDTO createPost(PostCreReqDTO postCreReqDTO) {

        Optional<User> user = userRepository.findById(postCreReqDTO.getUserId());
        Optional<Category> category = categoryRepository.findById(postCreReqDTO.getCategoryId());

        User user1 = user.get();
        Category category1 = category.get();

        Post post = new Post()
                .setPostTitle(postCreReqDTO.getPostTitle())
                .setContent(postCreReqDTO.getContent())
                .setUser(user1)
                .setCategory(category1);
        post = postRepository.save(post);


        List<Avatar> avatars = new ArrayList<>();
        for (MultipartFile image : postCreReqDTO.getImages()) {
            // Lưu ảnh vào hệ thống file
            // avatar.setImageUrl(savedImageUrl);

            Avatar avatar = new Avatar();

            avatar.setPost(post);
            avatar = avatarRepository.save(avatar);

            uploadAndSaveProductImage(image, avatar);

            // Đặt các thuộc tính của avatarResDTO từ avatar
//            avatars.add(avatar.toAvatarResDTO());
            avatars.add(avatar);
        }

        PostCreResDTO postResDTO = post.toPostCreResDTO(avatars);;
//        postResDTO.setPostImages(avatars);

        return postResDTO;
    }

    public void uploadAndSaveProductImage(MultipartFile image, Avatar avatar) {

        try {
            Map mapList =  iUploadService.uploadImage(image, uploadUtils.buildImageUploadParams(avatar));

            String fileUrl = (String) mapList.get("secure_url");
            String fileFormat = (String) mapList.get("format");
            int width = (int) mapList.get("width");
            int height = (int) mapList.get("height");

            avatar.setFileName(avatar.getId() + "." + fileFormat);
            avatar.setFileUrl(fileUrl);
            avatar.setFileFolder(UploadUtils.IMAGE_UPLOAD_FOLDER);
            avatar.setCloudId(avatar.getFileFolder() + "/" + avatar.getId());
            avatar.setWidth(width);
            avatar.setHeight(height);
            avatarRepository.save(avatar);

        } catch (IOException e) {
            e.printStackTrace();
            throw new DataInputException("Upload hình ảnh thất bại");
        }
    }

    public void uploadAndSaveProductImages(PostCreReqDTO postCreReqDTOList, Avatar avatar) {
        List<MultipartFile> images = postCreReqDTOList.getImages();

        try {
            List<Map> mapList =  iUploadService.uploadImages(images, uploadUtils.buildImageUploadParams(avatar));

            for (Map item : mapList) {
                String fileUrl = (String) item.get("secure_url");
                String fileFormat = (String) item.get("format");

                avatar.setFileName(avatar.getId() + "." + fileFormat);
                avatar.setFileUrl(fileUrl);
                avatar.setFileFolder(UploadUtils.IMAGE_UPLOAD_FOLDER);
                avatar.setCloudId(avatar.getFileFolder() + "/" + avatar.getId());
                avatarRepository.save(avatar);
            }

        } catch (IOException e) {
            e.printStackTrace();
            throw new DataInputException("Upload hình ảnh thất bại");
        }
    }
}
