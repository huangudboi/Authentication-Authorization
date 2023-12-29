package com.example.securityapp.service;

import com.example.securityapp.model.PasswordResetToken;
import org.springframework.http.ResponseEntity;

public interface PassResetService {

    PasswordResetToken saveOrUpdate(PasswordResetToken passwordResetToken);
    PasswordResetToken getLastTokenByUserId(int userId);
    ResponseEntity<?> resetPassword(String userName);
    ResponseEntity<?> creatNewPass(String userName, String token, String newPassword);
}
