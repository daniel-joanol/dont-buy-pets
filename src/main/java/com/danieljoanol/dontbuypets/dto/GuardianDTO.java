package com.danieljoanol.dontbuypets.dto;

import java.util.List;

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
    private String name;
    private Long cityId;
    private List<String> phones;
    private String email;
    private String website;
    private String image;

    public GuardianDTO(Guardian entity) {
        this.id = entity.getId();
        this.name = entity.getName();
        this.cityId = entity.getCityId();
        this.phones = entity.getPhones();
        this.email = entity.getEmail();
        this.website = entity.getWebsite();
        this.image = entity.getImage();
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
        
        return entity;
    }

}
