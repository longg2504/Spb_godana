package com.godana.service.favourite;

import com.godana.domain.dto.favourite.FavouriteDTO;
import com.godana.domain.entity.Favourite;
import com.godana.domain.entity.Place;
import com.godana.domain.entity.User;
import com.godana.service.IGeneralService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface IFavouriteService extends IGeneralService<Favourite, Long> {
    List<Favourite> findAllByUserIdAndDeletedFalse(Long userId);

    Optional<Favourite> findByUserIdAndPlaceId(Long userId, Long placeId);
    List<Favourite> findAllByPlace(Place place);

    Favourite create(User user, Place place);
}
