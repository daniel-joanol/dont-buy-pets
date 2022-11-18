package com.danieljoanol.dontbuypets.assembler;

import org.springframework.stereotype.Component;

import com.danieljoanol.dontbuypets.dto.RegionDTO;
import com.danieljoanol.dontbuypets.entity.Region;

@Component
public class RegionAssembler extends GenericAssembler<Region, RegionDTO> {

    @Override
    public RegionDTO convertToDTO(Region entity) {
        return new RegionDTO(entity);
    }
    
}
