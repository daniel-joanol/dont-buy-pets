package com.danieljoanol.dontbuypets.dto;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.danieljoanol.dontbuypets.entity.Region;
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
public class RegionDTO extends GenericDTO<Region> {

    private Long id;

    @NotBlank(message = "The name can't be empty")
    private String name;

    @NotNull(message = "The countryId can't be null")
    private Long countryId;

    public RegionDTO(Region entity) {
        this.id = entity.getId();
        this.name = entity.getName();
        this.countryId = entity.getCountryId();
    }

    @Override
    public Region toEntity() {
        Region entity = new Region();
        entity.setId(this.id);
        entity.setName(this.name);
        entity.setCountryId(this.countryId);

        return entity;
    }
}
