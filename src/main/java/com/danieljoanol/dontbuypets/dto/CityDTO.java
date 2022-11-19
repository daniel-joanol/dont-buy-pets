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
    private Double latitud;
    private Double longitud;

    public CityDTO(City entity) {
        this.id = entity.getId();
        this.name = entity.getName();
        this.regionId = entity.getRegionId();
        this.latitud = entity.getLatitud();
        this.longitud = entity.getLongitud();
    }

    @Override
    public City toEntity() {
        City entity = new City();
        entity.setId(this.id);
        entity.setName(this.name);
        entity.setRegionId(this.regionId);
        entity.setLatitud(this.latitud);
        entity.setLongitud(this.longitud);

        return entity;
    }

}
