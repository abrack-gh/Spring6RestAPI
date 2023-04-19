package com.s6restapi.spring6restapi.service;

import com.s6restapi.spring6restapi.model.Beer;
import com.s6restapi.spring6restapi.model.Customer;

import java.util.List;
import java.util.UUID;

public interface CustomerService {

    List<Customer> listCustomer();

    Customer getCustomerById(UUID id);

    Customer saveCustomer(Customer customer);

    void updateCustomerById(UUID customerId, Customer customer);
}
