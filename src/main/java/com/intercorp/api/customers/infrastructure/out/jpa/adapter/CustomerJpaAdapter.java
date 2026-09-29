package com.intercorp.api.customers.infrastructure.out.jpa.adapter;

import com.intercorp.api.customers.domain.model.Customer;
import com.intercorp.api.customers.domain.port.out.ICustomerPersistencePort;
import com.intercorp.api.customers.infrastructure.out.jpa.entity.CustomerEntity;
import com.intercorp.api.customers.infrastructure.out.jpa.mapper.CustomerEntityMapper;
import com.intercorp.api.customers.infrastructure.out.jpa.repository.ICustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class CustomerJpaAdapter implements ICustomerPersistencePort {

    private final ICustomerRepository customerRepository;
    private final CustomerEntityMapper customerEntityMapper;

    @Override
    public Customer createCustomer(Customer customer) {
        CustomerEntity customerEntity =
                customerEntityMapper.toCustomerEntity(customer);

        CustomerEntity savedCustomer =
                customerRepository.save(customerEntity);

        return customerEntityMapper.toCustomer(savedCustomer);
    }

    @Override
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll()
                .stream()
                .map(customerEntityMapper::toCustomer)
                .toList();
    }

    @Override
    public Optional<Customer> getCustomerByDni(String dni) {
        return customerRepository.findByDni(dni)
                .map(customerEntityMapper::toCustomer);
    }

    @Override
    public Optional<Customer> getCustomerByEmail(String email) {
        return customerRepository.findByEmail(email)
                .map(customerEntityMapper::toCustomer);
    }

    @Override
    public List<Customer> getCustomersByDniOrEmail(String searchTerm) {
        return customerRepository.findByDniOrEmail(searchTerm)
                .stream()
                .map(customerEntityMapper::toCustomer)
                .toList();
    }

}
