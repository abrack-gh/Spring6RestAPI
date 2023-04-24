package com.s6restapi.spring6restapi.repositories;

import com.s6restapi.spring6restapi.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CustomerRepository extends JpaRepository<Customer, UUID> {
}
