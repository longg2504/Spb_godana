package com.godana.api;

import com.godana.domain.dto.post.*;
import com.godana.domain.entity.Category;
import com.godana.domain.entity.Post;
import com.godana.domain.entity.User;
import com.godana.exception.DataInputException;
import com.godana.service.post.IPostService;
import com.godana.service.user.IUserService;
import com.godana.utils.AppUtils;
import com.godana.utils.ValidateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/post")
public class PostAPI {
    @Autowired
    private IPostService iPostService;
    @Autowired
    private IUserService iUserService;
    @Autowired
    private AppUtils appUtils;
    @Autowired
    private ValidateUtils validateUtils;

    @GetMapping
    public ResponseEntity<?> findAllPost(@RequestParam (defaultValue = "") Category category, Pageable pageable){
        Page<PostDTO> postDTOS = iPostService.findAllByCategory(category, pageable);
        if(postDTOS.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(postDTOS, HttpStatus.OK);
    }

    @GetMapping("/{postId}")
    public ResponseEntity<?> getPostById(@PathVariable("postId") String postIdStr){
        if(!validateUtils.isNumberValid(postIdStr)){
            throw new DataInputException("ID Post không đúng định dạng");
        }
        Long postId = Long.parseLong(postIdStr);

        Optional<Post> postOptional = iPostService.findById(postId);
        if(!postOptional.isPresent()){
            throw new DataInputException("Bài Post này không có xin vui lòng xem lại");
        }
        Post post = postOptional.get();
        PostDTO postDTO = post.toPostDTO(post.getLikes().size(), post.getComments().size(), post.getCreatedAt());
        return new ResponseEntity<>(postDTO, HttpStatus.OK);
    }

    @GetMapping("/get_all_post_by_user/{userId}")
    public ResponseEntity<?> getPostByUserId(@PathVariable("userId") String userIdStr){
        if(!validateUtils.isNumberValid(userIdStr)){
            throw new DataInputException("ID User không đúng định dạng");
        }

        Long userId = Long.parseLong(userIdStr);
        Optional<User> userOptional = iUserService.findById(userId);
        if(!userOptional.isPresent()){
            throw new DataInputException("User này không tồn tại");
        }
        List<Post> postList = iPostService.findAllByUserIdAndDeleted(userId, false);
        List<PostDTO> postDTOS = new ArrayList<>();
        for(Post item : postList){
            postDTOS.add(item.toPostDTO(item.getLikes().size(), item.getComments().size(), item.getCreatedAt()));
        }

        return new ResponseEntity<>(postDTOS, HttpStatus.OK);
    }



    @PostMapping
    public ResponseEntity<?> createPost(@ModelAttribute PostCreReqDTO postCreReqDTO){
        PostCreResDTO postCreResDTO = iPostService.createPost(postCreReqDTO);
        return new ResponseEntity<>(postCreResDTO, HttpStatus.OK);
    }

    @PostMapping("/{postId}")
    public ResponseEntity<?> updatePost(@PathVariable("postId") String postIdStr, @ModelAttribute PostUpReqDTO postUpReqDTO) {
        if(!validateUtils.isNumberValid(postIdStr)) {
            throw new DataInputException("Mã bài viết không hợp lệ vui lòng xem lại !!!");
        }
        Long postId = Long.parseLong(postIdStr);
        PostUpResDTO postUpResDTO = iPostService.updatePost(postUpReqDTO, postId);

        return new ResponseEntity<>(postUpResDTO, HttpStatus.OK);
    }

    @PostMapping("/deleted/{postId}")
    public ResponseEntity<?> deletedPost(@PathVariable("postId") String postIdStr){
        if(!validateUtils.isNumberValid(postIdStr)){
            throw new DataInputException("Mã bài viết không hợp lệ vui lòng xem lại !!!");
        }

        Long postId = Long.parseLong(postIdStr);
        Optional<Post> postOptional = iPostService.findById(postId);
        if(!postOptional.isPresent()){
            throw new DataInputException("Bài viết này không tồn tại");
        }
        Post post = postOptional.get();
        iPostService.delete(post);

        return new ResponseEntity<>(HttpStatus.OK);
    }

}
