package com.nus.tom.service.impl;

import com.nus.tom.model.User;
import com.nus.tom.repository.UserRepository;
import com.nus.tom.service.UpdatePasswordService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class UpdatePasswordServiceImpl implements UpdatePasswordService {

    private  final UserRepository userRepository;
    @Autowired
    PasswordEncoder encoder;

    /**
     * set new password
     * @param userId
     * @param password
     */
    @Override
    public void updatePassword(String userId, String password) {
      User user =   userRepository.findById(userId).get();

      if(Objects.nonNull(user))
        {
            String newPwd = encoder.encode(password);
            user.setPassword(newPwd);
            user.setActivated(Boolean.TRUE);
            userRepository.save(user);
        }

    }
}
