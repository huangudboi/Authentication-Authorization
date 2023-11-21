package com.example.securityapp.service;


import com.example.securityapp.model.PasswordResetToken;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;

public interface PassResetService {

    PasswordResetToken saveOrUpdate(PasswordResetToken passwordResetToken);
    PasswordResetToken getLastTokenByUserId(int userId);
    ResponseEntity<?> resetPassword(String userEmail, HttpServletRequest request);
    ResponseEntity<?> creatNewPass(String token, String newPassword);
}
