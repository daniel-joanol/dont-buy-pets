package com.danieljoanol.dontbuypets.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.danieljoanol.dontbuypets.entity.Guardian;

@Repository
public interface GuardianRepository extends JpaRepository<Guardian, Long> {
    
}
