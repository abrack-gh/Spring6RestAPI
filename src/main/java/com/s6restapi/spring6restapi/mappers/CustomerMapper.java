package com.s6restapi.spring6restapi.mappers;

import com.s6restapi.spring6restapi.entities.Customer;
import com.s6restapi.spring6restapi.model.CustomerDTO;
import org.mapstruct.Mapper;

@Mapper
public interface CustomerMapper {
    CustomerDTO CustomerDTOtoCustomer(CustomerDTO dto);

    Customer customerToCustomerDTO(Customer customer);
}
