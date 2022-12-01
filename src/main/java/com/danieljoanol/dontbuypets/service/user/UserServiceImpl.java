package com.danieljoanol.dontbuypets.service.user;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.danieljoanol.dontbuypets.controller.request.ActivateUserDTO;
import com.danieljoanol.dontbuypets.entity.Role;
import com.danieljoanol.dontbuypets.entity.User;
import com.danieljoanol.dontbuypets.exception.ActivationException;
import com.danieljoanol.dontbuypets.exception.DuplicatedUserDataException;
import com.danieljoanol.dontbuypets.exception.EmptyImageException;
import com.danieljoanol.dontbuypets.exception.InvalidImageFormatException;
import com.danieljoanol.dontbuypets.repository.RoleRepository;
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

    @Autowired
    private RoleRepository roleRepository;

    private final Random random = new Random();

    private final String DEFAULT_MESSAGE = "Check your email box";

    public UserServiceImpl(UserRepository userRepository) {
        super(userRepository);
        this.userRepository = userRepository; 
    }

    @Override
    public String createUser(User entity) throws Exception {
        entity.setId(null);
        entity.setImage(null);
        entity.setActive(false);
        List<Role> roles = new ArrayList<>();
        roles.add(roleRepository.findByName("USER")
                .orElseThrow(() -> new Exception("Error while trying to assing roles/permissions")));
        entity.setRoles(roles);

        if (userRepository.existsByEmail(entity.getEmail())) {
            throw new DuplicatedUserDataException("This email is already being used");
        }

        if (userRepository.existsByUsername(entity.getUsername())) {
            throw new DuplicatedUserDataException("This username is already being used");
        }

        String code = getCode();
        entity.setActivationCode(code);
        entity.setCodeDate(LocalDateTime.now());
        entity = userRepository.save(entity);

        sparkPostService.sendActivationCode(
                entity.getEmail(), entity.getUsername(), entity.getActivationCode());

        return DEFAULT_MESSAGE;
    }

    @Override
    public String newActivationCode(ActivateUserDTO activateUser) throws SparkPostException, ActivationException {
        User user = userRepository.findByUsername(activateUser.getUsername())
                .orElseThrow(() -> new EntityNotFoundException("This username does not exist"));

        if (!user.getEmail().equalsIgnoreCase(activateUser.getEmail())) {
            throw new ActivationException("This username does not belong to this email");
        }

        String code = getCode();
        user.setActivationCode(code);
        user.setCodeDate(LocalDateTime.now());
        user = userRepository.save(user);
        
        sparkPostService.sendActivationCode(
                activateUser.getEmail(), activateUser.getUsername(), user.getActivationCode());
        return DEFAULT_MESSAGE;
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
        User user = userRepository.findById(update.getId())
                .orElseThrow(() -> new EntityNotFoundException("This username does not exist"));

        if (!update.getUsername().equalsIgnoreCase(user.getUsername())) {
            if (userRepository.existsByUsername(update.getUsername())) {
                throw new DuplicatedUserDataException("This username is already being used");
            } else {
                user.setUsername(update.getUsername());
                user = userRepository.save(user);
                sparkPostService.confirmUpdate(
                    user.getEmail(), user.getUsername());
            }
        }

        if (!update.getEmail().equalsIgnoreCase(user.getEmail())) {
            if (userRepository.existsByEmail(update.getEmail())) {
                throw new DuplicatedUserDataException("This email is already being used");
            } else {
                user.setNewEmail(update.getEmail());
                String code = getCode();
                user.setNewEmailCode(code);
                user.setNewEmailDate(LocalDateTime.now());
                user = userRepository.save(user);
                sparkPostService.sendNewEmailCode(
                    user.getNewEmail(), user.getUsername(), user.getNewEmailCode());
            }
        }

        if (!update.getPassword().equalsIgnoreCase(user.getPassword())) {
            user.setNewPassword(update.getPassword());
            String code = getCode();
            user.setNewPassCode(code);
            user.setNewPassDate(LocalDateTime.now());
            user = userRepository.save(user);
            sparkPostService.sendActivationCode(
                    user.getEmail(), user.getUsername(), user.getNewPassCode());
        }

        return DEFAULT_MESSAGE;
    }

    @Override
    public User activateUser(ActivateUserDTO activateUser) throws SparkPostException, ActivationException {
        User user = userRepository.findByUsername(activateUser.getUsername())
                .orElseThrow(() -> new EntityNotFoundException("This username does not exist"));
        LocalDateTime now = LocalDateTime.now();

        if (activateUser.getActivationCode().equals(user.getActivationCode())
                && now.isBefore(user.getCodeDate().plusMinutes(5))) {
            user.setActive(true);
            user.setActivationCode(null);
            user.setCodeDate(null);
            user = userRepository.save(user);
            sparkPostService.confirmActivation(user.getEmail(), user.getUsername());
            return user;
        } else {
            throw new ActivationException("Activation failed");
        }
    }

    @Override
    public User activateNewPassword(ActivateUserDTO activateUser) throws SparkPostException, ActivationException {
        User user = userRepository.findByUsername(activateUser.getUsername())
                .orElseThrow(() -> new EntityNotFoundException("This username does not exist"));
        LocalDateTime now = LocalDateTime.now();

        if (activateUser.getActivationCode().equalsIgnoreCase(user.getNewPassCode())
                && now.isBefore(user.getNewPassDate().plusMinutes(5))) {
            user.setPassword(user.getNewPassword());
            user.setNewPassword(null);
            user.setNewPassDate(null);
            user.setNewPassCode(null);
            user = userRepository.save(user);
            sparkPostService.confirmUpdate(user.getEmail(), user.getUsername());
            return user;
        } else {
            throw new ActivationException("Activation failed");
        }
    }

    @Override
    public User activateNewEmail(ActivateUserDTO activateUser) throws SparkPostException, ActivationException {
        User user = userRepository.findByUsername(activateUser.getUsername())
                .orElseThrow(() -> new EntityNotFoundException("This username does not exist"));
        LocalDateTime now = LocalDateTime.now();

        if (activateUser.getActivationCode().equalsIgnoreCase(user.getNewEmailCode()) && 
                now.isBefore(user.getNewEmailDate().plusMinutes(5))) {
            user.setEmail(user.getNewEmail());
            user.setNewEmail(null);
            user.setNewEmailCode(null);
            user.setNewEmailDate(null);
            user = userRepository.save(user);
            sparkPostService.confirmUpdate(user.getEmail(), user.getUsername());
            return user;
        } else {
            throw new ActivationException("Activation failed");
        }
    }

    public String getCode() {
        String newPassCode = "";

        for (int i = 0; i < 6; i++) {
            Integer n = random.nextInt(9);
            newPassCode += n.toString();
        }

        return newPassCode;
    }

}
