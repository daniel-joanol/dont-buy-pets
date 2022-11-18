package com.danieljoanol.dontbuypets.service.moderator;

import java.util.List;

import com.danieljoanol.dontbuypets.entity.Moderator;

public interface ModeratorService {
    
    List<Moderator> getAll();

    Moderator getById(Long id);

    Moderator create(Moderator moderator);

    Moderator update(Moderator moderator);

    void delete(Long id);

}
