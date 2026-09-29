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

        customers.add(CustomerEntity.builder()
                .id(4L)
                .firstName("Ana")
                .lastName("Torres")
                .email("ana.torres@gmail.com")
                .dni("45678123")
                .createdAt(LocalDateTime.now())
                .birthDate(LocalDate.of(1999, 3, 12))
                .build());

        customers.add(CustomerEntity.builder()
                .id(5L)
                .firstName("Luis")
                .lastName("Gomez")
                .email("luis.gomez@gmail.com")
                .dni("56781234")
                .createdAt(LocalDateTime.now())
                .birthDate(LocalDate.of(1999, 3, 25))
                .build());

        customers.add(CustomerEntity.builder()
                .id(6L)
                .firstName("Lucia")
                .lastName("Fernandez")
                .email("lucia.fernandez@gmail.com")
                .dni("67812345")
                .createdAt(LocalDateTime.now())
                .birthDate(LocalDate.of(2000, 8, 14))
                .build());

        customers.add(CustomerEntity.builder()
                .id(7L)
                .firstName("Diego")
                .lastName("Castro")
                .email("diego.castro@gmail.com")
                .dni("78123456")
                .createdAt(LocalDateTime.now())
                .birthDate(LocalDate.of(2000, 8, 30))
                .build());

        customers.add(CustomerEntity.builder()
                .id(8L)
                .firstName("Valeria")
                .lastName("Mendoza")
                .email("valeria.mendoza@gmail.com")
                .dni("81234567")
                .createdAt(LocalDateTime.now())
                .birthDate(LocalDate.of(2000, 8, 7))
                .build());

        customers.add(CustomerEntity.builder()
                .id(9L)
                .firstName("Jorge")
                .lastName("Salazar")
                .email("jorge.salazar@gmail.com")
                .dni("92345678")
                .createdAt(LocalDateTime.now())
                .birthDate(LocalDate.of(2001, 1, 18))
                .build());

        customers.add(CustomerEntity.builder()
                .id(10L)
                .firstName("Camila")
                .lastName("Rojas")
                .email("camila.rojas@gmail.com")
                .dni("93456781")
                .createdAt(LocalDateTime.now())
                .birthDate(LocalDate.of(2001, 1, 9))
                .build());

        customers.add(CustomerEntity.builder()
                .id(11L)
                .firstName("Pedro")
                .lastName("Vargas")
                .email("pedro.vargas@gmail.com")
                .dni("94567812")
                .createdAt(LocalDateTime.now())
                .birthDate(LocalDate.of(2002, 11, 3))
                .build());
        customers.add(CustomerEntity.builder()
                .id(12L)
                .firstName("Andrea")
                .lastName("Paredes")
                .email("andrea.paredes@gmail.com")
                .dni("95678123")
                .createdAt(LocalDateTime.now())
                .birthDate(LocalDate.of(1998, 5, 28))
                .build());

        customers.add(CustomerEntity.builder()
                .id(13L)
                .firstName("Renzo")
                .lastName("Campos")
                .email("renzo.campos@gmail.com")
                .dni("96781234")
                .createdAt(LocalDateTime.now())
                .birthDate(LocalDate.of(1999, 7, 16))
                .build());

        customers.add(CustomerEntity.builder()
                .id(14L)
                .firstName("Daniela")
                .lastName("Soto")
                .email("daniela.soto@gmail.com")
                .dni("97812345")
                .createdAt(LocalDateTime.now())
                .birthDate(LocalDate.of(2000, 8, 22))
                .build());

        customers.add(CustomerEntity.builder()
                .id(15L)
                .firstName("Miguel")
                .lastName("Herrera")
                .email("miguel.herrera@gmail.com")
                .dni("98123456")
                .createdAt(LocalDateTime.now())
                .birthDate(LocalDate.of(2001, 4, 11))
                .build());

        customers.add(CustomerEntity.builder()
                .id(16L)
                .firstName("Sofia")
                .lastName("Navarro")
                .email("sofia.navarro@gmail.com")
                .dni("99234567")
                .createdAt(LocalDateTime.now())
                .birthDate(LocalDate.of(2002, 11, 19))
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