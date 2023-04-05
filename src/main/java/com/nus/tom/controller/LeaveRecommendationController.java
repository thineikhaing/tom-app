package com.nus.tom.controller;

import com.nus.tom.model.ResponseValueObject;
import com.nus.tom.service.LeaveRecommendationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("api/tom/leave/recommend")
@RequiredArgsConstructor
public class LeaveRecommendationController {
    private final LeaveRecommendationService leaveRecommendationService;

    @GetMapping(path = "/getRecommendation/{employeeId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseValueObject> getRecommendation(@PathVariable String employeeId) {
        return leaveRecommendationService.recommend(employeeId);
    }


}
