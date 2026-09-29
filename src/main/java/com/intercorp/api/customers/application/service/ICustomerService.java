package com.intercorp.api.customers.application.service;

import com.intercorp.api.customers.application.dto.CustomerIndicatorsResponse;
import com.intercorp.api.customers.application.dto.CustomerRequest;
import com.intercorp.api.customers.application.dto.CustomerResponse;
import com.intercorp.api.customers.application.dto.CustomerSearchRequest;

import java.util.List;

public interface ICustomerService {

    CustomerResponse createCustomer(CustomerRequest customerRequest);
    List<CustomerResponse> getCustomers(CustomerSearchRequest customerSearchRequest);
    CustomerIndicatorsResponse getCustomerIndicators();
}
