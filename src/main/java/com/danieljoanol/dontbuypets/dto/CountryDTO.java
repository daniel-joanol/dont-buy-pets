package com.danieljoanol.dontbuypets.dto;
import javax.validation.constraints.NotEmpty;

import com.danieljoanol.dontbuypets.entity.Country;
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
public class CountryDTO extends GenericDTO<Country> {

    private Long id;

    @NotEmpty(message = "Name can't be empty")
    private String name;

    @NotEmpty(message = "ISO can't be empty")
    private String ISO;

    public CountryDTO(Country entity) {
        this.id = entity.getId();
        this.name = entity.getName();
        this.ISO = entity.getISO();
    }

    @Override
    public Country toEntity() {
        Country entity = new Country();
        entity.setId(this.id);
        entity.setName(this.name);
        entity.setISO(this.ISO);

        return entity;
    }
}
