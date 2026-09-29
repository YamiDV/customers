package com.intercorp.api.customers.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Customer birth statistics response")
public class CustomerIndicatorsResponse {

    @Schema(description = "Birth statistics grouped by month and year")
    private List<BirthStatisticsResponse> birthsByPeriod;
    @Schema(description = "Period with the highest number of customer births")
    private BirthStatisticsResponse highestBirthPeriod;
    @Schema(description = "Period with the lowest number of customer births")
    private BirthStatisticsResponse lowestBirthPeriod;

    private List<MonthlyBirthRateResponse> monthlyBirthRates;

}