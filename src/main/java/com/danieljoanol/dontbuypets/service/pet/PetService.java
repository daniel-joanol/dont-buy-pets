package com.danieljoanol.dontbuypets.service.pet;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.danieljoanol.dontbuypets.entity.Pet;
import com.danieljoanol.dontbuypets.exception.EmptyImageException;
import com.danieljoanol.dontbuypets.exception.InvalidImageFormatException;
import com.danieljoanol.dontbuypets.service.generic.GenericService;

public interface PetService extends GenericService<Pet> {

    String addImage(Long id, MultipartFile image)
            throws EmptyImageException, InvalidImageFormatException, IOException;

    List<String> addImages(Long id, MultipartFile[] images)
            throws EmptyImageException, InvalidImageFormatException, IOException;
    
}
