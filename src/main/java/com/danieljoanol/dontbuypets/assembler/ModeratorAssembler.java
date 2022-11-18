package com.danieljoanol.dontbuypets.assembler;

import org.springframework.stereotype.Component;

import com.danieljoanol.dontbuypets.dto.ModeratorDTO;
import com.danieljoanol.dontbuypets.entity.Moderator;

@Component
public class ModeratorAssembler extends GenericAssembler<Moderator, ModeratorDTO> {
    
    @Override
    public ModeratorDTO convertToDTO(Moderator entity) {
        return new ModeratorDTO(entity);
    }
}
