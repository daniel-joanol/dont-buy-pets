package com.danieljoanol.dontbuypets.service.pet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.danieljoanol.dontbuypets.entity.Pet;
import com.danieljoanol.dontbuypets.exception.EmptyImageException;
import com.danieljoanol.dontbuypets.exception.InvalidImageFormatException;
import com.danieljoanol.dontbuypets.repository.PetRepository;
import com.danieljoanol.dontbuypets.service.cloud.CloudinaryService;
import com.danieljoanol.dontbuypets.service.generic.GenericServiceImpl;

@Service
public class PetServiceImpl extends GenericServiceImpl<Pet> implements PetService {

    private final PetRepository petRepository;

    @Autowired
    private CloudinaryService cloudinaryService;

    public PetServiceImpl(PetRepository petRepository) {
        super(petRepository);
        this.petRepository = petRepository;
    }

    @Override
    public Pet create(Pet pet) {
        pet.setId(null);
        return petRepository.save(pet);
    }

    @Override
    public String addImage(Long id, MultipartFile image)
            throws EmptyImageException, InvalidImageFormatException, IOException {
        Pet pet = petRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Id " + id + " not found"));
        String URL = cloudinaryService.uploadImage(image);
        pet.getImages().add(URL);
        pet = petRepository.save(pet);
        return URL;
    }

    @Override
    public List<String> addImages(Long id, MultipartFile[] images)
            throws EmptyImageException, InvalidImageFormatException, IOException {
        List<String> response = new ArrayList<>();
        Pet pet = petRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Id " + id + " not found"));
        for (MultipartFile image : images) {
            String URL = cloudinaryService.uploadImage(image);
            pet.getImages().add(URL);
            response.add(URL);
        }

        pet = petRepository.save(pet);
        return response;
    }
    
}
