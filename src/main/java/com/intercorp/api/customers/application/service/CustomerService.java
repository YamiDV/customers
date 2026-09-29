package com.intercorp.api.customers.application.service;

import com.intercorp.api.customers.application.dto.*;
import com.intercorp.api.customers.application.mapper.CustomerRequestMapper;
import com.intercorp.api.customers.application.mapper.CustomerResponseMapper;
import com.intercorp.api.customers.domain.model.Customer;
import com.intercorp.api.customers.domain.port.in.ICustomerServicePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.YearMonth;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomerService implements ICustomerService {

    private final ICustomerServicePort customerServicePort;
    private final CustomerResponseMapper customerResponseMapper;
    private final CustomerRequestMapper customerRequestMapper;


    @Override
    public CustomerResponse createCustomer(CustomerRequest customerRequest) {

        Customer customer = customerRequestMapper.toCustomer(customerRequest);
        Customer createdCustomer = customerServicePort.createCustomer(customer);

        return customerResponseMapper.toCustomerResponse(createdCustomer);
    }

    @Override
    public List<CustomerResponse> getCustomers(CustomerSearchRequest request) {

        List<Customer> customers;

        switch (request.getFilterType()) {

            case DNI -> customers = customerServicePort
                    .getCustomerByDni(request.getSearchTerm())
                    .map(List::of)
                    .orElseGet(List::of);

            case EMAIL -> customers = customerServicePort
                    .getCustomerByEmail(request.getSearchTerm())
                    .map(List::of)
                    .orElseGet(List::of);

            case ALL -> {

                if (request.getSearchTerm() == null
                        || request.getSearchTerm().isBlank()) {
                    customers = customerServicePort.getAllCustomers();

                } else {

                    customers = customerServicePort.getCustomersByDniOrEmail(request.getSearchTerm());
                }
            }

            default -> throw new IllegalArgumentException(
                    "Invalid filter type"
            );
        }

        return customers.stream()
                .map(customerResponseMapper::toCustomerResponse)
                .toList();
    }

    @Override
    public CustomerIndicatorsResponse getCustomerIndicators() {

        List<Customer> customers =
                customerServicePort.getAllCustomers();

        if (customers.isEmpty()) {
            return CustomerIndicatorsResponse.builder()
                    .birthsByPeriod(List.of())
                    .highestBirthPeriod(null)
                    .lowestBirthPeriod(null)
                    .monthlyBirthRates(List.of())
                    .build();
        }

        long totalCustomers = customers.size();



        //CANTIDAD DE NACIMIENTOS POR MES / AÑO

        Map<YearMonth, Long> birthsByPeriod =
                customers.stream()
                        .collect(Collectors.groupingBy(
                                customer ->
                                        YearMonth.from(customer.getBirthDate()),
                                Collectors.counting()
                        ));

        List<BirthStatisticsResponse> statistics =
                birthsByPeriod.entrySet()
                        .stream()
                        .map(entry -> {

                            YearMonth period = entry.getKey();
                            Long totalBirths = entry.getValue();

                            return BirthStatisticsResponse.builder()
                                    .month(period.getMonthValue())
                                    .year(period.getYear())
                                    .totalBirths(totalBirths)
                                    .build();
                        })
                        .sorted(
                                Comparator
                                        .comparing(BirthStatisticsResponse::getYear)
                                        .thenComparing(BirthStatisticsResponse::getMonth)
                        )
                        .toList();


        //PERIODO CON MAYOR CANTIDAD

        BirthStatisticsResponse highest =
                statistics.stream()
                        .max(
                                Comparator.comparing(
                                        BirthStatisticsResponse::getTotalBirths
                                )
                        )
                        .orElse(null);

        // PERIODO CON MENOR CANTIDAD
        BirthStatisticsResponse lowest =
                statistics.stream()
                        .min(
                                Comparator.comparing(
                                        BirthStatisticsResponse::getTotalBirths
                                )
                        )
                        .orElse(null);


        //TASA DE NATALIDAD POR MES

        Map<Integer, Long> birthsByMonth =
                customers.stream()
                        .collect(Collectors.groupingBy(
                                customer ->
                                        customer.getBirthDate().getMonthValue(),
                                Collectors.counting()
                        ));

        List<MonthlyBirthRateResponse> monthlyBirthRates =
                birthsByMonth.entrySet()
                        .stream()
                        .map(entry -> {

                            Integer month = entry.getKey();
                            Long totalBirths = entry.getValue();

                            double birthRate =
                                    (totalBirths * 100.0)
                                            / totalCustomers;

                            return MonthlyBirthRateResponse.builder()
                                    .month(month)
                                    .totalBirths(totalBirths)
                                    .birthRate(
                                            Math.round(
                                                    birthRate * 100.0
                                            ) / 100.0
                                    )
                                    .build();
                        })
                        .sorted(
                                Comparator.comparing(
                                        MonthlyBirthRateResponse::getMonth
                                )
                        )
                        .toList();


        return CustomerIndicatorsResponse.builder()
                .birthsByPeriod(statistics)
                .highestBirthPeriod(highest)
                .lowestBirthPeriod(lowest)
                .monthlyBirthRates(monthlyBirthRates)
                .build();
    }
}
