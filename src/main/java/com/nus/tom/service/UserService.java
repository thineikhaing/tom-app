package com.nus.tom.service;

import com.nus.tom.dtos.UserDTO;
import com.nus.tom.model.Employee;
import com.nus.tom.model.ResponseValueObject;
import com.nus.tom.model.User;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface UserService {

    ResponseEntity<ResponseValueObject> save(UserDTO user);

    UserDTO createUser(UserDTO userDTO);

    UserDTO updateUser(String userId, UserDTO userDTO);

    void deleteUser(String userId);

    UserDTO getUserById(String userId);

    List<UserDTO> getAllUsers();

}
