package com.s6restapi.spring6restapi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.s6restapi.spring6restapi.entities.Beer;
import com.s6restapi.spring6restapi.mappers.BeerMapper;
import com.s6restapi.spring6restapi.model.BeerDTO;
import com.s6restapi.spring6restapi.model.BeerStyle;
import com.s6restapi.spring6restapi.repositories.BeerRepository;
import jakarta.transaction.Transactional;
import org.hamcrest.core.IsNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.*;

import static net.bytebuddy.matcher.ElementMatchers.is;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class BeerControllerIT {

    @Autowired
    BeerController controller;

    @Autowired
    BeerRepository beerRepository;

    @Autowired
    BeerMapper beerMapper;

    @Autowired
    WebApplicationContext webApplicationContext;

    @Autowired
    ObjectMapper objectMapper;

    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }


    @Test
    void testListBeersByStyleAndNameShowInventoryTruePage2() throws Exception {
        mockMvc.perform(get(BeerController.BEER_PATH)
                .queryParam("beerName", "IPA")
                .queryParam("beerStyle", BeerStyle.IPA.name())
                .queryParam("showInventory", "true")
                .queryParam("pageNumber", "2")
                .queryParam("pageSize", "50"))
                .andExpect(status().isOk())
                .andExpect((ResultMatcher) jsonPath("$.size()", is(50)))
                .andExpect(jsonPath("$.[0].quantityOnHand").value(IsNull.notNullValue()));
    }
    @Test
    void testListBeersByStyleAndNameShowInventoryTrue() throws Exception {
        mockMvc.perform(get(BeerController.BEER_PATH)
                        .queryParam("beerName", "IPA")
                        .queryParam("beerStyle", BeerStyle.IPA.name())
                        .queryParam("showInventory", "true"))
                .andExpect(status().isOk())
                .andExpect((ResultMatcher) jsonPath("$.size()").value(336))
                .andExpect(jsonPath("$.[0].quantityOnHand").value(IsNull.notNullValue()));
    }

    @Test
    void testListBeersByStyleAndNameShowInventoryFalse() throws Exception {
        mockMvc.perform(get(BeerController.BEER_PATH)
                        .queryParam("beerName", "IPA")
                        .queryParam("beerStyle", BeerStyle.IPA.name())
                        .queryParam("showInventory", "false"))
                .andExpect(status().isOk())
                .andExpect((ResultMatcher) jsonPath("$.size()").value(336))
                .andExpect(jsonPath("$.[0].quantityOnHand").value(IsNull.nullValue()));
    }

        @Test
        void testListBeersByStyle() throws Exception {
        mockMvc.perform(get(BeerController.BEER_PATH)
                        .queryParam("beerName", "IPA")
                        .queryParam("beerStyle", BeerStyle.IPA.name()))
                    .andExpect(status().isOk())
                    .andExpect((ResultMatcher) jsonPath("$.size()").value(336));
    }

    @Test
    void testListBeersByName() throws Exception {
        mockMvc.perform(get(BeerController.BEER_PATH)
                .queryParam("beerName", "IPA"))
                .andExpect(status().isOk())
                .andExpect((ResultMatcher) jsonPath("$.size()", is(100)));
    }

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
    void testPatchBeerBadName() throws Exception {
        Beer beer = beerRepository.findAll().get(0);

        Map<String, Object> beerMap = new HashMap<>();
        beerMap.put("beerName", "New Name 1234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890");

        mockMvc.perform(patch(BeerController.BEER_PATH_ID, beer.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(beerMap)))
                .andExpect(status().isBadRequest());

    }



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
        Page<BeerDTO> dtos = controller.listBeers(null, null, false, 1, 25);

        assertThat(dtos.getContent().size()).isEqualTo(2313);
    }

    @Rollback
    @Transactional
    @Test
    void testEmptyList() {

        beerRepository.deleteAll();

        Page<BeerDTO> dtos = controller.listBeers(null, null, false, 1, 2413);

        assertThat(dtos.getContent().size()).isEqualTo(1000);
    }
}