package com.godana.service.rating;


import com.godana.domain.dto.rating.RatingCountDTO;
import com.godana.domain.dto.rating.RatingCreReqDTO;
import com.godana.domain.dto.rating.RatingCreResDTO;
import com.godana.domain.dto.rating.RatingDTO;
import com.godana.domain.entity.Place;
import com.godana.domain.entity.Rating;
import com.godana.service.IGeneralService;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface IRatingService extends IGeneralService<Rating, Long> {
    List<RatingDTO> findAllByPlaceId(@Param("placeId") Long placeId);
    List<Rating> findAllByPlace(Place place);
    RatingCreResDTO create(RatingCreReqDTO ratingCreReqDTO);
    RatingCountDTO countRating();
}
