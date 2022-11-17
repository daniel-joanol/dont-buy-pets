package com.danieljoanol.dontbuypets.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.danieljoanol.dontbuypets.entity.Pet;

@Repository
public interface PetRepository extends JpaRepository<Pet, Long> {
    
}
