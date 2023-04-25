package com.s6restapi.spring6restapi.service;

import com.s6restapi.spring6restapi.mappers.BeerMapper;
import com.s6restapi.spring6restapi.model.BeerDTO;
import com.s6restapi.spring6restapi.repositories.BeerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

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
    public List<BeerDTO> listBeers() {
        return beerRepository.findAll()
                .stream()
                .map(beerMapper::beerToBeerDto)
                .collect(Collectors.toList());

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
