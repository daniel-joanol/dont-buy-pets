package com.danieljoanol.dontbuypets.service.guardian;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.danieljoanol.dontbuypets.entity.Guardian;
import com.danieljoanol.dontbuypets.exception.EmptyImageException;
import com.danieljoanol.dontbuypets.exception.InvalidImageFormatException;

public interface GuardianService {
    
    List<Guardian> getAll();

    Guardian getById(Long id);

    Guardian create(Guardian guardian);

    Guardian update(Guardian guardian);

    void delete(Long id);

    String addImage(Long id, MultipartFile image) 
            throws EmptyImageException, InvalidImageFormatException, IOException;

}
