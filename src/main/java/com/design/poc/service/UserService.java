package com.design.poc.service;

import com.design.poc.exception.DuplicateEmailException;
import com.design.poc.model.User;
import com.design.poc.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User save(User user) {
        try {
            return userRepository.save(user);
        } catch (DataIntegrityViolationException e) {
            if (e.getMessage().contains("Duplicate entry")) {
                throw new DuplicateEmailException("Email already exists: "+ user.getEmail());
            }
            throw e;
        }
    }

    public Optional<User> findById(long userId) {
        return userRepository.findById(userId);
    }

    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}
