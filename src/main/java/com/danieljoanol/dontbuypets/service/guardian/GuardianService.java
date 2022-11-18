package com.danieljoanol.dontbuypets.service.guardian;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

import com.danieljoanol.dontbuypets.entity.Guardian;
import com.danieljoanol.dontbuypets.exception.EmptyImageException;
import com.danieljoanol.dontbuypets.exception.InvalidImageFormatException;
import com.danieljoanol.dontbuypets.service.generic.GenericService;

public interface GuardianService extends GenericService<Guardian> {

    String addImage(Long id, MultipartFile image) 
            throws EmptyImageException, InvalidImageFormatException, IOException;

}
