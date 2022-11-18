package com.danieljoanol.dontbuypets.service.user;

import java.io.IOException;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.danieljoanol.dontbuypets.entity.User;
import com.danieljoanol.dontbuypets.exception.EmptyImageException;
import com.danieljoanol.dontbuypets.exception.InvalidImageFormatException;
import com.danieljoanol.dontbuypets.repository.UserRepository;
import com.danieljoanol.dontbuypets.service.cloud.CloudinaryService;
import com.danieljoanol.dontbuypets.service.generic.GenericServiceImpl;

@Service
public class UserServiceImpl extends GenericServiceImpl<User> implements UserService {
    
    private final UserRepository userRepository;

    @Autowired
    private CloudinaryService cloudinaryService;

    public UserServiceImpl(UserRepository userRepository) {
        super(userRepository);
        this.userRepository = userRepository; 
    }

    @Override
    public User create(User moderator) {
        moderator.setId(null);
        return userRepository.save(moderator);
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

}
