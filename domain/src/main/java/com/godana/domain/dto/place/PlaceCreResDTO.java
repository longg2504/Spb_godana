package com.godana.domain.dto.place;

import com.godana.domain.dto.placeAvatar.PlaceAvatarDTO;
import com.godana.domain.dto.postAvatar.PostAvatarResDTO;
import com.godana.domain.dto.category.CategoryDTO;
import com.godana.domain.dto.contact.ContactDTO;
import com.godana.domain.dto.locationRegion.LocationRegionDTO;
import com.godana.domain.dto.user.UserDTO;
import com.godana.domain.enums.EPlaceStatus;
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
public class PlaceCreResDTO {
    private Long id;
    private String placeTitle;
    private String content;
    private String longitude;
    private String latitude;
    private List<PlaceAvatarDTO> placeAvatar;
    private CategoryDTO category;
    private LocationRegionDTO locationRegion;
    private ContactDTO contact;
    private UserDTO user;
    private EPlaceStatus status;
}
