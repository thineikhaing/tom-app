package com.nus.tom.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nus.tom.dtos.EmployeeDTO;
import com.nus.tom.dtos.LeaveDTO;
import com.nus.tom.model.Leave;
import com.nus.tom.model.ResponseValueObject;
import com.nus.tom.service.LeaveService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("api/tom/leave")
@RequiredArgsConstructor
public class LeaveController {

    private final LeaveService leaveService;

    @PostMapping(path = "/applyLeave", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseValueObject> save(@RequestBody String leave) {
        log.info("leave request received {} ", leave);
        return leaveService.save(leave);
    }

    @GetMapping(path = "/getLeaveBalance/{employeeId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseValueObject> getLeaveBalance(@PathVariable String employeeId) {
        return leaveService.getLeaveBalance(employeeId);
    }


}
