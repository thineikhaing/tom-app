package com.nus.tom.service;

import com.nus.tom.model.ResponseValueObject;
import org.springframework.http.ResponseEntity;

public interface LeaveRecommendationService {

    ResponseEntity<ResponseValueObject> recommend(String employeeId);

}
