package com.nus.tom.dtos;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;
@Getter
@Setter
public class UserDTO {
    private String id;
    private String username;
    private String email;
    private Set<String> roles;

    public UserDTO(String id, String username, String email, Set<String> roles) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.roles = roles;
    }

    public UserDTO() {

    }


}