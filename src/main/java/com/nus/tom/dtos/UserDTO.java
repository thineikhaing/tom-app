package com.nus.tom.dtos;

import java.util.Collection;
import java.util.Set;

public class UserDTO {
    private String id;
    private String username;
    private String email;
    private Set<RoleDTO> roles;

    public UserDTO(String id, String username, String email, Set<RoleDTO> roles) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.roles = roles;
    }

    public UserDTO() {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Set<RoleDTO> getRoles() {
        return roles;
    }

    public void setRoles(Set<RoleDTO> roles) {
        this.roles = roles;
    }

}