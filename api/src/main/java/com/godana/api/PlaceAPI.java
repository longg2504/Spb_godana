package com.godana.api;

import com.godana.domain.dto.place.PlaceCreReqDTO;
import com.godana.domain.dto.place.PlaceCreResDTO;
import com.godana.domain.dto.place.PlaceDTO;
import com.godana.domain.entity.Category;
import com.godana.domain.entity.Place;
import com.godana.exception.DataInputException;
import com.godana.service.category.ICategoryService;
import com.godana.service.place.IPlaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/api/place")
public class PlaceAPI {
    @Autowired
    private IPlaceService iPlaceService;
    @Autowired
    private ICategoryService iCategoryService;


    @GetMapping
    public ResponseEntity<?> getAllPlace(@RequestParam (defaultValue = "") Category category, @RequestParam (defaultValue = "") String search,Pageable pageable){
        Page<PlaceDTO> placeDTOS = iPlaceService.findAllByCategoryAndSearch(category, search, pageable);
        if(placeDTOS.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(placeDTOS, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> createPlace(@ModelAttribute PlaceCreReqDTO placeCreReqDTO){
        PlaceCreResDTO placeCreResDTO = iPlaceService.create(placeCreReqDTO);
        return new ResponseEntity<>(placeCreResDTO, HttpStatus.OK);
    }
}
