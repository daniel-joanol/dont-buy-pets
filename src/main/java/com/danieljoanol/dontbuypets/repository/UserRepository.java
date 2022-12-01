package com.danieljoanol.dontbuypets.repository;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.danieljoanol.dontbuypets.entity.User;

@Repository
public interface UserRepository extends GenericRepository<User> {

    Boolean existsByEmail(String email);

    Boolean existsByUsername(String username);

    Optional<User> findByUsername(String username);

}
