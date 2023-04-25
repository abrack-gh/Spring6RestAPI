package com.s6restapi.spring6restapi.controller;

import com.s6restapi.spring6restapi.entities.Beer;
import com.s6restapi.spring6restapi.entities.Customer;
import com.s6restapi.spring6restapi.mappers.CustomerMapper;
import com.s6restapi.spring6restapi.model.BeerDTO;
import com.s6restapi.spring6restapi.model.CustomerDTO;
import com.s6restapi.spring6restapi.repositories.CustomerRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
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

    @Autowired
    CustomerMapper customerMapper;

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

    @Test
    void UpdateCustomerById() {

        Customer customer = customerRepository.findAll().get(0);
        CustomerDTO customerDTO = customerMapper.customerToCustomerDto(customer);

        customerDTO.setId(null);
        customerDTO.setVersion(null);

        final String customerName = "Updated";
        customerDTO.setCustomerName(customerName);

        ResponseEntity responseEntity = controller.updateCustomerByID(customer.getId(), customerDTO);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(204));


        Customer updatedCustomer = customerRepository.findById(customer.getId()).get();
        assertThat(updatedCustomer.getCustomerName()).isEqualTo(customerName);
    }

    @Test
    void testUpdateNotFound() {
        assertThrows(NotFoundException.class, () -> {
            controller.updateCustomerByID(UUID.randomUUID(), CustomerDTO.builder().build());
        });
    }

    @Test
    void saveNewCustomer() {

               CustomerDTO customerDTO = CustomerDTO.builder()
               .customerName("New Customer")
               .build();

        ResponseEntity responseEntity = controller.handlePost(customerDTO);

       assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(201));
        assertThat(responseEntity.getHeaders().getLocation()).isNotNull();

        String[] locationUUID = responseEntity.getHeaders().getLocation().getPath().split("/");
        UUID savedUUID = UUID.fromString(String.valueOf(locationUUID[4]));

        Customer customer = customerRepository.getReferenceById(savedUUID);
        assertThat(customer).isNotNull();

    }

    @Transactional
    @Rollback
    @Test
    void deleteByIdFound() {

        Customer customer = customerRepository.findAll().get(0);

        ResponseEntity responseEntity = controller.deleteCustomerById(customer.getId());
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(204));

        assertThat(customerRepository.findById(customer.getId()).isEmpty());


    }

    @Test
    void testDeleteNotFound() {

        assertThrows(NotFoundException.class, () -> {
            controller.deleteCustomerById(UUID.randomUUID());
        });
    }
}