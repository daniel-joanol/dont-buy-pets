package com.danieljoanol.dontbuypets.assembler;

import org.springframework.stereotype.Component;

import com.danieljoanol.dontbuypets.dto.CityDTO;
import com.danieljoanol.dontbuypets.entity.City;

@Component
public class CityAssembler extends GenericAssembler<City, CityDTO> {

    @Override
    public CityDTO convertToDTO(City entity) {
        return new CityDTO(entity);
    }

}
