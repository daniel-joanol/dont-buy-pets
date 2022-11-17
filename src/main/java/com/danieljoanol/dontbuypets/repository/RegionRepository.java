package com.danieljoanol.dontbuypets.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.danieljoanol.dontbuypets.entity.Region;

@Repository
public interface RegionRepository extends JpaRepository<Region, Long> {
    
}
