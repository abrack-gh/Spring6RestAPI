package com.s6restapi.spring6restapi.service;

import com.s6restapi.spring6restapi.model.BeerDTO;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BeerService {

    List<BeerDTO> listBeers();

    Optional<BeerDTO> getBeerById(UUID id);

    BeerDTO saveBeer(BeerDTO beer);

    void updateBeerById(UUID beerId, BeerDTO beer);

    void deleteById(UUID beerId);

    void updatePatchById(UUID beerId, BeerDTO beer);
}
