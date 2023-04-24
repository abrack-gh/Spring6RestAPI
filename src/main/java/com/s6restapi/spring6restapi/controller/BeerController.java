package com.s6restapi.spring6restapi.controller;

import com.s6restapi.spring6restapi.model.BeerDTO;
import com.s6restapi.spring6restapi.service.BeerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@RestController
public class BeerController {

    public static final String BEER_PATH = "/api/v1/beer";
    public static final String BEER_PATH_ID = BEER_PATH + "/{beerId}";

    private final BeerService beerService;

    @PatchMapping(BEER_PATH_ID)
    public ResponseEntity updatePatchById(@PathVariable("beerId")UUID beerId, @RequestBody BeerDTO beer){

        beerService.updatePatchById(beerId, beer);

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @PutMapping(BEER_PATH_ID)
    public ResponseEntity updateById(@PathVariable("beerId")UUID beerId, @RequestBody BeerDTO beer) {

        beerService.updateBeerById(beerId, beer);

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping(BEER_PATH_ID)
    public ResponseEntity deleteById(@PathVariable("beerId")UUID beerId){

        beerService.deleteById(beerId);

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @PostMapping
    public ResponseEntity handlePost(@RequestBody BeerDTO beer){

    BeerDTO savedBeer = beerService.saveBeer(beer);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", BEER_PATH + savedBeer.getId().toString());

    return new ResponseEntity(headers, HttpStatus.CREATED);

    }


    @RequestMapping(method = RequestMethod.GET)
    public List<BeerDTO> listBeers(){

        return beerService.listBeers();

    }

    @RequestMapping(value = "{beerId}", method = RequestMethod.GET)
    public BeerDTO getBeerById(@PathVariable("beerId") UUID beerId){

        log.debug("Test controller 123uuuu");

        return beerService.getBeerById(beerId).orElseThrow(NotFoundException::new);
    }


}
