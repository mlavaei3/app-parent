package com.lava.server.customer.mapper;

import com.lava.server.customer.model.entity.Customer;
import com.lava.server.openapi.model.CustomerDTO;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CustomerMapper {
    CustomerDTO customerToCustomerDTO(Customer customer);
    Customer customerDTOToCustomer(CustomerDTO customerDTO);
}
