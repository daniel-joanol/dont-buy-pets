package com.danieljoanol.dontbuypets.service.sparkpost;

import com.sparkpost.exception.SparkPostException;

public interface SparkPostService {
    
    void sendActivationCode(String email, String username, String code) 
            throws SparkPostException;

    void sendNewEmailCode(String email, String username, String code) 
            throws SparkPostException;

    void confirmActivation(String email, String username)
            throws SparkPostException;

    void confirmUpdate(String email, String username)
            throws SparkPostException;
}
