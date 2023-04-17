package com.s6restapi.spring6restapi.service;

import com.s6restapi.spring6restapi.model.Customer;

import java.util.List;
import java.util.UUID;

public interface CustomerService {

    List<Customer> listCustomer();

    Customer getCustomerById(UUID id);
}
