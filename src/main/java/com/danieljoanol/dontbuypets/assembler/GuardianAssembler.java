package com.danieljoanol.dontbuypets.assembler;

import org.springframework.stereotype.Component;

import com.danieljoanol.dontbuypets.dto.GuardianDTO;
import com.danieljoanol.dontbuypets.entity.Guardian;

@Component
public class GuardianAssembler extends GenericAssembler<Guardian, GuardianDTO> {
    
    @Override
    public GuardianDTO convertToDTO(Guardian entity) {
        return new GuardianDTO(entity);
    }

}
