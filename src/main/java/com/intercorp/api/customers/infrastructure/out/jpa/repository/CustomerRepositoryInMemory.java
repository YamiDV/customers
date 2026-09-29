package com.intercorp.api.customers.infrastructure.out.jpa.repository;

import com.intercorp.api.customers.infrastructure.out.jpa.entity.CustomerEntity;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class CustomerRepositoryInMemory implements ICustomerRepository {

    private final List<CustomerEntity> customers = new ArrayList<>();

    public CustomerRepositoryInMemory() {

        customers.add(CustomerEntity.builder()
                .id(1L)
                .firstName("Juan")
                .lastName("Perez")
                .email("juan@gmail.com")
                .dni("12345678")
                .createdAt(LocalDateTime.now())
                .birthDate(LocalDate.of(1998, 5, 15))
                .build());

        customers.add(CustomerEntity.builder()
                .id(2L)
                .firstName("Maria")
                .lastName("Lopez")
                .email("maria@gmail.com")
                .dni("87654321")
                .createdAt(LocalDateTime.now())
                .birthDate(LocalDate.of(2000, 8, 20))
                .build());

        customers.add(CustomerEntity.builder()
                .id(3L)
                .firstName("Carlos")
                .lastName("Ramirez")
                .email("carlos@gmail.com")
                .dni("45678912")
                .createdAt(LocalDateTime.now())
                .birthDate(LocalDate.of(1998, 5, 10))
                .build());
    }

    @Override
    public CustomerEntity save(CustomerEntity customer) {
        customer.setId((long) (customers.size() + 1));
        customer.setCreatedAt(LocalDateTime.now());

        customers.add(customer);

        return customer;
    }

    @Override
    public List<CustomerEntity> findAll() {
        return customers;
    }

    @Override
    public Optional<CustomerEntity> findByDni(String dni) {
        return customers.stream()
                .filter(customer ->
                        customer.getDni().contains(dni))
                .findFirst();
    }

    @Override
    public Optional<CustomerEntity> findByEmail(String email) {
        return customers.stream()
                .filter(customer ->
                        customer.getEmail()
                                .toLowerCase()
                                .contains(email.toLowerCase()))
                .findFirst();
    }
    @Override
    public List<CustomerEntity> findByDniOrEmail(String searchTerm) {
        return customers.stream()
                .filter(customer ->
                        customer.getDni().contains(searchTerm)
                                || customer.getEmail()
                                .toLowerCase()
                                .contains(searchTerm.toLowerCase())
                )
                .toList();
    }

}