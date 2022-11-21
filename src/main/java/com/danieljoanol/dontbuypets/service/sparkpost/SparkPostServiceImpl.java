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

    private final String SIGNATURE = "\n\nBest regards,\nDon't buy pets' team";

    Client client = new Client(API_KEY);

    @Override
    public void sendActivationCode(String email, String username, Long code) 
            throws SparkPostException {
        
        String subject = "Activation code";
        String text = "Hi " + username + ",";
        text += "\n\n Your new activation code is " + code + ".";
        text += "\nRemember that the code will expire in 5 minutes.";
        text += SIGNATURE;
        
        try {
            client.sendMessage(
                localEmail, 
                email,
                subject, 
                text,
                text);
        } catch (SparkPostException ex) {
            log.error(ex.getMessage());
        }
    }

    @Override
    public void sendNewEmailCode(String email, String username, Long code) 
            throws SparkPostException {
        
        String subject = "Update username";
        String text = "Hi " + username + ",";
        text += "\n\nYour code is " + code + ".";
        text += "\nRemember that the code will expire in 5 minutes.";
        text += SIGNATURE;
        
        try {
            client.sendMessage(
                localEmail, 
                email,
                subject, 
                text,
                text);
        } catch (SparkPostException ex) {
            log.error(ex.getMessage());
        }
    }

    @Override
    public void confirmActivation(String email, String username) throws SparkPostException {
        
        String subject = "Account activated";
        String text = "Hi " + username + ",";
        text += "\n\n Your account has been activated succesfully.";
        text += SIGNATURE;

        try {
            client.sendMessage(
                localEmail, 
                email,
                subject, 
                text,
                text);
        } catch (SparkPostException ex) {
            log.error(ex.getMessage());
        }
    }

    @Override
    public void confirmUpdate(String email, String username) throws SparkPostException {
        
        String subject = "Account updated";
        String text = "Hi " + username + ",";
        text += "\n\n Your account has been updated succesfully.";
        text += SIGNATURE;

        try {
            client.sendMessage(
                localEmail, 
                email,
                subject, 
                text,
                text);
        } catch (SparkPostException ex) {
            log.error(ex.getMessage());
        }
    }

}
