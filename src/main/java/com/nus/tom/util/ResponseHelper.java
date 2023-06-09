package com.nus.tom.util;

import com.nus.tom.model.ErrorObject;
import com.nus.tom.model.ResponseValueObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class ResponseHelper {
    public ResponseEntity<ResponseValueObject> setResponseEntity(String message, String errorMsg, String payload) {
        ResponseValueObject responseValueObj = ResponseValueObject.builder()
                .message(message).payload(payload).build();

        if (!StringUtils.isEmpty(errorMsg)) {
            responseValueObj.setErrors((ErrorObject.builder().errorMsg(errorMsg).build()));
            return new ResponseEntity<>(responseValueObj, HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(responseValueObj, HttpStatus.OK);

    }
}
