package com.s6restapi.spring6restapi.controller;

import com.s6restapi.spring6restapi.entities.Beer;
import com.s6restapi.spring6restapi.entities.Customer;
import com.s6restapi.spring6restapi.model.BeerDTO;
import com.s6restapi.spring6restapi.model.CustomerDTO;
import com.s6restapi.spring6restapi.repositories.CustomerRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CustomerControllerIT {

    @Autowired
    CustomerController controller;

    @Autowired
    CustomerRepository customerRepository;

    @Test
    void testCustomerIdNotFound() {

        assertThrows(NotFoundException.class, () ->{ //ensure that this exception class is thrown

            controller.getCustomerById(UUID.randomUUID());

        });



    }

    @Test
    void testGetById() {
        Customer customer = customerRepository.findAll().get(0);
        CustomerDTO customerDTO = controller.getCustomerById(customer.getId());
        assertThat(customerDTO).isNotNull();
    }

    @Test
    void testListCustomer(){
        List<CustomerDTO> dtos = controller.listAllCustomers();

        assertThat(dtos.size()).isEqualTo(3);
    }

    @Rollback
    @Transactional
    @Test
    void testEmptyList() {

        customerRepository.deleteAll();

        List<CustomerDTO> dtos = controller.listAllCustomers();

        assertThat(dtos.size()).isEqualTo(0);
    }

}