package com.godana.service.postAvatar;

import com.godana.domain.entity.PostAvatar;
import com.godana.repository.postAvatar.PostAvatarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Transactional
@Service
public class PostAvartarServiceImpl implements IPostAvatarService {
    @Autowired
    private PostAvatarRepository postAvatarRepository;
    @Override
    public List<PostAvatar> findAll() {
        return postAvatarRepository.findAll();
    }

    @Override
    public Optional<PostAvatar> findById(String id) {
        return postAvatarRepository.findById(id);
    }

    @Override
    public PostAvatar save(PostAvatar postAvatar) {
        return postAvatarRepository.save(postAvatar);
    }

    @Override
    public void delete(PostAvatar postAvatar) {
        postAvatarRepository.delete(postAvatar);
    }

    @Override

    public void deleteById(String id) {
    }
}
