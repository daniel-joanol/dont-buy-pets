package com.danieljoanol.dontbuypets.repository;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.danieljoanol.dontbuypets.entity.Role;

@Repository
public interface RoleRepository extends GenericRepository<Role> {
    
    Optional<Role> findByName(String name);
    
}
