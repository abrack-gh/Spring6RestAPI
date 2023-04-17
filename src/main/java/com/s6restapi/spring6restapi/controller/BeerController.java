package com.s6restapi.spring6restapi.controller;

import com.s6restapi.spring6restapi.model.Beer;
import com.s6restapi.spring6restapi.service.BeerService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;

import java.util.UUID;

@Slf4j
@AllArgsConstructor
@Controller
public class BeerController {

    private final BeerService beerService;

    public Beer getBeerById(UUID id){

        log.debug("Test controller");

        return beerService.getBeerById(id);
    }


}
