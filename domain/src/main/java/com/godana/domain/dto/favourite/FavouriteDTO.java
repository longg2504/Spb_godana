package com.godana.domain.dto.favourite;

import com.godana.domain.dto.place.PlaceDTO;
import com.godana.domain.dto.user.UserDTO;
import com.godana.domain.entity.Place;
import com.godana.domain.entity.PlaceAvatar;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.List;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Accessors(chain = true)
public class FavouriteDTO {
    private Long id;
    private PlaceDTO place;
}
