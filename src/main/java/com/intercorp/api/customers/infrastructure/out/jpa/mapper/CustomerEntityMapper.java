package com.intercorp.api.customers.infrastructure.out.jpa.mapper;
import com.intercorp.api.customers.domain.model.Customer;
import com.intercorp.api.customers.infrastructure.out.jpa.entity.CustomerEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CustomerEntityMapper {
    CustomerEntity toCustomerEntity(Customer customer);
    Customer toCustomer(CustomerEntity customerEntity);
}
