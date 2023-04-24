package com.s6restapi.spring6restapi.bootstrap;

import com.s6restapi.spring6restapi.entities.Beer;
import com.s6restapi.spring6restapi.entities.Customer;
import com.s6restapi.spring6restapi.model.BeerStyle;
import com.s6restapi.spring6restapi.repositories.BeerRepository;
import com.s6restapi.spring6restapi.repositories.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;

@Component
@RequiredArgsConstructor
public class BootstrapData implements CommandLineRunner {

    private final CustomerRepository customerRepository;
    private final BeerRepository beerRepository;

    @Override
    public void run(String... args) throws Exception {

        loadCustomerData();
        loadBeerData();

    }

    private void loadCustomerData() {

        if(customerRepository.count() == 0) {

            Customer customer1 = Customer.builder()
                    .customerName("Alex Brack")
                    .createdDate(LocalDateTime.now())
                    .lastModifiedDate(LocalDateTime.now())
                    .build();

            Customer customer2 = Customer.builder()
                    .customerName("Tonald Drump")
                    .createdDate(LocalDateTime.now())
                    .lastModifiedDate(LocalDateTime.now())
                    .build();

            Customer customer3 = Customer.builder()
                    .customerName("Lionel Pessi")
                    .createdDate(LocalDateTime.now())
                    .lastModifiedDate(LocalDateTime.now())
                    .build();

            customerRepository.saveAll(Arrays.asList(customer1, customer2, customer3));
        }

    }

    private void loadBeerData() {

        if(beerRepository.count() == 0) {

            Beer beer1 = Beer.builder()
                    .beerName("BrackBeer")
                    .beerStyle(BeerStyle.PALE_ALE)
                    .upc("126")
                    .price(new BigDecimal("4.99"))
                    .quantityOnHand(12)
                    .createdDate(LocalDateTime.now())
                    .updatedDate(LocalDateTime.now())
                    .build();

            Beer beer2 = Beer.builder()
                    .beerName("Test Ickle Lager")
                    .beerStyle(BeerStyle.LAGER)
                    .upc("1235")
                    .price(new BigDecimal("3.99"))
                    .quantityOnHand(92)
                    .createdDate(LocalDateTime.now())
                    .updatedDate(LocalDateTime.now())
                    .build();

            Beer beer3 = Beer.builder()
                    .beerName("Don Pollo Real Ale")
                    .beerStyle(BeerStyle.ALE)
                    .upc("1236")
                    .price(new BigDecimal("10.99"))
                    .quantityOnHand(14)
                    .createdDate(LocalDateTime.now())
                    .updatedDate(LocalDateTime.now())
                    .build();

            beerRepository.save(beer1);
            beerRepository.save(beer2);
            beerRepository.save(beer3);
        }
    }
}
