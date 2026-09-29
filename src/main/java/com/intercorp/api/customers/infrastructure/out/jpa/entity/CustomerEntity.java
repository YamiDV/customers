package com.intercorp.api.customers.infrastructure.out.jpa.entity;


import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;


//@Entity
//@Table(name = "customers")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerEntity {

    //@Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //@Column(name = "first_name", nullable = false, length = 100)
    private String firstName;

    //@Column(name = "last_name", nullable = false, length = 100)
    private String lastName;

    //@Column(nullable = false, length = 150)
    private String email;

    //@Column(nullable = false, length = 8)
    private String dni;

    //@Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    //@Column(name = "birth_date", nullable = false)
    private LocalDate birthDate;
}
