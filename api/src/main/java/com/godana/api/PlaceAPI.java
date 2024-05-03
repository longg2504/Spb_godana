package com.godana.api;

import com.godana.domain.dto.place.*;
import com.godana.domain.dto.rating.RatingStats;
import com.godana.domain.entity.Category;
import com.godana.domain.entity.Place;
import com.godana.domain.entity.Rating;
import com.godana.exception.DataInputException;
import com.godana.repository.place.PlaceRepository;
import com.godana.service.category.ICategoryService;
import com.godana.service.place.IPlaceService;
import com.godana.utils.AppUtils;
import com.godana.utils.ValidateUtils;
import javassist.expr.NewArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/api/place")
public class PlaceAPI {
    @Autowired
    private IPlaceService iPlaceService;
    @Autowired
    private ICategoryService iCategoryService;
    @Autowired
    private ValidateUtils validateUtils;
    @Autowired
    private AppUtils appUtils;
    @Autowired
    private PlaceRepository placeRepository;


    @GetMapping
    public ResponseEntity<?> getAllPlace(@RequestParam (defaultValue = "") Category category,
                                         @RequestParam (defaultValue = "") String search, @RequestParam (defaultValue = "") String districtName,
                                         @RequestParam (defaultValue = "") String wardName, @RequestParam (defaultValue = "") String address,
                                         @RequestParam (defaultValue = "") Double rating,Pageable pageable){
        Page<PlaceDTO> placeDTOS = iPlaceService.findAllByCategoryAndSearch(category, search, districtName, wardName, address, rating, pageable);
        if(placeDTOS.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(placeDTOS, HttpStatus.OK);
    }

    @GetMapping("/{placeId}")
    public ResponseEntity<?> getByPlaceId(@PathVariable("placeId") String placeIdStr){
        if(!validateUtils.isNumberValid(placeIdStr)){
            throw new DataInputException("Mã địa điểm không tồn tại vui lòng xem lại!!!");
        }
        Long placeId = Long.valueOf(placeIdStr);

        Optional<Place> placeOptional = iPlaceService.findById(placeId);
        if(placeOptional.isEmpty()){
            throw new DataInputException(("Địa điểm không tồn tại vui lòng xem lại!!!"));
        }
        Place place = placeOptional.get();
        Double rating = calculateAverage(place.getRatingList()).getAverageRating();
        Integer numberRating = calculateAverage(place.getRatingList()).getNumberOfRatings() ;
        PlaceDTO placeDTO = place.toPlaceDTO(place.getPlaceAvatarList(),rating,numberRating);
        return new ResponseEntity<>(placeDTO, HttpStatus.OK);
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

    @PostMapping
    public ResponseEntity<?> createPlace(@ModelAttribute PlaceCreReqDTO placeCreReqDTO){
        PlaceCreResDTO placeCreResDTO = iPlaceService.create(placeCreReqDTO);
        return new ResponseEntity<>(placeCreResDTO, HttpStatus.OK);
    }

    @PostMapping("/{placeId}")
    public ResponseEntity<?> updatePlace(@PathVariable("placeId") String placeIdStr, PlaceUpReqDTO placeUpReqDTO){
        PlaceUpResDTO placeUpResDTO = iPlaceService.update(placeIdStr,placeUpReqDTO);
        return new ResponseEntity<>(placeUpResDTO, HttpStatus.OK);
    }

    @PostMapping("/nearby_place/{placeId}")
    public ResponseEntity<?> findNearbyPlace(@PathVariable("placeId") String placeIdStr) {
        if(!validateUtils.isNumberValid(placeIdStr)) {
            throw new DataInputException("ID của place không đúng định dạng");
        }

        Long placeId = Long.parseLong(placeIdStr);

        Optional<Place> placeOptional = iPlaceService.findById(placeId);
        if(!placeOptional.isPresent()){
            throw new DataInputException("địa điểm  không tồn tại");

        }

        Place place = placeOptional.get();

        Float longitude = Float.parseFloat(place.getLongitude());

        Float latitude = Float.parseFloat(place.getLatitude());

        List<Place> places = iPlaceService.findNearPlace(longitude,latitude,place.getId());
        List<PlaceDTO> placeDTOS = new ArrayList<>();
        for(Place item : places){
            Double rating = calculateAverage(item.getRatingList()).getAverageRating();
            Integer numberRating = calculateAverage(item.getRatingList()).getNumberOfRatings() ;
            placeDTOS.add(item.toPlaceDTO(item.getPlaceAvatarList(),rating, numberRating));
        }

        if(placeDTOS.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(placeDTOS, HttpStatus.OK);

    }
}
