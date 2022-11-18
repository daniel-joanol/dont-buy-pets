package com.danieljoanol.dontbuypets.service.moderator;

import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.danieljoanol.dontbuypets.entity.Moderator;
import com.danieljoanol.dontbuypets.repository.ModeratorRepository;

@Service
public class ModeratorServiceImpl implements ModeratorService {
    
    @Autowired
    private ModeratorRepository moderatorRepository;

    @Override
    public Moderator create(Moderator moderator) {
        moderator.setId(null);
        return moderatorRepository.save(moderator);
    }

    @Override
    public void delete(Long id) {
        moderatorRepository.deleteById(id);
        
    }

    @Override
    public List<Moderator> getAll() {
        return moderatorRepository.findAll();
    }

    @Override
    public Moderator getById(Long id) {
        return moderatorRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Id " + id + " not found"));
    }

    @Override
    public Moderator update(Moderator moderator) {
        return moderatorRepository.save(moderator);
    }

}
