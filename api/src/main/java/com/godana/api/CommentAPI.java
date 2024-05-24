package com.godana.api;

import com.godana.domain.dto.comment.*;
import com.godana.domain.dto.place.PlaceCreReqDTO;
import com.godana.domain.entity.Comment;
import com.godana.exception.DataInputException;
import com.godana.service.comment.ICommentService;
import com.godana.utils.AppUtils;
import com.godana.utils.ValidateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/comment")
public class CommentAPI {
    @Autowired
    private ValidateUtils validateUtils;
    @Autowired
    private ICommentService iCommentService;
    @Autowired
    private AppUtils appUtils;

    @GetMapping("/{postId}")
    public ResponseEntity<?> getAllCommentParentByPost(@PathVariable("postId") String postIdStr){
        if(!validateUtils.isNumberValid(postIdStr)){
            throw new DataInputException("ID Post không đúng định dạng");
        }
        Long postId = Long.parseLong(postIdStr);

        List<CommentResDTO> commentResDTOs = iCommentService.findAllByPostId(postId);
        if(commentResDTOs.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(commentResDTOs, HttpStatus.OK);

    }

    @GetMapping("/get_all_reply/{commentId}")
    public ResponseEntity<?> getAllReplyByCommentId(@PathVariable("commentId") String commentIdStr){
        if(!validateUtils.isNumberValid(commentIdStr)){
            throw new DataInputException("ID Comment không đúng định dạng");
        }
        Long commentId = Long.parseLong(commentIdStr);
        Optional<Comment> commentOptional = iCommentService.findById(commentId);
        if(!commentOptional.isPresent()){
            throw new DataInputException("Comment này không tồn tại");
        }
        List<ReplyResDTO> replyResDTOS = iCommentService.findAllByCommentParentId(commentId);
        if(replyResDTOS.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(replyResDTOS, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> createComment(@RequestBody CommentCreReqDTO commentCreReqDTO, BindingResult bindingResult){
        new CommentCreReqDTO().validate(commentCreReqDTO, bindingResult);
        if (bindingResult.hasFieldErrors()) {
            return appUtils.mapErrorToResponse(bindingResult);
        }
        CommenCreResDTO commentResDTO = iCommentService.createComment(commentCreReqDTO);
        return new ResponseEntity<>(commentResDTO, HttpStatus.OK);
    }

    @PostMapping("/{commentId}")
    public ResponseEntity<?> replyComment(@PathVariable("commentId") String commentIdStr , @RequestBody ReplyCreReqDTO replyCreReqDTO, BindingResult bindingResult){
        new ReplyCreReqDTO().validate(replyCreReqDTO, bindingResult);
        if (bindingResult.hasFieldErrors()) {
            return appUtils.mapErrorToResponse(bindingResult);
        }

        if(!validateUtils.isNumberValid(commentIdStr)){
            throw new DataInputException("ID Comment không đúng định dạng");
        }
        Long commentId = Long.parseLong(commentIdStr);
        ReplyCreResDTO replyCreResDTO = iCommentService.createReply(commentId, replyCreReqDTO);
        return new ResponseEntity<>(replyCreResDTO, HttpStatus.OK);
    }

    @PostMapping("/update/{commentId}")
    public ResponseEntity<?> updateComment(@PathVariable("commentId") String commentIdStr, @RequestBody CommentUpReqDTO commentUpReqDTO, BindingResult bindingResult) {
        new CommentUpReqDTO().validate(commentUpReqDTO, bindingResult);
        if (bindingResult.hasFieldErrors()) {
            return appUtils.mapErrorToResponse(bindingResult);
        }
        if(!validateUtils.isNumberValid(commentIdStr)){
            throw new DataInputException("ID Comment không đúng định dạng");
        }

        Long commentId = Long.parseLong(commentIdStr);
        CommentResDTO commentResDTO = iCommentService.updateComment(commentUpReqDTO, commentId);
        return new ResponseEntity<>(commentResDTO, HttpStatus.OK);
    }

    @PostMapping("/deleted/{commentId}")
    public ResponseEntity<?> deletedComment(@PathVariable("commentId") String commentIdStr){
        if(!validateUtils.isNumberValid(commentIdStr)){
            throw new DataInputException("ID Comment không đúng định dạng");
        }
        Long commentId = Long.parseLong(commentIdStr);

        Optional<Comment> commentOptional = iCommentService.findById(commentId);
        if(!commentOptional.isPresent()){
            throw new DataInputException("Comment này không tồn tại");
        }
        Comment comment = commentOptional.get();
        iCommentService.delete(comment);

        return new ResponseEntity<>(HttpStatus.OK);
    }

}
