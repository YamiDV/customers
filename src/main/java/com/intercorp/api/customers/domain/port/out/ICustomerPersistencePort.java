package com.intercorp.api.customers.domain.port.out;

import com.intercorp.api.customers.domain.model.Customer;

import java.util.List;
import java.util.Optional;

public interface ICustomerPersistencePort {
    Customer createCustomer(Customer customer);
    List<Customer> getAllCustomers();
    Optional<Customer> getCustomerByDni(String dni);
    Optional<Customer> getCustomerByEmail(String email);
    List<Customer> getCustomersByDniOrEmail(String searchTerm);
}