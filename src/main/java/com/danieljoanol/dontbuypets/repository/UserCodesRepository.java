package com.danieljoanol.dontbuypets.repository;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.danieljoanol.dontbuypets.entity.UserCodes;

@Repository
public interface UserCodesRepository extends GenericRepository<UserCodes> {

    Optional<UserCodes> findByUserId(Long userId);
    
}
