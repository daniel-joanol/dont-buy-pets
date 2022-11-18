package com.danieljoanol.dontbuypets.dto;
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
    private String name;

    public CountryDTO(Country entity) {
        this.id = entity.getId();
        this.name = entity.getName();
    }

    @Override
    public Country toEntity() {
        Country entity = new Country();
        entity.setId(this.id);
        entity.setName(this.name);

        return entity;
    }
}
