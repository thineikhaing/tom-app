package com.nus.tom.dtos;

import lombok.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProjectDTO implements Serializable {
    private Long id;
    private String name;
    private Instant startDate;
    private Instant endDate;


}
