package com.nus.tom.dtos;


import com.nus.tom.model.enums.ERole;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RoleDTO {
    private String id;
    private String name;

}