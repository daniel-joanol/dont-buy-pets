package com.danieljoanol.dontbuypets.service.user;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

import com.danieljoanol.dontbuypets.controller.request.ActivateUserDTO;
import com.danieljoanol.dontbuypets.entity.User;
import com.danieljoanol.dontbuypets.exception.ActivationException;
import com.danieljoanol.dontbuypets.exception.DuplicatedUserDataException;
import com.danieljoanol.dontbuypets.exception.EmptyImageException;
import com.danieljoanol.dontbuypets.exception.InvalidImageFormatException;
import com.danieljoanol.dontbuypets.service.generic.GenericService;
import com.sparkpost.exception.SparkPostException;

public interface UserService extends GenericService<User> {
    
    String addImage(Long id, MultipartFile image)
            throws EmptyImageException, InvalidImageFormatException, IOException;

    String createUser(User entity) throws DuplicatedUserDataException, SparkPostException, Exception;

    User activateUser(ActivateUserDTO activateUser) throws SparkPostException, ActivationException;

    String newActivationCode(ActivateUserDTO activateUser) throws SparkPostException, ActivationException;

    String updateUser(User update) throws SparkPostException, DuplicatedUserDataException;
    
    User activateNewPassword(ActivateUserDTO activateUser) throws SparkPostException, ActivationException;

    User activateNewEmail(ActivateUserDTO activateUser) throws SparkPostException, ActivationException;
}
