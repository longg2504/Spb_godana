package com.godana.service.comment;

import com.godana.domain.dto.comment.*;
import com.godana.domain.entity.Comment;
import com.godana.domain.entity.Post;
import com.godana.domain.entity.User;
import com.godana.exception.DataInputException;
import com.godana.repository.comment.CommentRepository;
import com.godana.service.post.IPostService;
import com.godana.service.user.IUserService;
import com.godana.utils.ValidateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Transactional
@Service
public class CommentServiceImpl implements ICommentService{
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private ValidateUtils validateUtils;
    @Autowired
    private IUserService iUserService;
    @Autowired
    private IPostService iPostService;
    @Override
    public List<Comment> findAll() {
        return commentRepository.findAll();
    }

    @Override
    public Optional<Comment> findById(Long id) {
        return commentRepository.findById(id);
    }

    @Override
    public Comment save(Comment comment) {
        return commentRepository.save(comment);
    }

    @Override
    public void delete(Comment comment) {
        commentRepository.delete(comment);
    }

    @Override
    public void deleteById(Long id) {
        commentRepository.deleteById(id);
    }

    @Override
    public List<CommentResDTO> findAllByPostId(Long postId) {
        return commentRepository.findAllByPostId(postId);
    }

    @Override
    public List<ReplyResDTO> findAllByCommentParentId(Long commentParentId) {
        return commentRepository.findAllByCommentParentId(commentParentId);
    }

    @Override
    public CommenCreResDTO createComment(CommentCreReqDTO commentCreReqDTO) {
        if(!validateUtils.isNumberValid(commentCreReqDTO.getUserId())){
            throw new DataInputException("ID User không đúng định dạng");
        }
        if(!validateUtils.isNumberValid(commentCreReqDTO.getPostId())){
            throw new DataInputException("ID Post không đúng định dạng");
        }
        Long userId = Long.parseLong(commentCreReqDTO.getUserId());
        Optional<User> userOptional = iUserService.findById(userId);
        if(!userOptional.isPresent()){
            throw new DataInputException("User này không tồn tại");
        }
        User user = userOptional.get();


        Long postId = Long.parseLong(commentCreReqDTO.getPostId());
        Optional<Post> postOptional = iPostService.findById(postId);
        if(!postOptional.isPresent()){
            throw new DataInputException("Post này không tồn tại");
        }
        Post post = postOptional.get();
        Comment comment = new Comment();
        comment.setId(null);
        comment.setCommentParent(null);
        comment.setContent(commentCreReqDTO.getContent());
        comment.setUser(user);
        comment.setPost(post);
        comment = commentRepository.save(comment);

        CommenCreResDTO commentResDTO = comment.toCommentCreResDTO(comment.getCreatedAt());
        return commentResDTO;
    }

    @Override
    public ReplyCreResDTO createReply(Long commentId, ReplyCreReqDTO replyCreReqDTO) {
        if(!validateUtils.isNumberValid(replyCreReqDTO.getUserId())){
            throw new DataInputException("ID User không đúng định dạng");
        }
        if(!validateUtils.isNumberValid(replyCreReqDTO.getPostId())){
            throw new DataInputException("ID Post không đúng định dạng");
        }
        Long userId = Long.parseLong(replyCreReqDTO.getUserId());
        Optional<User> userOptional = iUserService.findById(userId);

        if(!userOptional.isPresent()){
            throw new DataInputException("User này không tồn tại");
        }
        User user = userOptional.get();

        Long postId = Long.parseLong(replyCreReqDTO.getPostId());
        Optional<Post> postOptional = iPostService.findById(postId);
        if(!postOptional.isPresent()){
            throw new DataInputException("Post này không tồn tại");
        }

        Optional<Comment> commentOptional = commentRepository.findById(commentId);
        if(!commentOptional.isPresent()){
            throw new DataInputException("Comment này không tồn tại");
        }
        Post post = postOptional.get();


        Comment comment = new Comment();
        comment.setId(null);
        comment.setContent(replyCreReqDTO.getContent());
        comment.setCommentParent(commentOptional.get());
        comment.setUser(user);
        comment.setPost(post);
        comment = commentRepository.save(comment);

        ReplyCreResDTO replyCreResDTO = comment.toReplyCreResDTO(comment.getCreatedAt(), commentOptional.get().getId());
        return replyCreResDTO;
    }

    @Override
    public CommentResDTO updateComment(CommentUpReqDTO commentUpReqDTO, Long commentId) {
        Optional<Comment> commentOptional = commentRepository.findById(commentId);
        if(!commentOptional.isPresent()){
            throw new DataInputException("Comment này không tồn tại");
        }

        Comment comment = commentOptional.get();
        comment.setContent(commentUpReqDTO.getContent());
        comment = commentRepository.save(comment);

        CommentResDTO commentResDTO = comment.toCommentResDTO(comment.getCreatedAt(),comment.getComments().stream().count());
        return commentResDTO;
    }
}
