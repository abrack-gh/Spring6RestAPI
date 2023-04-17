package com.s6restapi.spring6restapi.service;

import com.s6restapi.spring6restapi.model.Beer;

import java.util.UUID;

public interface BeerService {

    Beer getBeerById(UUID id);
}
