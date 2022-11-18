package com.danieljoanol.dontbuypets.assembler;

import org.springframework.stereotype.Component;

import com.danieljoanol.dontbuypets.dto.UserDTO;
import com.danieljoanol.dontbuypets.entity.User;

@Component
public class UserAssembler extends GenericAssembler<User, UserDTO> {
    
    @Override
    public UserDTO convertToDTO(User entity) {
        return new UserDTO(entity);
    }
}
