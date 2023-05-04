package com.s6restapi.spring6restapi.repositories;

import com.s6restapi.spring6restapi.bootstrap.BootstrapData;
import com.s6restapi.spring6restapi.entities.Beer;
import com.s6restapi.spring6restapi.model.BeerCSVRecord;
import com.s6restapi.spring6restapi.model.BeerStyle;
import com.s6restapi.spring6restapi.service.BeerCsvServiceImpl;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.dao.DataIntegrityViolationException;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Import({BootstrapData.class, BeerCsvServiceImpl.class})
class BeerRepositoryTest {

    @Autowired
    BeerRepository beerRepository;

    @Test
    void testBeerByName(){
        List<Beer> list = beerRepository.findAllByBeerNameIsLikeIgnoreCase("%IPA"); //return any beers with IPA in beername

        assertThat(list.size()).isEqualTo(30);
    }

    @Test
    void testBeerNameTooLong(){

        assertThrows(ConstraintViolationException.class, () -> {

            Beer savedBeer = beerRepository.save(Beer.builder()
                    .beerName("MyBeerMyBeerMyBeerMyBeerMyBeerMyBeerMyBeerMyBeerMyBeerMyBeerMyBeerMyBeerMyBeerMyBeerMyBeer")
                    .beerStyle(BeerStyle.PALE_ALE)
                    .upc("18928492")
                    .price(new BigDecimal(12.99))
                    .build());

            beerRepository.flush();

        });

    }
    @Test
    void testSave(){
        Beer savedBeer = beerRepository.save(Beer.builder()
                        .beerName("MyBeer")
                        .beerStyle(BeerStyle.PALE_ALE)
                        .upc("18928492")
                        .price(new BigDecimal(12.99))
                .build());

        beerRepository.flush();

        assertThat(savedBeer).isNotNull();
        assertThat(savedBeer.getId()).isNotNull();
    }

}