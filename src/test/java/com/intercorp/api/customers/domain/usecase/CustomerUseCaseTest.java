package com.intercorp.api.customers.domain.usecase;

import com.intercorp.api.customers.domain.model.Customer;
import com.intercorp.api.customers.domain.port.out.ICustomerPersistencePort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CustomerUseCaseTest {

    @Mock
    private ICustomerPersistencePort customerPersistencePort;

    @InjectMocks
    private CustomerUseCase customerUseCase;

    private Customer customer;

    @BeforeEach
    void setUp() {

        customer = Customer.builder()
                .id(1L)
                .firstName("Juan")
                .lastName("Perez")
                .email("juan@gmail.com")
                .dni("12345678")
                .createdAt(LocalDateTime.now())
                .birthDate(LocalDate.of(1998, 5, 15))
                .build();
    }

    @Test
    void shouldCreateCustomerSuccessfully() {

        // Arrange
        when(customerPersistencePort.getCustomerByDni(customer.getDni()))
                .thenReturn(Optional.empty());

        when(customerPersistencePort.getCustomerByEmail(customer.getEmail()))
                .thenReturn(Optional.empty());

        when(customerPersistencePort.createCustomer(customer))
                .thenReturn(customer);

        // Act
        Customer result = customerUseCase.createCustomer(customer);

        // Assert
        assertNotNull(result);

        assertEquals(
                customer.getDni(),
                result.getDni()
        );

        assertEquals(
                customer.getEmail(),
                result.getEmail()
        );

        assertEquals(
                customer.getFirstName(),
                result.getFirstName()
        );

        verify(customerPersistencePort)
                .getCustomerByDni(customer.getDni());

        verify(customerPersistencePort)
                .getCustomerByEmail(customer.getEmail());

        verify(customerPersistencePort)
                .createCustomer(customer);
    }
}