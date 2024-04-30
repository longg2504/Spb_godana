package com.godana.service.favourite;

import com.godana.domain.dto.favourite.FavouriteDTO;
import com.godana.domain.entity.Favourite;
import com.godana.domain.entity.Place;
import com.godana.domain.entity.User;
import com.godana.repository.favourite.FavouriteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Transactional
@Service
public class FavouriteServiceImpl implements IFavouriteService{
    @Autowired
    private FavouriteRepository favouriteRepository;
    @Override
    public List<Favourite> findAll() {
        return favouriteRepository.findAll();
    }

    @Override
    public Optional<Favourite> findById(Long id) {
        return favouriteRepository.findById(id);
    }

    @Override
    public Favourite save(Favourite favourite) {
        return favouriteRepository.save(favourite);
    }

    @Override
    public void delete(Favourite favourite) {
        favouriteRepository.delete(favourite);
    }

    @Override
    public void deleteById(Long id) {

    }

    @Override
    public List<Favourite> findAllByUserId(Long userId) {
        return favouriteRepository.findAllByUserId(userId);
    }

    @Override
    public Optional<Favourite> findByUserIdAndPlaceId(Long userId, Long placeId) {
        return favouriteRepository.findByUserIdAndPlaceId(userId, placeId);
    }

    @Override
    public void create(User user, Place place) {
        Favourite favourite = new Favourite();
        favourite.setId(null);
        favourite.setUser(user);
        favourite.setPlace(place);
        favouriteRepository.save(favourite);
    }
}
