package com.s6restapi.spring6restapi.bootstrap;

import com.s6restapi.spring6restapi.repositories.BeerRepository;
import com.s6restapi.spring6restapi.repositories.CustomerRepository;
import com.s6restapi.spring6restapi.service.BeerCsvService;
import com.s6restapi.spring6restapi.service.BeerCsvServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Import(BeerCsvServiceImpl.class)
class BootstrapDataTest {

    @Autowired
    BeerRepository beerRepository;

    @Autowired
    CustomerRepository customerRepository;

    BootstrapData bootstrapData;

    @BeforeEach
    void setUp() {
        bootstrapData = new BootstrapData(customerRepository, beerRepository);
    }

    @Test
    void Testrun() throws Exception {

        bootstrapData.run(null);

        assertThat(customerRepository.count()).isEqualTo(3);
        assertThat(beerRepository.count()).isEqualTo(2413);
    }

}