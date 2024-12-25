package net.mahtabalam;

import java.util.HashMap;
import java.util.Map;

public class UserRepository {
    private Map<Long, User> users = new HashMap<>();

    public User findById(Long id) {
        return users.get(id);
    }

    public void save(User user) {
        users.put(user.getId(), user);
    }
}