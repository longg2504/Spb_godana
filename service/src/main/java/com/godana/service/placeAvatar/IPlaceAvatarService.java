package com.godana.service.placeAvatar;

import com.godana.domain.dto.place.PlaceDTO;
import com.godana.domain.dto.placeAvatar.PlaceAvatarDTO;
import com.godana.domain.entity.Place;
import com.godana.domain.entity.PlaceAvatar;
import com.godana.service.IGeneralService;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IPlaceAvatarService extends IGeneralService<PlaceAvatar, String> {
    List<PlaceAvatar> findAllByPlace(Place place);


}
