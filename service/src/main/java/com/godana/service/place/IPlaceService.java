package com.godana.service.place;

import com.godana.domain.dto.place.*;
import com.godana.domain.entity.Category;
import com.godana.domain.entity.Place;
import com.godana.service.IGeneralService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface IPlaceService extends IGeneralService<Place, Long> {

    Optional<PlaceDTO> findPlaceById(Long id);
    Page<PlaceDTO> findAllByCategoryAndSearch(@Param("category") Category category, @Param("search") String search, @Param("districtName") String districtName, @Param("wardName") String wardName, @Param("address") String address, @Param("rating") Double rating, Pageable pageable);
    Optional<Place> findPlaceByIdAndDeletedFalse(Long id);
    PlaceCreResDTO create(PlaceCreReqDTO placeCreReqDTO);

    PlaceUpResDTO update(String placeIdStr, PlaceUpReqDTO placeUpReqDTO);

    List<Place> findNearPlace(float longitude, float latitude, Long id);

    PlaceCountDTO countPlace();


}
