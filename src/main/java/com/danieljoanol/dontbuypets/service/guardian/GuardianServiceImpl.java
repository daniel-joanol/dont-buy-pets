package com.danieljoanol.dontbuypets.service.guardian;

import java.io.IOException;
import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.danieljoanol.dontbuypets.entity.Guardian;
import com.danieljoanol.dontbuypets.exception.EmptyImageException;
import com.danieljoanol.dontbuypets.exception.InvalidImageFormatException;
import com.danieljoanol.dontbuypets.repository.GuardianRepository;
import com.danieljoanol.dontbuypets.service.cloud.CloudinaryService;
import com.danieljoanol.dontbuypets.service.generic.GenericServiceImpl;

@Service
public class GuardianServiceImpl extends GenericServiceImpl<Guardian> implements GuardianService {

    private final GuardianRepository guardianRepository;

    @Autowired
    private CloudinaryService cloudinaryService;

    public GuardianServiceImpl(GuardianRepository guardianRepository) {
        super(guardianRepository);
        this.guardianRepository = guardianRepository;
    }

    @Override
    public Guardian create(Guardian guardian) {
        guardian.setId(null);
        return guardianRepository.save(guardian);
    }
    
    @Override
    public String addImage(Long id, MultipartFile image) 
            throws EmptyImageException, InvalidImageFormatException, IOException {
        Guardian guardian = guardianRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Id " + id + " not found"));
        String URL = cloudinaryService.uploadImage(image);
        guardian.setImage(URL);
        guardian = guardianRepository.save(guardian);
        return guardian.getImage();
    }
    
}
