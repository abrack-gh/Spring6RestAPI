package com.s6restapi.spring6restapi.repositories;

import com.s6restapi.spring6restapi.entities.Beer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface BeerRepository extends JpaRepository<Beer, UUID> {
}
