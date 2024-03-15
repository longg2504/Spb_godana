package com.godana.service.place;

import com.godana.domain.entity.Place;
import com.godana.repository.place.PlaceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Transactional
@Service
public class PlaceServiceImpl implements IPlaceService{
    @Autowired
    private PlaceRepository placeRepository;
    @Override
    public List<Place> findAll() {
        return placeRepository.findAll();
    }

    @Override
    public Optional<Place> findById(Long id) {
        return placeRepository.findById(id);
    }

    @Override
    public Place save(Place place) {
        return placeRepository.save(place);
    }

    @Override
    public void delete(Place place) {
        placeRepository.delete(place);
    }

    @Override
    public void deleteById(Long id) {
        placeRepository.deleteById(id);
    }
}
