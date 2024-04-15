package com.godana.api;

import com.godana.domain.dto.rating.RatingCreReqDTO;
import com.godana.domain.dto.rating.RatingCreResDTO;
import com.godana.domain.dto.rating.RatingDTO;
import com.godana.domain.entity.Place;
import com.godana.domain.entity.User;
import com.godana.exception.DataInputException;
import com.godana.service.place.IPlaceService;
import com.godana.service.rating.IRatingService;
import com.godana.service.user.IUserService;
import com.godana.utils.ValidateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/rating")
public class RatingAPI {
    @Autowired
    private IRatingService iRatingService;
    @Autowired
    private IPlaceService iPlaceService;
    @Autowired
    private IUserService iUserService;
    @Autowired
    private ValidateUtils validateUtils;


    @GetMapping("/{placeId}")
    public ResponseEntity<?> getAllRatingByPlaceId(@PathVariable("placeId") String placeIdStr){
        if(!validateUtils.isNumberValid(placeIdStr)){
            throw new DataInputException("Mã địa điểm không đúng vui lòng kiểm tra lại");
        }
        Long placeId = Long.parseLong(placeIdStr);
        Optional<Place> optionalPlace = iPlaceService.findPlaceByIdAndDeletedFalse(placeId);
        if(!optionalPlace.isPresent()){
            throw new DataInputException("Địa điểm không tồn tại vui lòng kiểm tra lại");
        }

        List<RatingDTO> ratingCreResDTOS = iRatingService.findAllByPlaceId(placeId);
        return new ResponseEntity<>(ratingCreResDTOS, HttpStatus.OK);
    }


    @PostMapping
    public ResponseEntity<?> createRating(@RequestBody RatingCreReqDTO ratingCreReqDTO){
        Double point = ratingCreReqDTO.getRating();
        if(1.0 <= point || point >= 5.0) {
            RatingCreResDTO ratingCreResDTO = iRatingService.create(ratingCreReqDTO);
            return new ResponseEntity<>(ratingCreResDTO, HttpStatus.OK);
        }
        else {
            throw new DataInputException("Số điểm đánh giá không thể lớn hơn 5.0 hoặc bé hơn 1.0");
        }
    }
}
