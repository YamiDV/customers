package com.intercorp.api.customers.infrastructure.in.rest;

import com.intercorp.api.customers.application.dto.*;
import com.intercorp.api.customers.application.service.ICustomerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customers")
@RequiredArgsConstructor
@Tag(
        name = "Customers",
        description = "Operations for customer registration, search and indicators"
)
public class CustomerRestController {

    private final ICustomerService customerService;

    @Operation(
            summary = "Register a new customer",
            description = "Creates a new customer with personal information such as name, email, DNI and birth date."
    )
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

    @Operation(
            summary = "Search customers",
            description = """
                    Returns customers using an optional filter.
                    
                    Filter types:
                    - ALL: Returns all customers when searchTerm is empty, or searches by DNI/email when searchTerm is provided.
                    - DNI: Searches customers by DNI.
                    - EMAIL: Searches customers by email.
                    """
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Customers retrieved successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid filter parameters"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
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

    @Operation(
            summary = "Get customer indicators",
            description = """
                    Returns customer birth statistics grouped by month and year,
                    including the period with the highest number of births,
                    the period with the lowest number of births,
                    and the birth rate for each period.
                    """
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Customer indicators retrieved successfully"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/indicators")
    public ResponseEntity<CustomerIndicatorsResponse> getCustomerIndicators() {
        CustomerIndicatorsResponse indicators = customerService.getCustomerIndicators();
        return ResponseEntity.ok(indicators);
    }
}
