package com.s6restapi.spring6restapi.service;

import com.s6restapi.spring6restapi.entities.Beer;
import com.s6restapi.spring6restapi.mappers.BeerMapper;
import com.s6restapi.spring6restapi.model.BeerDTO;
import com.s6restapi.spring6restapi.model.BeerStyle;
import com.s6restapi.spring6restapi.repositories.BeerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Service
@Primary
@RequiredArgsConstructor
public class BeerServiceJPA implements BeerService {

    private final BeerRepository beerRepository;
    private final BeerMapper beerMapper;

    @Override
    public List<BeerDTO> listBeers(String beerName, BeerStyle beerStyle, Boolean showInventory) {

        List<Beer> beerList;

        if(StringUtils.hasText(beerName) && beerStyle == null){
            //impl
            beerList = listBeersByName(beerName);

        } else if(!StringUtils.hasText(beerName) && beerStyle != null){
            beerList = listBeersByStyle(beerStyle);

        } else if(StringUtils.hasText(beerName) && beerStyle != null){

            beerList = listBeersByNameandStyle(beerName, beerStyle);

        } else {
            beerList = beerRepository.findAll();
        }

        if(showInventory !=null && !showInventory){
            beerList.forEach(beer -> beer.setQuantityOnHand(null));
        }

        return beerList.stream()
                .map(beerMapper::beerToBeerDto)
                .collect(Collectors.toList());

    }

    private List<Beer> listBeersByNameandStyle(String beerName, BeerStyle beerStyle) {

        return beerRepository.findAllByBeerNameIsLikeIgnoreCaseAndBeerStyle("%" + beerName + "%", beerStyle);
    }

    private List<Beer> listBeersByStyle(BeerStyle beerStyle) {

        return beerRepository.findAllByBeerStyle(beerStyle);
    }

    public List<Beer> listBeersByName(String beerName){

        return beerRepository.findAllByBeerNameIsLikeIgnoreCase("%" + beerName + "%");
    }

    @Override
    public Optional<BeerDTO> getBeerById(UUID id) {
        return Optional.ofNullable(beerMapper.beerToBeerDto(beerRepository.findById(id)
                .orElse(null)));
    }

    @Override
    public BeerDTO saveBeer(BeerDTO beer) {

       return beerMapper.beerToBeerDto(beerRepository.save(beerMapper.beerDtotoBeer(beer)));

        //takes beerDTO convert it to beer object(entity) passes it into save which returns saved beer obkect
        // that has UUID and version set, then take the result and pass to other converter
        // to convert from beer entity to beerDTO, and return to beerMapper.
    }

    @Override
    public Optional<BeerDTO> updateBeerById(UUID beerId, BeerDTO beer) {

        AtomicReference<Optional<BeerDTO>> atomicReference = new AtomicReference<>();

        beerRepository.findById(beerId).ifPresentOrElse(foundBeer -> {
            foundBeer.setBeerName(beer.getBeerName());
            foundBeer.setBeerStyle(beer.getBeerStyle());
            foundBeer.setPrice(beer.getPrice());
            foundBeer.setUpc(beer.getUpc());
            atomicReference.set(Optional.of(beerMapper.beerToBeerDto(beerRepository.save(foundBeer))));
        }, () -> {
            atomicReference.set(Optional.empty());
        });

        return atomicReference.get();
    }

    @Override
    public Boolean deleteById(UUID beerId) {

        if(beerRepository.existsById(beerId)){
            beerRepository.deleteById(beerId);
            return true;
        }
        return false;
    }

    @Override
    public void updatePatchById(UUID beerId, BeerDTO beer) {

    }
}
