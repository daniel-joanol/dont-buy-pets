package com.danieljoanol.dontbuypets.dto;

import java.util.List;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.danieljoanol.dontbuypets.entity.Guardian;
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
public class GuardianDTO extends GenericDTO<Guardian> {
    
    private Long id;

    @NotEmpty(message = "Name can't be empty")
    private String name;

    @NotNull(message = "CityId can't be null")
    private Long cityId;

    @NotEmpty(message = "Phones can't be empty")
    private List<String> phones;

    @NotEmpty(message = "Email can't be empty")
    private String email;
    private String website;
    private String image;

    @NotEmpty(message = "Address can't be empty")
    private String address;

    public GuardianDTO(Guardian entity) {
        this.id = entity.getId();
        this.name = entity.getName();
        this.cityId = entity.getCityId();
        this.phones = entity.getPhones();
        this.email = entity.getEmail();
        this.website = entity.getWebsite();
        this.image = entity.getImage();
        this.address = entity.getAddress();
    }

    @Override
    public Guardian toEntity() {
        Guardian entity = new Guardian();
        entity.setId(this.id);
        entity.setName(this.name);
        entity.setCityId(this.cityId);
        entity.setPhones(this.phones);
        entity.setEmail(this.email);
        entity.setWebsite(this.website);
        entity.setImage(this.image);
        entity.setAddress(this.address);
        
        return entity;
    }

}
