package com.godana.api;

import com.godana.domain.dto.post.PostCreReqDTO;
import com.godana.domain.dto.post.PostCreResDTO;
import com.godana.service.post.IPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/post")
public class PostAPI {
    @Autowired
    private IPostService postService;


    @PostMapping
    public ResponseEntity<?> createPost(@ModelAttribute PostCreReqDTO postCreReqDTO){
        PostCreResDTO postCreResDTO = postService.createPost(postCreReqDTO);
        return new ResponseEntity<>(postCreResDTO, HttpStatus.OK);
    }
}
