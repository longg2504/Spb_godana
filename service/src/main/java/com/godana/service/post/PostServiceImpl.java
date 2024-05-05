package com.godana.service.post;

import com.godana.domain.dto.placeAvatar.PlaceAvatarDTO;
import com.godana.domain.dto.post.*;
import com.godana.domain.dto.postAvatar.PostAvatarResDTO;
import com.godana.domain.entity.*;
import com.godana.exception.DataInputException;
import com.godana.repository.postAvatar.PostAvatarRepository;
import com.godana.repository.category.CategoryRepository;
import com.godana.repository.post.PostRepository;
import com.godana.repository.user.UserRepository;
import com.godana.service.upload.IUploadService;
import com.godana.utils.UploadUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.*;

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
    private PostAvatarRepository postAvatarRepository;
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
    public Page<PostDTO> findAllByCategory(Category category, Pageable pageable) {
        Page<PostDTO> postDTOS = postRepository.findAllByCategory(category, pageable);
        List<PostAvatarResDTO> postAvatarResDTOS = new ArrayList<>();
        for(PostDTO item : postDTOS){
            Long postId = item.getId();
            Optional<Post> postOptional = postRepository.findById(postId);
            List<PostAvatar> postAvatars = postAvatarRepository.findAllByPost(postOptional.get());
            postAvatarResDTOS = toAvatarDTOList(postAvatars);
            item.setPostAvatar(postAvatarResDTOS);
            item.setLike(item.getLike());
            item.setComment(item.getComment());
        }
        return postDTOS;
    }

    @Override
    public PostCreResDTO createPost(PostCreReqDTO postCreReqDTO) {
        Optional<User> user = userRepository.findById(postCreReqDTO.getUserId());
        Optional<Category> category = categoryRepository.findById(postCreReqDTO.getCategoryId());

        Post post = new Post()
                .setPostTitle(postCreReqDTO.getPostTitle())
                .setContent(postCreReqDTO.getContent())
                .setUser(user.get())
                .setCategory(category.get());
        post = postRepository.save(post);
        PostCreResDTO postResDTO = new PostCreResDTO();
        if(postCreReqDTO.getImages() == null) {
            postResDTO = post.toPostCreResDTO();
        }
        else {
                List<PostAvatar> postAvatars = new ArrayList<>();
            for (MultipartFile image : postCreReqDTO.getImages()) {
                // Lưu ảnh vào hệ thống file
                // avatar.setImageUrl(savedImageUrl);

                PostAvatar postAvatar = new PostAvatar();

                postAvatar.setPost(post);
                postAvatar.setWidth(600);
                postAvatar.setHeight(600);
                postAvatar = postAvatarRepository.save(postAvatar);

                uploadAndSavePostImage(image, postAvatar);

                // Đặt các thuộc tính của avatarResDTO từ avatar
//            avatars.add(avatar.toAvatarResDTO());
                postAvatars.add(postAvatar);
        }

            postResDTO = post.toPostCreResDTO(postAvatars);;
        }
        return postResDTO;
    }

    @Override
    public PostUpResDTO updatePost(PostUpReqDTO postUpReqDTO, Long postId) {
        Optional<Post> optionalPost = postRepository.findById(postId);
        Post post = optionalPost.get();
        if(optionalPost.isPresent()) {
            new DataInputException("Bài post muốn sửa không tồn tại vui lòng xem lại");
        }
        Long userId = optionalPost.get().getUser().getId();
        if(!userId.equals(postUpReqDTO.getUserId())){
            new DataInputException("Bài post này không phải của bạn không thể sửa đổi");
        }
        post.setPostTitle(postUpReqDTO.getPostTitle());
        post.setContent(postUpReqDTO.getContent());
        PostUpResDTO postUpResDTO = post.toPostUpResDTO(post.getPostImages());
        return postUpResDTO;
    }

    public void uploadAndSavePostImage(MultipartFile image, PostAvatar postAvatar) {

        try {
            Map mapList =  iUploadService.uploadImage(image, uploadUtils.buildImageUploadParams(postAvatar));

            String fileUrl = (String) mapList.get("secure_url");
            String fileFormat = (String) mapList.get("format");
            int width = (int) mapList.get("width");
            int height = (int) mapList.get("height");

            postAvatar.setFileName(postAvatar.getId() + "." + fileFormat);
            postAvatar.setFileUrl(fileUrl);
            postAvatar.setFileFolder(UploadUtils.IMAGE_UPLOAD_FOLDER);
            postAvatar.setCloudId(postAvatar.getFileFolder() + "/" + postAvatar.getId());
            postAvatar.setWidth(width);
            postAvatar.setHeight(height);
            postAvatarRepository.save(postAvatar);

        } catch (IOException e) {
            e.printStackTrace();
            throw new DataInputException("Upload hình ảnh thất bại");
        }
    }

    public void uploadAndSaveProductImages(PostCreReqDTO postCreReqDTOList, PostAvatar postAvatar) {
        List<MultipartFile> images = postCreReqDTOList.getImages();

        try {
            List<Map> mapList =  iUploadService.uploadImages(images, uploadUtils.buildImageUploadParams(postAvatar));

            for (Map item : mapList) {
                String fileUrl = (String) item.get("secure_url");
                String fileFormat = (String) item.get("format");

                postAvatar.setFileName(postAvatar.getId() + "." + fileFormat);
                postAvatar.setFileUrl(fileUrl);
                postAvatar.setFileFolder(UploadUtils.IMAGE_UPLOAD_FOLDER);
                postAvatar.setCloudId(postAvatar.getFileFolder() + "/" + postAvatar.getId());
                postAvatarRepository.save(postAvatar);
            }

        } catch (IOException e) {
            e.printStackTrace();
            throw new DataInputException("Upload hình ảnh thất bại");
        }
    }

    public List<PostAvatarResDTO> toAvatarDTOList(List<PostAvatar> postAvatars){
        List<PostAvatarResDTO> dtoList = new ArrayList<>();
        for (PostAvatar postAvatar : postAvatars) {
            dtoList.add(postAvatar.toAvatarResDTO());
        }
        return dtoList;
    }
}
