package com.intercorp.api.customers.application.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BirthStatisticsResponse {

    private Integer month;
    private Integer year;
    private Long totalBirths;

}
