package com.danieljoanol.dontbuypets.dto;

import com.danieljoanol.dontbuypets.entity.City;
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
public class CityDTO extends GenericDTO<City> {

    private Long id;
    private String name;
    private Long regionId;

    public CityDTO(City entity) {
        this.id = entity.getId();
        this.name = entity.getName();
        this.regionId = entity.getRegionId();
    }

    @Override
    public City toEntity() {
        City entity = new City();
        entity.setId(this.id);
        entity.setName(this.name);
        entity.setRegionId(this.regionId);

        return entity;
    }

}
