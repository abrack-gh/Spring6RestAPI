package com.s6restapi.spring6restapi.controller;

import com.s6restapi.spring6restapi.entities.Beer;
import com.s6restapi.spring6restapi.model.BeerDTO;
import com.s6restapi.spring6restapi.repositories.BeerRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BeerControllerIT {

    @Autowired
    BeerController controller;

    @Autowired
    BeerRepository beerRepository;

    @Test
    void testBeerIdNotFound() {

        assertThrows(NotFoundException.class, () ->{ //ensure that this exception class is thrown

            controller.getBeerById(UUID.randomUUID());

                });



    }

    @Test
    void testGetById() {

        Beer beer = beerRepository.findAll().get(0);

        BeerDTO dto = controller.getBeerById(beer.getId());

        assertThat(dto).isNotNull();
    }

    @Test
    void testListBeer(){
        List<BeerDTO> dtos = controller.listBeers();

        assertThat(dtos.size()).isEqualTo(3);
    }

    @Rollback
    @Transactional
    @Test
    void testEmptyList() {

        beerRepository.deleteAll();

        List<BeerDTO> dtos = controller.listBeers();

        assertThat(dtos.size()).isEqualTo(0);
    }
}