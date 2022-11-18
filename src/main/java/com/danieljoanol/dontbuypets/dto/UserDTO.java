package com.danieljoanol.dontbuypets.dto;

import com.danieljoanol.dontbuypets.entity.User;
import com.danieljoanol.dontbuypets.enumarator.Roles;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@JsonInclude(Include.NON_NULL)
public class UserDTO extends GenericDTO<User> {
    
    private Long id;
    private String name;
    private String username;
    private String email;
    private Roles role;
    private String image;

    public UserDTO(User entity) {
        this.id = entity.getId();
        this.name = entity.getName();
        this.username = entity.getUsername();
        this.email = entity.getEmail();
        this.role = entity.getRole();
        this.image = entity.getImage();
    }

    @Override
    public User toEntity() {
        User entity = new User();
        entity.setId(this.id);
        entity.setName(this.name);
        entity.setUsername(this.username);
        entity.setEmail(this.email);
        entity.setRole(this.role);
        entity.setImage(this.image);

        return entity;
    }
}
