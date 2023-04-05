package com.nus.tom.dtos;

import com.nus.tom.model.Leave;
import lombok.*;

import java.time.Instant;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LeaveDTO {
    private String id;
    private String name;
    private Instant startDate;
    private Instant endDate;
    private String status;
    private String leaveType;

}