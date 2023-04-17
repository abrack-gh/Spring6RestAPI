package com.s6restapi.spring6restapi.model;

import lombok.Data;

import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class Beer {

    private UUID id;
    private Integer version;
    private String beerName;
    private BeerStyle beerStyle;
    private String upc;
    private Integer quantityOnHand;
    private BigInteger price;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
}
