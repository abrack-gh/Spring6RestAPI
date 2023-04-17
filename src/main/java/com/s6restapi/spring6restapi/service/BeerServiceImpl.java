package com.s6restapi.spring6restapi.service;

import com.s6restapi.spring6restapi.model.Beer;
import com.s6restapi.spring6restapi.model.BeerStyle;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Slf4j
public class BeerServiceImpl implements BeerService {
    @Override
    public Beer getBeerById(UUID id) {

        log.debug("Log ex");

        return Beer.builder()
                .id(id)
                .version(1)
                .beerName("BrackBeer")
                .beerStyle(BeerStyle.PALE_ALE)
                .upc("123")
                .price(new BigDecimal("1.50"))
                .quantityOnHand(100)
                .createdDate(LocalDateTime.now())
                .updatedDate(LocalDateTime.now())
                .build();
    }
}
