package com.danieljoanol.dontbuypets.service.user;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Random;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.danieljoanol.dontbuypets.controller.request.ActivateUser;
import com.danieljoanol.dontbuypets.entity.User;
import com.danieljoanol.dontbuypets.exception.ActivationException;
import com.danieljoanol.dontbuypets.exception.DuplicatedUserDataException;
import com.danieljoanol.dontbuypets.exception.EmptyImageException;
import com.danieljoanol.dontbuypets.exception.InvalidImageFormatException;
import com.danieljoanol.dontbuypets.repository.UserRepository;
import com.danieljoanol.dontbuypets.service.cloud.CloudinaryService;
import com.danieljoanol.dontbuypets.service.generic.GenericServiceImpl;
import com.danieljoanol.dontbuypets.service.sparkpost.SparkPostService;
import com.sparkpost.exception.SparkPostException;

@Service
public class UserServiceImpl extends GenericServiceImpl<User> implements UserService {
    
    private final UserRepository userRepository;

    @Autowired
    private CloudinaryService cloudinaryService;

    @Autowired
    private SparkPostService sparkPostService;

    private final Random random = new Random();

    private final String DEFAULT_MESSAGE = "Check your email box";

    public UserServiceImpl(UserRepository userRepository) {
        super(userRepository);
        this.userRepository = userRepository; 
    }

    @Override
    public String createUser(User entity) throws DuplicatedUserDataException, SparkPostException {
        entity.setId(null);
        entity.setActive(false);

        if (userRepository.existsByEmail(entity.getEmail())) {
            throw new DuplicatedUserDataException("This email is already being used");
        }

        if (userRepository.existsByUsername(entity.getUsername())) {
            throw new DuplicatedUserDataException("This username is already being used");
        }

        entity = newActivationCode(entity);

        sparkPostService.sendActivationCode(
                entity.getEmail(), entity.getUsername(), entity.getActivationCode());

        return DEFAULT_MESSAGE;
    }

    @Override
    public String newActivationCode(ActivateUser activateUser) throws SparkPostException, ActivationException {
        User user = userRepository.findByUsername(activateUser.getUsername()).orElseThrow();

        if (!user.getEmail().equalsIgnoreCase(activateUser.getEmail())) {
            throw new ActivationException("This username does not belong to this email");
        }

        user = newActivationCode(user);
        sparkPostService.sendActivationCode(
                activateUser.getEmail(), activateUser.getUsername(), user.getActivationCode());
        return DEFAULT_MESSAGE;
    }

    public User newActivationCode(User user) {
        Long activationCode = random.nextLong();
        user.setActivationCode(activationCode);
        user.setCodeDate(LocalDateTime.now());
        return userRepository.save(user);
    }

    @Override
    public User activateUser(ActivateUser activateUser) throws SparkPostException, ActivationException {
        User user = userRepository.findByUsername(activateUser.getUsername()).orElseThrow();
        LocalDateTime now = LocalDateTime.now();

        if (activateUser.getActivationCode() == user.getActivationCode() && 
                now.isBefore(user.getCodeDate().plusMinutes(5))) {
            user.setActive(true);
            sparkPostService.confirmActivation(user.getEmail(), user.getUsername());
            return user;
        } else {
            throw new ActivationException("Activation failed");
        }
    }

    @Override
    public String addImage(Long id, MultipartFile image)
            throws EmptyImageException, InvalidImageFormatException, IOException {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Id " + id + " not found"));
        String URL = cloudinaryService.uploadImage(image);
        user.setImage(URL);
        user = userRepository.save(user);
        return URL;
    }

    @Override
    public String updateUser(User update) throws SparkPostException, DuplicatedUserDataException {
        User user = userRepository.findById(update.getId()).orElseThrow();

        if (!update.getUsername().equalsIgnoreCase(user.getUsername())) {
            if (userRepository.existsByUsername(update.getUsername())) {
                throw new DuplicatedUserDataException("This username is already being used");
            } else {
                user.setUsername(update.getUsername());
                sparkPostService.confirmUpdate(
                    user.getEmail(), user.getUsername());
            }
        }

        if (!update.getEmail().equalsIgnoreCase(user.getEmail())) {
            if (userRepository.existsByEmail(update.getEmail())) {
                throw new DuplicatedUserDataException("This email is already being used");
            } else {
                user.setNewEmail(update.getEmail());
                user = newEmailCode(user);
                sparkPostService.sendNewEmailCode(
                    user.getNewEmail(), user.getUsername(), user.getNewEmailCode());
            }
        }

        if (!update.getPassword().equalsIgnoreCase(user.getPassword())) {
            user.setNewPassword(update.getPassword());
            user = newPasswordCode(user);
            sparkPostService.sendActivationCode(
                    user.getEmail(), user.getUsername(), user.getNewPassCode());
        }

        return DEFAULT_MESSAGE;
    }

    public User newPasswordCode(User user) {
        Long newPassCode = random.nextLong();
        user.setNewPassCode(newPassCode);
        user.setNewPassDate(LocalDateTime.now());
        return userRepository.save(user);
    }

    public User newEmailCode(User user) {
        Long newEmailCode = random.nextLong();
        user.setNewEmailCode(newEmailCode);
        user.setNewEmailDate(LocalDateTime.now());
        return userRepository.save(user);
    }

}
