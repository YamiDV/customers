package com.intercorp.api.customers.application.service;

import com.intercorp.api.customers.application.dto.CustomerIndicatorsResponse;
import com.intercorp.api.customers.application.dto.CustomerRequest;
import com.intercorp.api.customers.application.dto.CustomerResponse;
import com.intercorp.api.customers.application.dto.CustomerSearchRequest;
import com.intercorp.api.customers.application.mapper.CustomerRequestMapper;
import com.intercorp.api.customers.application.mapper.CustomerResponseMapper;
import com.intercorp.api.customers.domain.model.Customer;
import com.intercorp.api.customers.domain.port.in.ICustomerServicePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerService implements ICustomerService {

    private final ICustomerServicePort customerServicePort;
    private final CustomerResponseMapper customerResponseMapper;
    private final CustomerRequestMapper customerRequestMapper;


    @Override
    public CustomerResponse createCustomer(CustomerRequest customerRequest) {

        Customer customer = customerRequestMapper.toCustomer(customerRequest);
        Customer createdCustomer = customerServicePort.createCustomer(customer);

        return customerResponseMapper.toCustomerResponse(createdCustomer);
    }

    @Override
    public List<CustomerResponse> getCustomers(CustomerSearchRequest request) {

        List<Customer> customers;

        switch (request.getFilterType()) {

            case DNI -> customers = customerServicePort
                    .getCustomerByDni(request.getSearchTerm())
                    .map(List::of)
                    .orElseGet(List::of);

            case EMAIL -> customers = customerServicePort
                    .getCustomerByEmail(request.getSearchTerm())
                    .map(List::of)
                    .orElseGet(List::of);

            case ALL -> {

                if (request.getSearchTerm() == null
                        || request.getSearchTerm().isBlank()) {
                    customers = customerServicePort.getAllCustomers();

                } else {

                    customers = customerServicePort.getCustomersByDniOrEmail(request.getSearchTerm());
                }
            }

            default -> throw new IllegalArgumentException(
                    "Invalid filter type"
            );
        }

        return customers.stream()
                .map(customerResponseMapper::toCustomerResponse)
                .toList();
    }

    @Override
    public CustomerIndicatorsResponse getCustomerIndicators() {
        return null;
    }
}
