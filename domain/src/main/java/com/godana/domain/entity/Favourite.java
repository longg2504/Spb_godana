package com.godana.domain.entity;

import com.godana.domain.dto.favourite.FavouriteDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name="favourites")
public class Favourite extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="place_id" , referencedColumnName = "id", nullable = false)
    private Place place;

    @ManyToOne
    @JoinColumn(name="user_id" , referencedColumnName = "id", nullable = false)
    private User user;

    public FavouriteDTO toFavouriteDTO(List<PlaceAvatar> placeAvatarList, Double rating, Integer numberRating) {
        return new FavouriteDTO()
                .setId(id)
                .setPlace(place.toPlaceDTO(placeAvatarList, rating, numberRating));
    }
}
