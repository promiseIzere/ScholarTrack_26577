package com.scholartrack.service;

import com.scholartrack.model.User;
import com.scholartrack.model.Location;
import com.scholartrack.repository.UserRepository;
import com.scholartrack.repository.LocationRepository;
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

    @Autowired
    private LocationRepository locationRepository;

    public User create(User user) {
        user.setCreatedAt(LocalDateTime.now());
        if (user.getLocation() != null) {
            Location provided = user.getLocation();
            Location attached = null;
            if (provided.getId() != null) {
                attached = locationRepository.findById(provided.getId()).orElse(null);
            }
            if (attached == null && provided.getCode() != null) {
                attached = locationRepository.findByCode(provided.getCode()).orElse(null);
            }
            if (attached == null && (provided.getName() != null || provided.getCode() != null)) {
                attached = locationRepository.save(provided);
            }
            user.setLocation(attached);
        }
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
            // Update location if provided
            if (update.getLocation() != null) {
                Location provided = update.getLocation();
                Location attached = null;
                if (provided.getId() != null) {
                    attached = locationRepository.findById(provided.getId()).orElse(null);
                }
                if (attached == null && provided.getCode() != null) {
                    attached = locationRepository.findByCode(provided.getCode()).orElse(null);
                }
                if (attached == null && (provided.getName() != null || provided.getCode() != null)) {
                    attached = locationRepository.save(provided);
                }
                existing.setLocation(attached);
            }
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
