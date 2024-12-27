package net.mahtabalam.repository;

import net.mahtabalam.model.User;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class UserRepository {
    private final Map<Long, User> users = new HashMap<>();

    public User findById(Long id) {
        return users.get(id);
    }

    public void save(User user) {
        users.put(user.getId(), user);
    }
}