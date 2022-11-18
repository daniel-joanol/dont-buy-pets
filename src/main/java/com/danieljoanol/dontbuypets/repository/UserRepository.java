package com.danieljoanol.dontbuypets.repository;

import org.springframework.stereotype.Repository;

import com.danieljoanol.dontbuypets.entity.User;

@Repository
public interface UserRepository extends GenericRepository<User> {
    //TODO: implement pagination on repositories
}
