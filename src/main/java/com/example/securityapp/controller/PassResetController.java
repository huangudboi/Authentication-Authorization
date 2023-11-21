package com.example.securityapp.controller;

import com.example.securityapp.service.PassResetService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpServletRequest;

@RestController
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:8080")
@RequestMapping("/api/v1/passReset")
public class PassResetController {

    @Autowired
    private PassResetService passResetService;

    @GetMapping("/resetPassword")
    public ResponseEntity<?> resetPassword(@RequestParam("email") String userEmail, HttpServletRequest request) {
        return ResponseEntity.ok(passResetService.resetPassword(userEmail, request));
    }
    @PostMapping("/creatNewPass")
    public ResponseEntity<?> creatNewPass(@RequestParam("token") String token, @RequestParam("newPassword") String newPassword) {
        return ResponseEntity.ok(passResetService.creatNewPass(token, newPassword));
    }
}
