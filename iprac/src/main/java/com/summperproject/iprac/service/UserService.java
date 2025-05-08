package com.summperproject.iprac.service;

import java.util.List;
import java.util.Optional;

import com.summperproject.iprac.entity.User;

public interface UserService {
    User createUser(User user);
    User updateUser(User user);
    Optional<User> getUserById(Long id);
    Optional<User> getUserByUsername(String username);
    Optional<User> getUserByEmail(String email);
    List<User> getAllUsers();
    void deleteUser(Long id);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
}
