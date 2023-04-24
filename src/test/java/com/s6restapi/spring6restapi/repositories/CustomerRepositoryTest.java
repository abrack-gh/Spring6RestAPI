package com.s6restapi.spring6restapi.repositories;

import com.s6restapi.spring6restapi.entities.Beer;
import com.s6restapi.spring6restapi.entities.Customer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class CustomerRepositoryTest {

    @Autowired
    CustomerRepository customerRepository;

    @Test
    void testSave(){
        Customer savedCustomer = customerRepository.save(Customer.builder()
                .customerName("Customer1")
                .build());

        assertThat(savedCustomer).isNotNull();
        assertThat(savedCustomer.getId()).isNotNull();
    }

}