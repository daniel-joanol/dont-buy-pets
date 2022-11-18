package com.danieljoanol.dontbuypets.service.cloud;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

import com.danieljoanol.dontbuypets.exception.EmptyImageException;
import com.danieljoanol.dontbuypets.exception.InvalidImageFormatException;

public interface CloudinaryService {
    String uploadImage(MultipartFile image) 
            throws EmptyImageException, InvalidImageFormatException, IOException;
}
