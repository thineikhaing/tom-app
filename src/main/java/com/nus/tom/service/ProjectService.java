package com.nus.tom.service;

import com.nus.tom.model.ResponseValueObject;
import org.springframework.http.ResponseEntity;

public interface ProjectService {

    ResponseEntity<ResponseValueObject> save(String project);

}
