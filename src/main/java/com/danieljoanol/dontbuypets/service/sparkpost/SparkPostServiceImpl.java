package com.danieljoanol.dontbuypets.service.sparkpost;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.sparkpost.Client;
import com.sparkpost.exception.SparkPostException;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@NoArgsConstructor
@Slf4j
public class SparkPostServiceImpl implements SparkPostService {

    @Value("${sparkpost.key}")
    private String API_KEY;

    @Value("${sparkpost.email}")
    private String localEmail;

    private String signature = "\n\nBest regards,\nDon't buy pets' team";

    Client client = new Client(API_KEY);

    @Override
    public String sendActivationCode(String email, String username, Long code) 
            throws SparkPostException {
        
        String text = "Hi " + username + ",";
        text += "\n Your new activation code is " + code + "." + signature;
        
        try {
            client.sendMessage(
                localEmail, 
                email,
                "Activation code", 
                text,
                text);
        } catch (SparkPostException ex) {
            log.error(ex.getMessage());
        }

        return "Check your email box";
    }

}
