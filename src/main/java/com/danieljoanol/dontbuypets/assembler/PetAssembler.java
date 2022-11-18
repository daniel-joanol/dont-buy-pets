package com.danieljoanol.dontbuypets.assembler;

import org.springframework.stereotype.Component;

import com.danieljoanol.dontbuypets.dto.PetDTO;
import com.danieljoanol.dontbuypets.entity.Pet;

@Component
public class PetAssembler extends GenericAssembler<Pet, PetDTO> {
    
    @Override
    public PetDTO convertToDTO(Pet entity) {
        return new PetDTO(entity);
    }

}
