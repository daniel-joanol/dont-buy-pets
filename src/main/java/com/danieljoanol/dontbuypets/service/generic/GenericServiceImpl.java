package com.danieljoanol.dontbuypets.service.generic;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityNotFoundException;

import com.danieljoanol.dontbuypets.entity.GenericEntity;
import com.danieljoanol.dontbuypets.repository.GenericRepository;

public abstract class GenericServiceImpl<T extends GenericEntity<T>> {
    
    private final GenericRepository<T> repository;

    public GenericServiceImpl(GenericRepository<T> repository) {
        this.repository = repository;
    }

    public List<T> getAll() {
        return repository.findAll();
    }

    public T get(Long id) {
        return repository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Id " + id + " not found"));
    }

    public T update(T update) {
        return repository.save(update);
    }

    public T create(T create) {
        return repository.save(create);
    }

    public List<T> create(List<T> tList) {
        List<T> response = new ArrayList<>();
        for (T t : tList) {
            response.add(create(t));
        }
        return response;
    }

    public void delete(Long id) {
        // Check if the entity exists
        get(id);
        repository.deleteById(id);
    }
}
