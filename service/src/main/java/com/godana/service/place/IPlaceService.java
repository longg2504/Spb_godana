package com.godana.service.place;

import com.godana.domain.dto.place.PlaceCreReqDTO;
import com.godana.domain.dto.place.PlaceCreResDTO;
import com.godana.domain.entity.Place;
import com.godana.service.IGeneralService;

public interface IPlaceService extends IGeneralService<Place, Long> {
    PlaceCreResDTO create(PlaceCreReqDTO placeCreReqDTO);
}
