package com.intercorp.api.customers.infrastructure.in.rest;

import com.intercorp.api.customers.application.dto.CustomerFilterType;
import com.intercorp.api.customers.application.dto.CustomerRequest;
import com.intercorp.api.customers.application.dto.CustomerResponse;
import com.intercorp.api.customers.application.dto.CustomerSearchRequest;
import com.intercorp.api.customers.application.service.ICustomerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customers")
@RequiredArgsConstructor
public class CustomerRestController {

    private final ICustomerService customerService;

    @Operation(summary = "Register a new Customer")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Customer created", content = @Content),
            @ApiResponse(responseCode = "409", description = "Customer already exists", content = @Content),
            @ApiResponse(responseCode = "400", description = "Invalid request payload", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    @PostMapping("/register")
    public ResponseEntity<CustomerResponse> createCustomer(@Valid @RequestBody CustomerRequest customerRequest) {
        CustomerResponse customerResponse = customerService.createCustomer(customerRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(customerResponse);
    }

    @GetMapping("/find")
    public ResponseEntity<List<CustomerResponse>> getCustomers(
            @RequestParam(defaultValue = "ALL") CustomerFilterType filterType,
            @RequestParam(required = false) String searchTerm) {

        CustomerSearchRequest request =
                new CustomerSearchRequest(filterType, searchTerm);

        List<CustomerResponse> customers =
                customerService.getCustomers(request);

        return ResponseEntity.ok(customers);
    }
}
