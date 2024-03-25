package com.godana.service.userAvatar;

import com.godana.domain.entity.UserAvatar;
import com.godana.repository.userAvatar.UserAvatarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Transactional
@Service
public class UserAvatarServiceImpl implements IUserAvatarService{
    @Autowired
    private UserAvatarRepository userAvatarRepository;
    @Override
    public List<UserAvatar> findAll() {
        return userAvatarRepository.findAll();
    }

    @Override
    public Optional<UserAvatar> findById(String id) {
        return userAvatarRepository.findById(id);
    }

    @Override
    public UserAvatar save(UserAvatar userAvatar) {
        return userAvatarRepository.save(userAvatar);
    }

    @Override
    public void delete(UserAvatar userAvatar) {

    }

    @Override
    public void deleteById(String id) {

    }

}
