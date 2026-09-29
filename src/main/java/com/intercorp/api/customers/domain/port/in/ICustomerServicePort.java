package com.intercorp.api.customers.domain.port.in;

import com.intercorp.api.customers.domain.model.Customer;

import java.util.List;
import java.util.Optional;

public interface ICustomerServicePort {

    Customer createCustomer(Customer customer);
    List<Customer> getAllCustomers();
    Optional<Customer> getCustomerByDni(String dni);
    Optional<Customer> getCustomerByEmail(String email);
    List<Customer> getCustomersByDniOrEmail(String searchTerm);
}
