package com.godana.repository.placeAvatar;

import com.godana.domain.dto.placeAvatar.PlaceAvatarDTO;
import com.godana.domain.entity.Place;
import com.godana.domain.entity.PlaceAvatar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlaceAvatarRepository extends JpaRepository<PlaceAvatar, String> {
    List<PlaceAvatar> findAllByPlace(Place place);


}
