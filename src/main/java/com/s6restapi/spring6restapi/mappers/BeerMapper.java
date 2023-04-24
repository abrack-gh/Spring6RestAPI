package com.s6restapi.spring6restapi.mappers;

import com.s6restapi.spring6restapi.entities.Beer;
import com.s6restapi.spring6restapi.model.BeerDTO;
import org.mapstruct.Mapper;

@Mapper
public interface BeerMapper {

    Beer beerDtotoBeer(BeerDTO dto);

    BeerDTO beerToBeerDto(Beer beer);
}
