package com.nus.tom.dtos;


import com.nus.tom.model.enums.ERole;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RoleDTO {
    private String id;
    private String name;

    public RoleDTO() {}

    public RoleDTO(String id, String name) {
        this.id = id;
        this.name = name;
    }


}