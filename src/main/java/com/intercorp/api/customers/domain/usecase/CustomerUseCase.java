package com.intercorp.api.customers.domain.usecase;

import com.intercorp.api.customers.domain.exception.CustomerAlreadyExistsException;
import com.intercorp.api.customers.domain.model.Customer;
import com.intercorp.api.customers.domain.port.in.ICustomerServicePort;
import com.intercorp.api.customers.domain.port.out.ICustomerPersistencePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomerUseCase implements ICustomerServicePort {

    private final ICustomerPersistencePort customerPersistencePort;

    @Override
    public Customer createCustomer(Customer customer) {

        customerPersistencePort.getCustomerByDni(customer.getDni())
                .ifPresent(existing -> {
                    throw new CustomerAlreadyExistsException(
                            "A customer with DNI " + customer.getDni() + " already exists"
                    );
                });

        customerPersistencePort.getCustomerByEmail(customer.getEmail())
                .ifPresent(existing -> {
                    throw new CustomerAlreadyExistsException(
                            "A customer with email " + customer.getEmail() + " already exists"
                    );
                });

        return customerPersistencePort.createCustomer(customer);
    }

    @Override
    public List<Customer> getAllCustomers() {
        return customerPersistencePort.getAllCustomers();
    }

    @Override
    public Optional<Customer> getCustomerByDni(String dni) {
        return customerPersistencePort.getCustomerByDni(dni);
    }

    @Override
    public Optional<Customer> getCustomerByEmail(String email) {
        return customerPersistencePort.getCustomerByEmail(email);
    }

    @Override
    public List<Customer> getCustomersByDniOrEmail(String searchTerm) {
        return customerPersistencePort.getCustomersByDniOrEmail(searchTerm);
    }


}