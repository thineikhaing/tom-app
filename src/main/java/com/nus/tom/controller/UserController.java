package com.nus.tom.controller;

import com.nus.tom.dtos.UserDTO;
import com.nus.tom.dtos.UserMapper;
import com.nus.tom.model.User;
import com.nus.tom.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

//    private final UserService userService;
//    private final UserMapper userMapper;
//
//    public UserController(UserService userService, UserMapper userMapper) {
//        this.userService = userService;
//        this.userMapper = userMapper;
//    }
//
//    @GetMapping("/{id}")
//    public ResponseEntity<UserDTO> getUserById(@PathVariable String id) {
//        User user = userService.getUserById(id);
//        if (user == null) {
//            return ResponseEntity.notFound().build();
//        }
//        UserDTO userDTO = userMapper.toUserDTO(user);
//        return ResponseEntity.ok(userDTO);
//    }
//
//    @PostMapping
//    public ResponseEntity<UserDTO> createUser(@RequestBody UserDTO userDTO) {
//        User user = userMapper.toUser(userDTO);
//        user = userService.createUser(user);
//        userDTO = userMapper.toUserDTO(user);
//        return ResponseEntity.created(URI.create("/users/" + userDTO.getId())).body(userDTO);
//    }
//
//    @PutMapping("/{id}")
//    public ResponseEntity<UserDTO> updateUser(@PathVariable String id, @RequestBody UserDTO userDTO) {
//        User user = userMapper.toUser(userDTO);
//        user.setId(id);
//        user = userService.updateUser(user);
//        userDTO = userMapper.toUserDTO(user);
//        return ResponseEntity.ok(userDTO);
//    }
//
//    @DeleteMapping("/{id}")
//    public ResponseEntity<Void> deleteUser(@PathVariable String id) {
//        userService.deleteUser(id);
//        return ResponseEntity.noContent().build();
//    }
}

