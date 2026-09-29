package com.intercorp.api.customers.application.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MonthlyBirthRateResponse {

    private Integer month;

    private Long totalBirths;

    private Double birthRate;
}