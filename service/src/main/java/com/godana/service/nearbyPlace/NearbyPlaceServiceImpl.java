package com.godana.service.nearbyPlace;

import com.godana.domain.entity.NearbyPlace;
import com.godana.repository.nearbyPlace.NearbyPlaceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Transactional
@Service
public class NearbyPlaceServiceImpl implements INearbyPlaceService{
    @Autowired
    private NearbyPlaceRepository nearbyPlaceRepository;
    @Override
    public List<NearbyPlace> findAll() {
        return nearbyPlaceRepository.findAll();
    }

    @Override
    public Optional<NearbyPlace> findById(Long id) {
        return nearbyPlaceRepository.findById(id);
    }

    @Override
    public NearbyPlace save(NearbyPlace nearbyPlace) {
        return nearbyPlaceRepository.save(nearbyPlace);
    }

    @Override
    public void delete(NearbyPlace nearbyPlace) {
        nearbyPlaceRepository.delete(nearbyPlace);
    }

    @Override
    public void deleteById(Long id) {
        nearbyPlaceRepository.deleteById(id);
    }
}
