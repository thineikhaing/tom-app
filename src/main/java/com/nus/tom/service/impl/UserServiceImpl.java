package com.nus.tom.service.impl;

import com.nus.tom.dtos.UserDTO;
import com.nus.tom.model.ResponseValueObject;
import com.nus.tom.model.Role;
import com.nus.tom.model.User;
import com.nus.tom.repository.UserRepository;
import com.nus.tom.service.UserService;
import com.nus.tom.util.ResourceNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public ResponseEntity<ResponseValueObject> save(UserDTO user) {
        return null;
    }

    @Override
    public UserDTO createUser(UserDTO userDTO) {
        // Create a new User entity from the DTO
        User user = modelMapper.map(userDTO, User.class);
        // Encode the password before saving the user
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        // Save the user entity
        User savedUser = userRepository.save(user);
        // Return the DTO of the saved user
        return modelMapper.map(savedUser, UserDTO.class);
    }

    @Override
    public UserDTO updateUser(String userId, UserDTO userDTO) {
        // Find the User entity to update
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            // Update the fields of the User entity from the DTO
            user.setUsername(userDTO.getUsername());
            user.setEmail(userDTO.getEmail());
            // If the password field is not empty, encode the new password
//            if (!userDTO.getPassword().isEmpty()) {
//                user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
//            }
//            // Update the roles of the User entity from the DTO
//            Set<Role> roles = user.getRoles().stream()
//                    .filter(role -> userDTO.getRoleIds().contains(role.getId()))
//                    .collect(Collectors.toSet());
//            user.setRoles(roles);
            // Save the updated User entity
            User savedUser = userRepository.save(user);
            // Return the DTO of the saved user
            return modelMapper.map(savedUser, UserDTO.class);
        } else {
            throw new ResourceNotFoundException("User", "id", userId);
        }
    }

    @Override
    public void deleteUser(String userId) {
        // Delete the User entity by ID
        userRepository.deleteById(userId);
    }

    @Override
    public UserDTO getUserById(String userId) {
        // Find the User entity by ID
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isPresent()) {
            // Return the DTO of the found user
            return modelMapper.map(optionalUser.get(), UserDTO.class);
        } else {
            throw new ResourceNotFoundException("User", "id", userId);
        }
    }

    @Override
    public List<UserDTO> getAllUsers() {
        // Find all User entities and map them to DTOs
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(user -> modelMapper.map(user, UserDTO.class))
                .collect(Collectors.toList());
    }
}
