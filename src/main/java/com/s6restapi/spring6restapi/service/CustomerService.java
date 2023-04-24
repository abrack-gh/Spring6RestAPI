package com.s6restapi.spring6restapi.service;

import com.s6restapi.spring6restapi.model.CustomerDTO;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CustomerService {

    List<CustomerDTO> listCustomer();

    Optional<CustomerDTO> getCustomerById(UUID id);

    CustomerDTO saveCustomer(CustomerDTO customer);

    void updateCustomerById(UUID customerId, CustomerDTO customer);

    void deleteById(UUID customerId);

    void updatePatchById(UUID customerId, CustomerDTO customer);
}
