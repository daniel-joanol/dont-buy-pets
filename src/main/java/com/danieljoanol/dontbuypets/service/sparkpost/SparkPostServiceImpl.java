package com.danieljoanol.dontbuypets.service.sparkpost;

import org.springframework.stereotype.Service;

import com.sparkpost.Client;
import com.sparkpost.exception.SparkPostException;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@NoArgsConstructor
@Slf4j
public class SparkPostServiceImpl implements SparkPostService {

    private String API_KEY = System.getenv("SPARK_KEY");
    private String localEmail = System.getenv("SPARK_EMAIL");

    private final String SIGNATURE = "\n\nBest regards,\nDon't buy pets team";

    Client client = new Client(API_KEY);

    @Override
    public void sendActivationCode(String email, String username, String code) 
            throws SparkPostException {

        System.out.println(API_KEY);

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
    public void sendNewEmailCode(String email, String username, String code) 
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
