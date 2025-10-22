package com.scholartrack.service;

import com.scholartrack.model.User;
import com.scholartrack.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User create(User user) {
        user.setCreatedAt(LocalDateTime.now());
        return userRepository.save(user);
    }

    @Transactional(readOnly = true)
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<User> findById(UUID id) {
        return userRepository.findById(id);
    }

    @Transactional(readOnly = true)
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Transactional(readOnly = true)
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Transactional(readOnly = true)
    public List<User> findByRole(User.Role role) {
        return userRepository.findByRole(role);
    }

    @Transactional(readOnly = true)
    public List<User> findByStatus(User.Status status) {
        return userRepository.findByStatus(status);
    }

    public Optional<User> update(UUID id, User update) {
        return userRepository.findById(id).map(existing -> {
            existing.setUsername(update.getUsername());
            existing.setEmail(update.getEmail());
            existing.setPassword(update.getPassword());
            existing.setFirstName(update.getFirstName());
            existing.setLastName(update.getLastName());
            existing.setRole(update.getRole());
            existing.setStatus(update.getStatus());
            existing.setUpdatedAt(LocalDateTime.now());
            return existing;
        });
    }

    public void delete(UUID id) {
        userRepository.deleteById(id);
    }

    public void updateLastLogin(UUID id) {
        userRepository.findById(id).ifPresent(user -> {
            user.setLastLogin(LocalDateTime.now());
            userRepository.save(user);
        });
    }
}
