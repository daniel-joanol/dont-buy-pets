package com.danieljoanol.dontbuypets.service.user;

import org.springframework.stereotype.Service;

import com.danieljoanol.dontbuypets.entity.User;
import com.danieljoanol.dontbuypets.repository.UserRepository;
import com.danieljoanol.dontbuypets.service.generic.GenericServiceImpl;

@Service
public class UserServiceImpl extends GenericServiceImpl<User> implements UserService {
    
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        super(userRepository);
        this.userRepository = userRepository; 
    }

    @Override
    public User create(User moderator) {
        moderator.setId(null);
        return userRepository.save(moderator);
    }

}
