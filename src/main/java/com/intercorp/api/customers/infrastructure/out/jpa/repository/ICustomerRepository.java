package com.intercorp.api.customers.infrastructure.out.jpa.repository;

import com.intercorp.api.customers.domain.model.Customer;
import com.intercorp.api.customers.infrastructure.out.jpa.entity.CustomerEntity;

import java.util.List;
import java.util.Optional;

public interface ICustomerRepository {

    CustomerEntity save(CustomerEntity customer);

    List<CustomerEntity> findAll();

    Optional<CustomerEntity> findByDni(String dni);

    Optional<CustomerEntity> findByEmail(String email);

    List<CustomerEntity> findByDniOrEmail(String searchTerm);

}