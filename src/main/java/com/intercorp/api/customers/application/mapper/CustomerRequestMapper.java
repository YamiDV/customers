package com.intercorp.api.customers.application.mapper;

import com.intercorp.api.customers.application.dto.CustomerRequest;
import com.intercorp.api.customers.domain.model.Customer;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CustomerRequestMapper {

    Customer toCustomer(CustomerRequest customerRequest);
}
