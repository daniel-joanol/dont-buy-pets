package com.danieljoanol.dontbuypets.service.user;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.danieljoanol.dontbuypets.controller.request.ActivateUserDTO;
import com.danieljoanol.dontbuypets.entity.Role;
import com.danieljoanol.dontbuypets.entity.User;
import com.danieljoanol.dontbuypets.entity.UserCodes;
import com.danieljoanol.dontbuypets.exception.ActivationException;
import com.danieljoanol.dontbuypets.exception.DuplicatedUserDataException;
import com.danieljoanol.dontbuypets.exception.EmptyImageException;
import com.danieljoanol.dontbuypets.exception.InvalidImageFormatException;
import com.danieljoanol.dontbuypets.repository.RoleRepository;
import com.danieljoanol.dontbuypets.repository.UserCodesRepository;
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

    @Autowired
    private UserCodesRepository userCodesRepository;

    @Autowired
    private PasswordEncoder encoder;

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
        entity.setPassword(encoder.encode(entity.getPassword()));
        Set<Role> roles = new HashSet<>();
        roles.add(roleRepository.findByName("USER")
                .orElseThrow(() -> new Exception("Error while trying to assing roles/permissions")));
        entity.setRoles(roles);

        if (userRepository.existsByEmail(entity.getEmail())) {
            throw new DuplicatedUserDataException("This email is already being used");
        }

        if (userRepository.existsByUsername(entity.getUsername())) {
            throw new DuplicatedUserDataException("This username is already being used");
        }

        entity = userRepository.save(entity);

        UserCodes codes = new UserCodes();
        codes.setUserId(entity.getId());
        String code = getCode();
        codes.setActivationCode(code);
        codes.setActivationCodeDate(LocalDateTime.now());
        codes = userCodesRepository.save(codes);

        sparkPostService.sendActivationCode(
                entity.getEmail(), entity.getUsername(), codes.getActivationCode());

        return DEFAULT_MESSAGE;
    }

    @Override
    public String newActivationCode(ActivateUserDTO activateUser) throws SparkPostException, ActivationException {
        User user = userRepository.findByUsername(activateUser.getUsername())
                .orElseThrow(() -> new EntityNotFoundException("This username does not exist"));

        if (!user.getEmail().equalsIgnoreCase(activateUser.getEmail())) {
            throw new ActivationException("This username does not belong to this email");
        }

        UserCodes codes = userCodesRepository.findByUserId(user.getId()).orElseThrow();
        String code = getCode();
        codes.setActivationCode(code);
        codes.setActivationCodeDate(LocalDateTime.now());
        codes = userCodesRepository.save(codes);
        
        sparkPostService.sendActivationCode(
                activateUser.getEmail(), activateUser.getUsername(), codes.getActivationCode());
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

        UserCodes codes = userCodesRepository.findByUserId(user.getId()).orElseThrow();

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
                codes.setNewEmail(update.getEmail());
                String code = getCode();
                codes.setNewEmailCode(code);
                codes.setNewEmailCodeDate(LocalDateTime.now());
                codes = userCodesRepository.save(codes);
                sparkPostService.sendNewEmailCode(
                    codes.getNewEmail(), user.getUsername(), codes.getNewEmailCode());
            }
        }

        if (!update.getPassword().equalsIgnoreCase(user.getPassword())) {
            codes.setNewPassword(update.getPassword());
            String code = getCode();
            codes.setNewPassCode(code);
            codes.setNewPassCodeDate(LocalDateTime.now());
            codes = userCodesRepository.save(codes);
            sparkPostService.sendActivationCode(
                    user.getEmail(), user.getUsername(), codes.getNewPassCode());
        }

        return DEFAULT_MESSAGE;
    }

    @Override
    public User activateUser(ActivateUserDTO activateUser) throws SparkPostException, ActivationException {
        User user = userRepository.findByUsername(activateUser.getUsername())
                .orElseThrow(() -> new EntityNotFoundException("This username does not exist"));
        UserCodes codes = userCodesRepository.findByUserId(user.getId()).orElseThrow();
        LocalDateTime now = LocalDateTime.now();

        if (activateUser.getActivationCode().equals(codes.getActivationCode())
                && now.isBefore(codes.getActivationCodeDate().plusMinutes(5))) {
            user.setActive(true);
            codes.setActivationCode(null);
            codes.setActivationCodeDate(null);
            user = userRepository.save(user);
            codes = userCodesRepository.save(codes);
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
        UserCodes codes = userCodesRepository.findByUserId(user.getId()).orElseThrow();
        LocalDateTime now = LocalDateTime.now();

        if (activateUser.getActivationCode().equalsIgnoreCase(codes.getNewPassCode())
                && now.isBefore(codes.getNewPassCodeDate().plusMinutes(5))) {
            user.setPassword(codes.getNewPassword());
            codes.setNewPassword(null);
            codes.setNewPassCodeDate(null);
            codes.setNewPassCode(null);
            user = userRepository.save(user);
            codes = userCodesRepository.save(codes);
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
        UserCodes codes = userCodesRepository.findByUserId(user.getId()).orElseThrow();
        LocalDateTime now = LocalDateTime.now();

        if (activateUser.getActivationCode().equalsIgnoreCase(codes.getNewEmailCode()) && 
                now.isBefore(codes.getNewEmailCodeDate().plusMinutes(5))) {
            user.setEmail(codes.getNewEmail());
            codes.setNewEmail(null);
            codes.setNewEmailCode(null);
            codes.setNewEmailCodeDate(null);
            user = userRepository.save(user);
            codes = userCodesRepository.save(codes);
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
