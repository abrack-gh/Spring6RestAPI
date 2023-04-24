package com.s6restapi.spring6restapi.controller;

import com.s6restapi.spring6restapi.model.CustomerDTO;
import com.s6restapi.spring6restapi.service.CustomerService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Slf4j
@AllArgsConstructor
@RestController
public class CustomerController {

    private final CustomerService customerService;

    public static final String CUSTOMER_PATH = "/api/v1/customer";
    public static final String CUSTOMER_PATH_ID = CUSTOMER_PATH + "/{customerId}";

    @PatchMapping(CUSTOMER_PATH_ID)
    public ResponseEntity updatePatchById(@PathVariable("customerId")UUID customerId, @RequestBody CustomerDTO customer){

        customerService.updateCustomerById(customerId, customer);

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping(CUSTOMER_PATH_ID)
    public ResponseEntity deleteById(@PathVariable("customerId")UUID customerId){

        customerService.deleteById(customerId);

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @PutMapping(CUSTOMER_PATH_ID)
    public ResponseEntity updateById(@PathVariable("customerId")UUID customerId, @RequestBody CustomerDTO customer){

        customerService.updateCustomerById(customerId, customer);

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @PostMapping
    public ResponseEntity postCustomer(@RequestBody CustomerDTO customer){

        CustomerDTO savedCustomer = customerService.saveCustomer(customer);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", CUSTOMER_PATH + savedCustomer.getId().toString());

        return new ResponseEntity(headers, HttpStatus.CREATED);

    }

    @RequestMapping(method = RequestMethod.GET)
    public List<CustomerDTO> listCustomer(){
        return customerService.listCustomer();
    }

    @RequestMapping(value = "{customerId}", method = RequestMethod.GET)
    public CustomerDTO getCustomerById(@PathVariable("customerId") UUID customerId){

        return customerService.getCustomerById(customerId).orElseThrow(NotFoundException::new);
    }
}
