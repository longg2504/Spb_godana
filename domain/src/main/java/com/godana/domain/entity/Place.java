package com.godana.domain.entity;

import com.godana.domain.dto.avatar.AvatarResDTO;
import com.godana.domain.dto.place.PlaceCreResDTO;
import com.godana.domain.enums.EPlaceStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name ="places")
@Accessors(chain = true)
public class Place extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String content;

    @Column(length = 50)
    private String longitude;

    @Column(length = 50)
    private String latitude;

    @ManyToOne
    @JoinColumn(name = "category_id", referencedColumnName = "id", nullable = false)
    private Category category;

    @ManyToOne
    @JoinColumn(name="location_region_id", referencedColumnName = "id" , nullable = false)
    private LocationRegion locationRegion;

    @OneToMany(mappedBy = "place")
    private List<Rating> ratingList;

    @OneToMany(mappedBy = "place")
    private List<NearbyPlace> nearbyPlaceList;

    @ManyToOne
    @JoinColumn(name="user_Id" , referencedColumnName = "id" , nullable = false)
    private User user;

    @Enumerated(EnumType.STRING)
    private EPlaceStatus status;

    public PlaceCreResDTO toPlaceCreResDTO(){
        return new PlaceCreResDTO()
                .setId(id)
                .setPlaceTitle(title)
                .setContent(content)
                .setLongitude(longitude)
                .setLatitude(latitude)
                .setCategory(category.toCategoryDTO())
                .setLocationRegion(locationRegion.toLocationRegionDTO())
                .setUser(user.toUserDTO())
                .setStatus(status)
                ;
    }

    public PlaceCreResDTO toPlaceCreResDTO(List<PlaceAvatar> placeAvatars){
        return new PlaceCreResDTO()
                .setId(id)
                .setPlaceTitle(title)
                .setContent(content)
                .setLongitude(longitude)
                .setLatitude(latitude)
                .setCategory(category.toCategoryDTO())
                .setLocationRegion(locationRegion.toLocationRegionDTO())
                .setUser(user.toUserDTO())
                .setStatus(status)
                .setPlaceAvatar(toAvatarResDTOList(placeAvatars))
                ;
    }

    public List<AvatarResDTO> toAvatarResDTOList(List<PlaceAvatar> placeAvatars){
        List<AvatarResDTO> dtoList = new ArrayList<>();
        for (PlaceAvatar placeAvatar : placeAvatars) {
            dtoList.add(placeAvatar.toPlaceAvatarResDTO());
        }
        return dtoList;
    }

}
