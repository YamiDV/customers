package com.intercorp.api.customers.application.mapper;

import com.intercorp.api.customers.application.dto.CustomerRequest;
import com.intercorp.api.customers.application.dto.CustomerResponse;
import com.intercorp.api.customers.domain.model.Customer;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CustomerResponseMapper {

    CustomerResponse toCustomerResponse(Customer customer);
}
