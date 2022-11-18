package com.danieljoanol.dontbuypets.dto;

import java.util.List;

import com.danieljoanol.dontbuypets.entity.Pet;
import com.danieljoanol.dontbuypets.enumarator.AnimalSpecies;
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
public class PetDTO extends GenericDTO<Pet> {
    
    private Long id;
    private String name;
    private Long guardianId;
    private AnimalSpecies species;
    private List<String> images;
    private String description;

    public PetDTO(Pet entity) {
        this.id = entity.getId();
        this.name = entity.getName();
        this.guardianId = entity.getGuardianId();
        this.species = entity.getSpecies();
        this.images = entity.getImages();
        this.description = entity.getDescription();
    }

    @Override
    public Pet toEntity() {
        Pet entity = new Pet();
        entity.setId(this.id);
        entity.setName(this.name);
        entity.setGuardianId(this.guardianId);
        entity.setSpecies(this.species);
        entity.setImages(this.images);
        entity.setDescription(this.description);

        return entity;
    }

}
