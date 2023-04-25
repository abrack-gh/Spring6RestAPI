package com.s6restapi.spring6restapi.controller;

import com.s6restapi.spring6restapi.entities.Beer;
import com.s6restapi.spring6restapi.mappers.BeerMapper;
import com.s6restapi.spring6restapi.model.BeerDTO;
import com.s6restapi.spring6restapi.repositories.BeerRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.Rollback;

import java.util.Arrays;
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

    @Autowired
    BeerMapper beerMapper;

//    @Rollback
//    @Transactional
//    @Test
//    void saveNewBeerTest() {
//        BeerDTO beerDTO = BeerDTO.builder()
//                .beerName("New Beer")
//                .build();
//
//        ResponseEntity responseEntity = controller.handlePost(beerDTO);
//
//        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(201));
//        assertThat(responseEntity.getHeaders().getLocation()).isNotNull();
//
//        String[] locationUUID = responseEntity.getHeaders().getLocation().getPath().split("/");
//        UUID savedUUID = UUID.fromString(String.valueOf(locationUUID.length));
//
//        Beer beer = beerRepository.getReferenceById(savedUUID);
//        assertThat(beer).isNotNull();
//    }


    @Test
    void testDeleteByIDNotFound() {

        assertThrows(NotFoundException.class, () ->{

            controller.deleteById(UUID.randomUUID());

        });
    }

    @Transactional
    @Rollback
    @Test
    void deleteByIdFound() {

        Beer beer = beerRepository.findAll().get(0);

        ResponseEntity responseEntity = controller.deleteById(beer.getId());
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(204));

        assertThat(beerRepository.findById(beer.getId()).isEmpty());

//        Beer foundBeer = beerRepository.findById(beer.getId()).get();
//        assertThat(foundBeer).isNull();


    }

    @Transactional
    @Rollback
    @Test
    void testUpdateNotFound() {

        assertThrows(NotFoundException.class, () ->{

            controller.updateById(UUID.randomUUID(), BeerDTO.builder().build());

        });
    }

    @Test
    void UpdateBeer() {

        Beer beer = beerRepository.findAll().get(0);
        BeerDTO beerDTO = beerMapper.beerToBeerDto(beer);

        beerDTO.setId(null);
        beerDTO.setVersion(null);

        final String beerName = "Updated";
        beerDTO.setBeerName(beerName);

        ResponseEntity responseEntity = controller.updateById(beer.getId(), beerDTO);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(204));


        Beer updatedBeer = beerRepository.findById(beer.getId()).get();
        assertThat(updatedBeer.getBeerName()).isEqualTo(beerName);
    }

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