package com.danieljoanol.dontbuypets.service.user;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

import com.danieljoanol.dontbuypets.entity.User;
import com.danieljoanol.dontbuypets.exception.EmptyImageException;
import com.danieljoanol.dontbuypets.exception.InvalidImageFormatException;
import com.danieljoanol.dontbuypets.service.generic.GenericService;

public interface UserService extends GenericService<User> {
    
    String addImage(Long id, MultipartFile image)
            throws EmptyImageException, InvalidImageFormatException, IOException;
}
