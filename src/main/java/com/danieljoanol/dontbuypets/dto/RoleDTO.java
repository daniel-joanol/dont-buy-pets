package com.danieljoanol.dontbuypets.dto;

import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.NotBlank;

import com.danieljoanol.dontbuypets.entity.Role;
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
public class RoleDTO extends GenericDTO<Role> {

    private Long id;

    @NotBlank(message = "Name can't be empty")
    private String name;
    
    public RoleDTO(Role entity) {
        this.id = entity.getId();
        this.name = entity.getName();
    }

    @Override
    public Role toEntity() {
        Role entity = new Role();
        entity.setId(this.id);
        entity.setName(this.name);
        return entity;
    }

    public static Set<Role> toEntity(Set<RoleDTO> dtos) {
        Set<Role> entities = new HashSet<>();
        for (RoleDTO dto : dtos) {
            Role role = new Role();
            role.setId(dto.getId());
            role.setName(dto.getName());
            entities.add(role);
        }

        return entities;
    }

    public static Set<RoleDTO> fromEntity(Set<Role> entities) {
        Set<RoleDTO> dtos = new HashSet<>();
        for (Role entity : entities) {
            RoleDTO dto = new RoleDTO();
            dto.setId(entity.getId());
            dto.setName(entity.getName());
            dtos.add(dto);
        }

        return dtos;
    }
}
