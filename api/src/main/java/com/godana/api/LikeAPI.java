package com.godana.api;

import com.godana.domain.dto.like.LikeDTO;
import com.godana.domain.dto.like.LikeReqDTO;
import com.godana.domain.entity.Like;
import com.godana.domain.entity.Post;
import com.godana.domain.entity.User;
import com.godana.exception.DataInputException;
import com.godana.service.like.ILikeService;
import com.godana.service.post.IPostService;
import com.godana.service.user.IUserService;
import com.godana.utils.ValidateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/like")
public class LikeAPI {
    @Autowired
    private ILikeService iLikeService;
    @Autowired
    private ValidateUtils validateUtils;
    @Autowired
    private IPostService iPostService;
    @Autowired
    private IUserService iUserService;


    @GetMapping("/{userId}")
    public ResponseEntity<?> findAllLikeByUser(@PathVariable("userId") String userIdStr){
        if(!validateUtils.isNumberValid(userIdStr)){
            throw new DataInputException("ID user không đúng định dạng");
        }
        Long userId = Long.parseLong(userIdStr);
        List<Like> likeList = iLikeService.findByUserId(userId);
        List<LikeDTO> likeDTOS = new ArrayList<>();
        for(Like item : likeList){
            Optional<Post> postOptional = iPostService.findById(item.getPost().getId());
            int totalLikes = postOptional.get().getLikes().size();
            int totalComments = postOptional.get().getComments().size();
            Date createAt = postOptional.get().getCreatedAt();
             likeDTOS.add(item.toLikeDTO(totalLikes, totalComments,createAt));
        }
        if(likeDTOS.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(likeDTOS, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> createLike(@RequestBody LikeReqDTO likeReqDTO){
        iLikeService.create(likeReqDTO);
        return new ResponseEntity<>(HttpStatus.OK);

    }

    @PostMapping("/delete")
    public ResponseEntity<?> deleteLike(@RequestBody LikeReqDTO likeReqDTO){
        if(!validateUtils.isNumberValid(likeReqDTO.getPostId())){
            throw new DataInputException("ID post không đúng định dạng");
        }

        if(!validateUtils.isNumberValid(likeReqDTO.getUserId())){
            throw new DataInputException("ID user không đúng định dạng");
        }

        Long postId = Long.parseLong(likeReqDTO.getPostId());
        Optional<Post> postOptional = iPostService.findById(postId);
        if(!postOptional.isPresent()){
            throw new DataInputException("Bài Post không tồn tại");
        }
        Post post = postOptional.get();

        Long userId = Long.parseLong(likeReqDTO.getUserId());
        Optional<User> userOptional = iUserService.findById(userId);
        if(!userOptional.isPresent()){
            throw new DataInputException("User không tồn tại");
        }
        User user = userOptional.get();

        Optional<Like> likeOptional = iLikeService.findByUserIdAndPostId(userId, postId);
        Like like = likeOptional.get();
        iLikeService.delete(like);

        return new ResponseEntity<>(HttpStatus.OK);

    }
}
