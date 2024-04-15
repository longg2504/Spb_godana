package com.godana.domain.dto.rating;

import com.godana.domain.entity.Place;
import com.godana.domain.entity.Rating;
import com.godana.domain.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Accessors(chain = true)
public class RatingCreReqDTO {
    private String content;
    private Double rating;
    private Long placeId;
    private Long userId;


    public Rating toRating(User user, Place place) {
        return new Rating()
                .setContent(content)
                .setRating(rating)
                .setUser(user)
                .setPlace(place)
                ;
    }
}
