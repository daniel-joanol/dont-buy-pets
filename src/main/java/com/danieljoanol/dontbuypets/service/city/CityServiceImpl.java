package com.danieljoanol.dontbuypets.service.city;

import org.springframework.stereotype.Service;

import com.danieljoanol.dontbuypets.entity.City;
import com.danieljoanol.dontbuypets.repository.CityRepository;
import com.danieljoanol.dontbuypets.service.generic.GenericServiceImpl;

@Service
public class CityServiceImpl extends GenericServiceImpl<City> implements CityService {
    
    private final CityRepository cityRepository;

    public CityServiceImpl(CityRepository cityRepository) {
        super(cityRepository);
        this.cityRepository = cityRepository;
    }

    @Override
    public City create(City city) {
        city.setId(null);
        return cityRepository.save(city);
    }
}
