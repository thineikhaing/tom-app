package com.nus.tom.controller;

import com.nus.tom.model.ResponseValueObject;
import com.nus.tom.service.LeaveService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

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
    public ResponseEntity<List<Map<String, Object>>> getLeaveBalance(@PathVariable String employeeId) {
        return leaveService.getLeaveBalance(employeeId);
    }

    @PostMapping(path = "/updateLeaveStatus", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseValueObject> updateLeaveStatus(@RequestBody String leave) {
        log.info("leave request received {} ", leave);
        return leaveService.updateLeaveStatus(leave);
    }

    @GetMapping(path = "/getLeaveRequest/{employeeId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Map<String, Object>>> getLeaveRequest(@PathVariable String employeeId) {
        return leaveService.getLeaveRequests(employeeId);
    }


}
