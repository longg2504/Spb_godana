package com.godana.domain.dto.report;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AllReportDTO {
    IReportDTO dayPlaceReport;
    IReportDTO monthPlaceReport;
    List<IYearReportDTO> yearPlacesReport;
    List<I6MonthAgoReportDTO> placeSixMonthAgoReport;
    List<I6MonthAgoReportDTO> userSixMonthAgoReport;
    List<ICountPlaceByCateReportDTO> countPlacesByCateReport;
    Long countUser;
    Long countPlace;
    Long countCategory;
    Long countPost;
    Long countRating;
}
