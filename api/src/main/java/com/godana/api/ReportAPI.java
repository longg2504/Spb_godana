package com.godana.api;

import com.godana.domain.dto.category.CategoryCountDTO;
import com.godana.domain.dto.place.PlaceCountDTO;
import com.godana.domain.dto.post.PostCountDTO;
import com.godana.domain.dto.rating.RatingCountDTO;
import com.godana.domain.dto.report.*;
import com.godana.domain.dto.user.UserCountDTO;
import com.godana.service.category.ICategoryService;
import com.godana.service.place.IPlaceService;
import com.godana.service.post.IPostService;
import com.godana.service.rating.IRatingService;
import com.godana.service.user.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/report")
public class ReportAPI {
    @Autowired
    private IPlaceService iPlaceService;
    @Autowired
    private IPostService iPostService;
    @Autowired
    private ICategoryService iCategoryService;
    @Autowired
    private IRatingService iRatingService;
    @Autowired
    private IUserService iUserService;

    @GetMapping("/get_all_report")
    public ResponseEntity<?> getAllReport() {
        IReportDTO placesDayReport = iPlaceService.getPlaceReportOfCurrentDay();
        IReportDTO placesMonthReport = iPlaceService.getPlaceReportOfCurrentMonth();
        List<IYearReportDTO> placesYearReport = iPlaceService.getPlaceReportByCurrentYear();
        List<I6MonthAgoReportDTO> placesSixMonthReport = iPlaceService.getPlaceReport6Months();
        List<I6MonthAgoReportDTO> usersSixMonthReport = iUserService.getUserReport6Months();
        List<ICountPlaceByCateReportDTO> countPlacesByCategoryReport = iCategoryService.getCountPlaceByCategory();
        PlaceCountDTO placeCountDTO = iPlaceService.countPlace();
        CategoryCountDTO categoryCountDTO = iCategoryService.countCategory();
        PostCountDTO postCountDTO = iPostService.countPost();
        RatingCountDTO ratingCountDTO = iRatingService.countRating();
        UserCountDTO userCountDTO = iUserService.countUser();

        AllReportDTO allReportDTO = new AllReportDTO();
        allReportDTO.setDayPlaceReport(placesDayReport);
        allReportDTO.setMonthPlaceReport(placesMonthReport);
        allReportDTO.setYearPlacesReport(placesYearReport);
        allReportDTO.setPlaceSixMonthAgoReport(placesSixMonthReport);
        allReportDTO.setUserSixMonthAgoReport(usersSixMonthReport);
        allReportDTO.setCountPlacesByCateReport(countPlacesByCategoryReport);
        allReportDTO.setCountPlace(placeCountDTO.getCountPlace());
        allReportDTO.setCountPost(postCountDTO.getCountPost());
        allReportDTO.setCountCategory(categoryCountDTO.getCountCategory());
        allReportDTO.setCountRating(ratingCountDTO.getCountRating());
        allReportDTO.setCountUser(userCountDTO.getCountUser());


        return new ResponseEntity<>(allReportDTO, HttpStatus.OK);
    }

}
