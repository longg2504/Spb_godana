package com.godana.domain.dto.place;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PlaceCountDTO {
    private Long countPlace;

    public PlaceCountDTO(Long countPlace) {
        this.countPlace = countPlace;
    }
}
