package com.intercorp.api.customers.application.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CustomerSearchRequest {

    @NotNull(message = "Filter type is required")
    private CustomerFilterType filterType;

    private String searchTerm;
}