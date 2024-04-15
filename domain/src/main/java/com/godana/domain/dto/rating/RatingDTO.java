package com.godana.domain.dto.rating;

import com.godana.domain.dto.user.UserDTO;
import com.godana.domain.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.Date;


@NoArgsConstructor
@Getter
@Setter
@Accessors(chain = true)
public class RatingDTO {
    private Long id;
    private String content;
    private Double rating;
    private UserDTO userDTO;
    private Date created_at;


    public RatingDTO(Long id, String content, Double rating, User user, Date create_at) {
        this.id = id;
        this.content = content;
        this.rating = rating;
        this.userDTO = user.toUserDTO();
        this.created_at = create_at;
    }
}
