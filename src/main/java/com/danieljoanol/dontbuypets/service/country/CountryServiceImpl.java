package com.danieljoanol.dontbuypets.service.country;

import org.springframework.stereotype.Service;

import com.danieljoanol.dontbuypets.entity.Country;
import com.danieljoanol.dontbuypets.repository.CountryRepository;
import com.danieljoanol.dontbuypets.service.generic.GenericServiceImpl;

@Service
public class CountryServiceImpl extends GenericServiceImpl<Country> implements CountryService {

    private final CountryRepository countryRepository;

    public CountryServiceImpl(CountryRepository countryRepository) {
        super(countryRepository);
        this.countryRepository = countryRepository;
    }

    @Override
    public Country create(Country country) {
        country.setId(null);
        return countryRepository.save(country);
    }
    
}
