package com.godana.service.like;

import com.godana.domain.dto.like.LikeReqDTO;
import com.godana.domain.entity.Like;
import com.godana.domain.entity.Post;
import com.godana.domain.entity.User;
import com.godana.exception.DataInputException;
import com.godana.repository.like.LikeRepository;
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
public class LikeServiceImpl implements ILikeService{
    @Autowired
    private LikeRepository likeRepository;
    @Autowired
    private ValidateUtils validateUtils;
    @Autowired
    private IPostService iPostService;
    @Autowired
    private IUserService iUserService;
    @Override
    public List<Like> findAll() {
        return likeRepository.findAll();
    }

    @Override
    public Optional<Like> findById(Long id) {
        return likeRepository.findById(id);
    }

    @Override
    public Like save(Like like) {
        return likeRepository.save(like);
    }

    @Override
    public void delete(Like like) {
        likeRepository.delete(like);
    }

    @Override
    public void deleteById(Long id) {
        likeRepository.deleteById(id);
    }

    @Override
    public void create(LikeReqDTO likeReqDTO) {
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

        Like like = new Like();
        like.setId(null);
        like.setPost(post);
        like.setUser(user);

        save(like);
    }

    @Override
    public Optional<Like> findByUserIdAndPostId(Long userId, Long postId) {
        return likeRepository.findByUserIdAndPostId(userId, postId);
    }

    @Override
    public List<Like> findByUserId(Long userId) {
        return likeRepository.findByUserId(userId);
    }
}
