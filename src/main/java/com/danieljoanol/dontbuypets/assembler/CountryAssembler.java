package com.danieljoanol.dontbuypets.assembler;

import org.springframework.stereotype.Component;

import com.danieljoanol.dontbuypets.dto.CountryDTO;
import com.danieljoanol.dontbuypets.entity.Country;

@Component
public class CountryAssembler extends GenericAssembler<Country, CountryDTO> {
    
    @Override
    public CountryDTO convertToDTO(Country entity) {
        return new CountryDTO(entity);
    }

}
