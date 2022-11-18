package com.danieljoanol.dontbuypets.service.guardian;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.danieljoanol.dontbuypets.entity.Guardian;
import com.danieljoanol.dontbuypets.exception.EmptyImageException;
import com.danieljoanol.dontbuypets.exception.InvalidImageFormatException;
import com.danieljoanol.dontbuypets.repository.GuardianRepository;
import com.danieljoanol.dontbuypets.service.cloud.CloudinaryService;

@Service
public class GuardianServiceImpl implements GuardianService {

    @Autowired
    private GuardianRepository guardianRepository;

    @Autowired
    private CloudinaryService cloudinaryService;

    @Override
    public Guardian create(Guardian guardian) {
        guardian.setId(null);
        return guardianRepository.save(guardian);
    }

    @Override
    public void delete(Long id) {
        guardianRepository.deleteById(id);
        
    }

    @Override
    public List<Guardian> getAll() {
        return guardianRepository.findAll();
    }

    @Override
    public Guardian getById(Long id) {
        //TODO: create a handler controller
        return guardianRepository.findById(id).orElseThrow();
    }

    @Override
    public Guardian update(Guardian guardian) {
        return guardianRepository.save(guardian);
    }
    
    @Override
    public String addImage(Long id, MultipartFile image) 
            throws EmptyImageException, InvalidImageFormatException, IOException {
        Guardian guardian = guardianRepository.findById(id).orElseThrow();
        String URL = cloudinaryService.uploadImage(image);
        guardian.setImage(URL);
        guardian = guardianRepository.save(guardian);
        return guardian.getImage();
        
    }
    
}
