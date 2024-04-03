package com.godana.service.place;

import com.godana.domain.dto.place.PlaceCreReqDTO;
import com.godana.domain.dto.place.PlaceCreResDTO;
import com.godana.domain.dto.place.PlaceDTO;
import com.godana.domain.entity.Category;
import com.godana.domain.entity.Place;
import com.godana.service.IGeneralService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface IPlaceService extends IGeneralService<Place, Long> {


    Page<PlaceDTO> findAllByCategoryAndSearch(@Param("category") Category category, @Param("search") String search, Pageable pageable);
    PlaceCreResDTO create(PlaceCreReqDTO placeCreReqDTO);

}
