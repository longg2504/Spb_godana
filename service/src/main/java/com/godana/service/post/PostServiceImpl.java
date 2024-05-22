package com.godana.service.post;

import com.godana.domain.dto.placeAvatar.PlaceAvatarDTO;
import com.godana.domain.dto.post.*;
import com.godana.domain.dto.postAvatar.PostAvatarResDTO;
import com.godana.domain.entity.*;
import com.godana.exception.DataInputException;
import com.godana.repository.comment.CommentRepository;
import com.godana.repository.like.LikeRepository;
import com.godana.repository.postAvatar.PostAvatarRepository;
import com.godana.repository.category.CategoryRepository;
import com.godana.repository.post.PostRepository;
import com.godana.repository.user.UserRepository;
import com.godana.service.upload.IUploadService;
import com.godana.utils.UploadUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

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
    @Autowired
    private LikeRepository likeRepository;
    @Autowired
    private CommentRepository commentRepository;
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
        List<PostAvatar> postAvatars = postAvatarRepository.findAllByPost(post);
        if(!postAvatars.isEmpty()){
            for(PostAvatar item : postAvatars){
                item.setDeleted(true);
                postAvatarRepository.save(item);
            }
        }
        List<Like> likeList = likeRepository.findAllByPost(post);
        if(!likeList.isEmpty()){
            for(Like item : likeList){
                item.setDeleted(true);
                likeRepository.save(item);
            }
        }

        List<Comment> commentList = commentRepository.findAllByPost(post);
        if(!commentList.isEmpty()){
            for(Comment item : commentList){
                item.setDeleted(true);
                commentRepository.save(item);
            }
        }

        post.setDeleted(true);
        postRepository.save(post);
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
    public Optional<PostDTO> findAllByPostId(Long postId) {
        return postRepository.findAllByPostId(postId);
    }

    @Override
    public List<Post> findAllByUserIdAndDeleted(Long userId, boolean deleted) {
        return postRepository.findAllByUserIdAndDeleted(userId, false, Sort.by(Sort.Direction.DESC, "createdAt"));
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
        Optional<Post> postOptional = postRepository.findById(postId);
        if (!postOptional.isPresent()) {
            throw new DataInputException("Mã bài viết muốn sữa không tồn tại vui lòng xem lại !!!");
        }
        Optional<Post> optionalPost = postRepository.findById(postId);
        Post post = optionalPost.get();

        if (optionalPost.isPresent()) {
            new DataInputException("Bài post muốn sửa không tồn tại vui lòng xem lại");
        }

        Long categoryId = postUpReqDTO.getCategoryId();
        Optional<Category> categoryOptional = categoryRepository.findById(categoryId);
        Category category = categoryOptional.get();

        post.setPostTitle(postUpReqDTO.getPostTitle());
        post.setContent(postUpReqDTO.getContent());
        post.setCategory(category);
        postRepository.save(post);

        List<String> newImageIds = postUpReqDTO.getListIdAvatarCurrrent();
        List<PostAvatar> existingAvatars = postAvatarRepository.findAllByPost(post);
        List<PostAvatar> avatarsToDelete = existingAvatars.stream()
                .filter(avatar -> !newImageIds.contains(avatar.getId()))
                .collect(Collectors.toList());
        postAvatarRepository.deleteAll(avatarsToDelete);


        if (postUpReqDTO.getImages() == null) {
            List<PostAvatar> updatedAvatars = postAvatarRepository.findAllByPost(post);
            return post.toPostUpResDTO(updatedAvatars);
        }
        else {
            for (MultipartFile image : postUpReqDTO.getImages()) {
                PostAvatar postAvatar = new PostAvatar();
                postAvatar.setPost(post);
                postAvatar.setHeight(600);
                postAvatar.setWidth(600);
                postAvatar = postAvatarRepository.save(postAvatar);
                uploadAndSavePostImage(image, postAvatar);
            }
            List<PostAvatar> updatedAvatars = postAvatarRepository.findAllByPost(post);
            return post.toPostUpResDTO(updatedAvatars);

        }



//        if (postUpReqDTO.getImages() == null) {
//            PostUpResDTO postUpResDTO = post.toPostUpResDTO(post.getPostImages());
//            return postUpResDTO;
//        } else {
//            List<PostAvatar> postAvatars = postAvatarRepository.findAllByPost(post);
//            postAvatarRepository.deleteAll(postAvatars);
//
//            List<PostAvatar> postAvatarList = new ArrayList<>();
//            for (MultipartFile image : postUpReqDTO.getImages()) {
//                PostAvatar postAvatar = new PostAvatar();
//                postAvatar.setPost(post);
//                postAvatar.setHeight(600);
//                postAvatar.setWidth(600);
//                postAvatar = postAvatarRepository.save(postAvatar);
//                uploadAndSavePostImage(image, postAvatar);
//
//                postAvatarList.add(postAvatar);
//            }
//            PostUpResDTO postUpResDTO = post.toPostUpResDTO(postAvatarList);
//            return postUpResDTO;
//        }
    }

    @Override
    public PostCountDTO countPost() {
        return postRepository.countPost();
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
