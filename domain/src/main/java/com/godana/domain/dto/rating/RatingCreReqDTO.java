package com.godana.domain.dto.rating;

import com.godana.domain.dto.post.PostCreReqDTO;
import com.godana.domain.entity.Place;
import com.godana.domain.entity.Rating;
import com.godana.domain.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import javax.validation.constraints.NotBlank;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Accessors(chain = true)
public class RatingCreReqDTO implements Validator {
    @NotBlank(message = "nội dung đánh giá không được trồng")
    private String content;
    @NotBlank(message = "điểm đánh giá không được trống")
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

    @Override
    public boolean supports(Class<?> clazz) {
        return RatingCreReqDTO.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        RatingCreReqDTO ratingCreReqDTO = (RatingCreReqDTO) target;
        String content = ratingCreReqDTO.content;
        Double rating = ratingCreReqDTO.rating;
        if (content.isEmpty()) {
            errors.rejectValue("content", "content.null", "nội dung đánh giá không được phép rỗng");
        }
    }

}
