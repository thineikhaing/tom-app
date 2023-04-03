package com.nus.tom.service;

import com.nus.tom.model.Leave;
import com.nus.tom.model.ResponseValueObject;
import org.springframework.http.ResponseEntity;

public interface UpdatePasswordService {
    

void updatePassword(String userId,String password);

}
