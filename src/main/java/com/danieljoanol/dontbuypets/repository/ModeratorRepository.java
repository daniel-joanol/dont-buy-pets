package com.danieljoanol.dontbuypets.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.danieljoanol.dontbuypets.entity.Moderator;

@Repository
public interface ModeratorRepository extends JpaRepository<Moderator, Long> {
    //TODO: implement pagination on repositories
}
