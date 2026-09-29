package com.intercorp.api.customers.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CustomerFilterType {

    ALL(0),
    DNI(1),
    EMAIL(2);

    private final int code;
}