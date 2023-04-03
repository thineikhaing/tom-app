package com.nus.tom.controller;

import com.nus.tom.model.ResponseValueObject;
import com.nus.tom.service.UpdatePasswordService;
import com.nus.tom.util.ResponseHelper;
import com.nus.tom.util.TOMConstants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@Slf4j
@RequiredArgsConstructor
public class UpdatePasswordController {
    private final ResponseHelper responseHelper;

    private final UpdatePasswordService updatePasswordService;


    @GetMapping(path = "/updatePasswordPage/{userId}", produces = MediaType.TEXT_HTML_VALUE)
    public byte[] getUpdatePage(@PathVariable String userId) throws IOException {
        ClassPathResource classPathResource = new ClassPathResource("updatepassword/updatepassword.html");
        return IOUtils.toByteArray(classPathResource.getInputStream());
    }

    @PostMapping(path = "/activate")
    public ResponseEntity<ResponseValueObject> save(@RequestPart("userId") String userId, @RequestPart("password") String password) {

        updatePasswordService.updatePassword(userId, password);
        return responseHelper.setResponseEntity(TOMConstants.ACTIVATED, TOMConstants.EMPTY_STRING, userId);

    }

}
