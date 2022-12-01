package com.danieljoanol.dontbuypets.dto;

import java.util.List;

import javax.validation.constraints.NotBlank;

import com.danieljoanol.dontbuypets.entity.User;
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

    @NotBlank(message = "Name can't be empty")
    private String name;

    @NotBlank(message = "Username can't be empty")
    private String username;

    @NotBlank(message = "Email can't be empty")
    private String email;

    private List<RoleDTO> roles;
    private String image;
    private Boolean active;

    @NotBlank(message = "Password can't be empty")
    private String password;

    public UserDTO(User entity) {
        this.id = entity.getId();
        this.name = entity.getName();
        this.username = entity.getUsername();
        this.email = entity.getEmail();
        this.roles = RoleDTO.fromEntity(entity.getRoles());
        this.image = entity.getImage();
        this.active = entity.getActive();
        this.password = entity.getPassword();
    }

    @Override
    public User toEntity() {
        User entity = new User();
        entity.setId(this.id);
        entity.setName(this.name);
        entity.setUsername(this.username);
        entity.setEmail(this.email);
        entity.setRoles(RoleDTO.toEntity(this.roles));
        entity.setImage(this.image);
        entity.setActive(this.active);

        return entity;
    }
}
