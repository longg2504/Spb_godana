package com.godana.api;

import com.godana.domain.dto.favourite.FavouriteCreReqDTO;
import com.godana.domain.dto.favourite.FavouriteDTO;
import com.godana.domain.dto.favourite.FavouriteDelReqDTO;
import com.godana.domain.dto.place.PlaceDTO;
import com.godana.domain.dto.rating.RatingStats;
import com.godana.domain.entity.*;
import com.godana.exception.DataInputException;
import com.godana.service.favourite.IFavouriteService;
import com.godana.service.place.IPlaceService;
import com.godana.service.user.IUserService;
import com.godana.utils.ValidateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/favourite")
public class FavouriteAPI {
    @Autowired
    private IFavouriteService iFavouriteService;
    @Autowired
    private IUserService iUserService;
    @Autowired
    private IPlaceService iPlaceService;

    @Autowired
    private ValidateUtils validateUtils;

    @GetMapping("/{userId}")
    public ResponseEntity<?> getAllFavouriteList(@PathVariable("userId") String userIdStr) {
        if(!validateUtils.isNumberValid(userIdStr)){
            throw new DataInputException("Mã User không tồn tại");
        }

        Long userId = Long.parseLong(userIdStr);
        Optional<User> userOptional = iUserService.findById(userId);
        if(!userOptional.isPresent()){
            throw new DataInputException("User không tồn tại");
        }
        List<Favourite> favourites = iFavouriteService.findAllByUserId(userId);
        List<FavouriteDTO> favouriteDTOS = new ArrayList<>();
        for(Favourite item : favourites){
            Optional<Place> placeOptional = iPlaceService.findById(item.getPlace().getId());
            List<PlaceAvatar> placeAvatars = placeOptional.get().getPlaceAvatarList();
            Double rating = calculateAverage(placeOptional.get().getRatingList()).getAverageRating() ;
            Integer numberRating = calculateAverage(placeOptional.get().getRatingList()).getNumberOfRatings();
            favouriteDTOS.add(item.toFavouriteDTO(placeAvatars,rating,numberRating));
        }
        if(favouriteDTOS.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(favouriteDTOS, HttpStatus.OK);
    }



    @PostMapping
    public ResponseEntity<?> createFavourite(@RequestBody FavouriteCreReqDTO favouriteCreReqDTO){
        if(!validateUtils.isNumberValid(favouriteCreReqDTO.getUserId())){
            throw new DataInputException("Mã User không tồn tại");
        }
        Long userId = Long.parseLong(favouriteCreReqDTO.getUserId());
        Optional<User> userOptional = iUserService.findById(userId);
        if(!userOptional.isPresent()){
            throw new DataInputException("User không tồn tại");
        }
        User user = userOptional.get();

        if(!validateUtils.isNumberValid(favouriteCreReqDTO.getPlaceId())){
            throw new DataInputException("Mã Place không tồn tại");
        }
        Long placeId = Long.parseLong(favouriteCreReqDTO.getPlaceId());
        Optional<Place> placeOptional = iPlaceService.findById(placeId);
        if(!placeOptional.isPresent()){
            throw new DataInputException("Place không tồn tại");
        }
        Place place = placeOptional.get();
        iFavouriteService.create(user, place);

        return new ResponseEntity<>(HttpStatus.CREATED);

    }

    @PostMapping("/delete")
    public ResponseEntity<?> deleteFavourite(@RequestBody FavouriteDelReqDTO favouriteDelReqDTO){
        if(!validateUtils.isNumberValid(favouriteDelReqDTO.getUserId())){
            throw new DataInputException("Mã User không tồn tại");
        }
        Long userId = Long.parseLong(favouriteDelReqDTO.getUserId());
        Optional<User> userOptional = iUserService.findById(userId);
        if(!userOptional.isPresent()){
            throw new DataInputException("User không tồn tại");
        }


        if(!validateUtils.isNumberValid(favouriteDelReqDTO.getPlaceId())){
            throw new DataInputException("Mã Place không tồn tại");
        }
        Long placeId = Long.parseLong(favouriteDelReqDTO.getPlaceId());
        Optional<Place> placeOptional = iPlaceService.findById(placeId);
        if(!placeOptional.isPresent()){
            throw new DataInputException("Place không tồn tại");
        }
        Optional<Favourite> favouriteOptional = iFavouriteService.findByUserIdAndPlaceId(userId, placeId);
        Favourite favourite = favouriteOptional.get();
        iFavouriteService.delete(favourite);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    private RatingStats calculateAverage(List<Rating> ratings) {
        if (ratings == null || ratings.isEmpty()) {
            return new RatingStats(0.0, 0);
        }

        Double sum = 0.0;
        for (Rating rating : ratings) {
            sum += rating.getRating();
        }
        double averageRating = sum / ratings.size();
        return new RatingStats(averageRating, (Integer) ratings.size());
    }
}
