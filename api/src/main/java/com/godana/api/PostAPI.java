package com.godana.api;

import com.godana.domain.dto.post.PostCreReqDTO;
import com.godana.domain.dto.post.PostCreResDTO;
import com.godana.domain.dto.post.PostUpReqDTO;
import com.godana.domain.dto.post.PostUpResDTO;
import com.godana.domain.entity.Post;
import com.godana.domain.entity.User;
import com.godana.exception.DataInputException;
import com.godana.service.post.IPostService;
import com.godana.service.user.IUserService;
import com.godana.utils.AppUtils;
import com.godana.utils.ValidateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("api/post")
public class PostAPI {
    @Autowired
    private IPostService iPostService;
    @Autowired
    private IUserService iUserService;
    @Autowired
    private AppUtils appUtils;
    @Autowired
    private ValidateUtils validateUtils;


    @PostMapping
    public ResponseEntity<?> createPost(@ModelAttribute PostCreReqDTO postCreReqDTO){
        PostCreResDTO postCreResDTO = iPostService.createPost(postCreReqDTO);
        return new ResponseEntity<>(postCreResDTO, HttpStatus.OK);
    }

    @PatchMapping("/{postId}")
    public ResponseEntity<?> updatePost(@PathVariable("postId") String postIdStr, PostUpReqDTO postUpReqDTO) {
        if(!validateUtils.isNumberValid(postIdStr)) {
            throw new DataInputException("Mã bài viết không hợp lệ vui lòng xem lại !!!");
        }
        Long postId = Long.parseLong(postIdStr);
        Optional<Post> postOptional =  iPostService.findById(postId);
        if(!postOptional.isPresent()){
            throw new DataInputException("Mã bài viết muốn sữa không tồn tại vui lòng xem lại !!!");
        }
        PostUpResDTO postUpResDTO = iPostService.updatePost(postUpReqDTO, postId);

        return new ResponseEntity<>(postUpResDTO, HttpStatus.OK);
    }




}
