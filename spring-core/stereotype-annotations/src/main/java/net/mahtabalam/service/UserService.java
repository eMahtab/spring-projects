package net.mahtabalam.service;

import net.mahtabalam.model.User;
import net.mahtabalam.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User getUserById(Long id) {
        return userRepository.findById(id);
    }

    public void saveUser(User user) {
        userRepository.save(user);
    }
}