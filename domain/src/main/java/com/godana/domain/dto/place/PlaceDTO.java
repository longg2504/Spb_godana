package com.godana.domain.dto.place;

import com.godana.domain.dto.category.CategoryDTO;
import com.godana.domain.dto.contact.ContactDTO;
import com.godana.domain.dto.locationRegion.LocationRegionDTO;
import com.godana.domain.dto.placeAvatar.PlaceAvatarDTO;
import com.godana.domain.entity.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;


@NoArgsConstructor
@Getter
@Setter
@Accessors(chain = true)
public class PlaceDTO {
    private Long id;
    private String placeTitle;
    private String content;
    private String longitude;
    private String latitude;
    private List<PlaceAvatarDTO> placeAvatar;
    private CategoryDTO category;
    private LocationRegionDTO locationRegion;
    private ContactDTO contact;
    private Double rating;
    private Long numberRating;

    public PlaceDTO(Long id, String placeTitle, String content, String longitude, String latitude, List<PlaceAvatar> placeAvatar, Category category, LocationRegion locationRegion, Contact contact, Double rating) {
        this.id = id;
        this.placeTitle = placeTitle;
        this.content = content;
        this.longitude = longitude;
        this.latitude = latitude;
        this.placeAvatar = toAvatarDTOList(placeAvatar);
        this.category = category.toCategoryDTO();
        this.locationRegion = locationRegion.toLocationRegionDTO();
        this.contact = contact.toContactDTO();
        this.rating = rating;

    }
    public PlaceDTO(Long id, String placeTitle, String content, String longitude, String latitude, Category category, LocationRegion locationRegion, Contact contact, Double rating, Long numberRating) {
        this.id = id;
        this.placeTitle = placeTitle;
        this.content = content;
        this.longitude = longitude;
        this.latitude = latitude;
        this.category = category.toCategoryDTO();
        this.locationRegion = locationRegion.toLocationRegionDTO();
        this.contact = contact.toContactDTO();
        this.rating = rating;
        this.numberRating = numberRating;
    }

    public List<PlaceAvatarDTO> toAvatarDTOList(List<PlaceAvatar> placeAvatars){
        List<PlaceAvatarDTO> dtoList = new ArrayList<>();
        for (PlaceAvatar placeAvatar : placeAvatars) {
            dtoList.add(placeAvatar.toPlaceAvatarDTO());
        }
        return dtoList;
    }
}
