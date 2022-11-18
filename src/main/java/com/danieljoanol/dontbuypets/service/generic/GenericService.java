package com.danieljoanol.dontbuypets.service.generic;

import java.util.List;

public interface GenericService<T> {
    
    public List<T> getAll();

    public T get(Long id);

    public T update(T update);

    public T create(T create);

    public List<T> create(List<T> tList);

    public void delete(Long id);

}
