package com.godana.domain.dto.rating;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RatingCountDTO {
    private Long countRating;

    public RatingCountDTO(Long countRating) {
        this.countRating = countRating;
    }
}
