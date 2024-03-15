package com.godana.service.like;

import com.godana.domain.entity.Like;
import com.godana.repository.like.LikeRepository;
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
}
