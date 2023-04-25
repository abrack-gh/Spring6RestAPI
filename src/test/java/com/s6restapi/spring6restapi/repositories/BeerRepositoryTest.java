package com.s6restapi.spring6restapi.repositories;

import com.s6restapi.spring6restapi.entities.Beer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class BeerRepositoryTest {

    @Autowired
    BeerRepository beerRepository;

    @Test
    void testSave(){
        Beer savedBeer = beerRepository.save(Beer.builder()
                        .beerName("MyBeer")
                .build());

        assertThat(savedBeer).isNotNull();
        assertThat(savedBeer.getId()).isNotNull();
    }

}