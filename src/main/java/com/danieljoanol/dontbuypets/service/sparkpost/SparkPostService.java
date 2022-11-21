package com.danieljoanol.dontbuypets.service.sparkpost;

import com.sparkpost.exception.SparkPostException;

public interface SparkPostService {
    
    String sendActivationCode(String email, String username, Long code) 
            throws SparkPostException;

}
