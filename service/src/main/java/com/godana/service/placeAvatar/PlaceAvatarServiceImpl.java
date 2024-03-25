package com.godana.service.placeAvatar;

import com.godana.domain.entity.PlaceAvatar;
import com.godana.repository.placeAvatar.PlaceAvatarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class PlaceAvatarServiceImpl implements IPlaceAvatarService{
    @Autowired
    private PlaceAvatarRepository placeAvatarRepository;
    @Override
    public List<PlaceAvatar> findAll() {
        return placeAvatarRepository.findAll();
    }

    @Override
    public Optional<PlaceAvatar> findById(String id) {
        return placeAvatarRepository.findById(id);
    }

    @Override
    public PlaceAvatar save(PlaceAvatar placeAvatar) {
        return placeAvatarRepository.save(placeAvatar);
    }

    @Override
    public void delete(PlaceAvatar placeAvatar) {

    }

    @Override
    public void deleteById(String id) {

    }
}
