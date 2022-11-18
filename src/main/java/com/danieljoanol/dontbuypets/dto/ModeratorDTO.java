package com.danieljoanol.dontbuypets.dto;

import com.danieljoanol.dontbuypets.entity.Moderator;
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
public class ModeratorDTO extends GenericDTO<Moderator> {
    
    private Long id;
    private String name;
    private String username;
    private String email;

    public ModeratorDTO(Moderator entity) {
        this.id = entity.getId();
        this.name = entity.getName();
        this.username = entity.getUsername();
        this.email = entity.getEmail();
    }

    @Override
    public Moderator toEntity() {
        Moderator entity = new Moderator();
        entity.setId(this.id);
        entity.setName(this.name);
        entity.setUsername(this.username);
        entity.setEmail(this.email);

        return entity;
    }
}
