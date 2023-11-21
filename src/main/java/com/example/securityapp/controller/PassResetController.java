package com.example.securityapp.controller;

import com.example.securityapp.service.PassResetService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:8080")
@RequestMapping("/api/v1/passReset")
public class PassResetController {

    @Autowired
    private PassResetService passResetService;

    @GetMapping("/resetPassword")
    //Param thì cần truyền params vào khi callAPI không được sử dụng raw
    public ResponseEntity<?> resetPassword(@RequestParam("userName") String userName) {
        System.out.println(userName);
        return ResponseEntity.ok(passResetService.resetPassword(userName));
    }
    @PostMapping("/creatNewPass")
    public ResponseEntity<?> creatNewPass(@RequestParam("userName") String userName,
                                          @RequestParam("token") String token,
                                          @RequestParam("newPassword") String newPassword) {
        return ResponseEntity.ok(passResetService.creatNewPass(userName, token, newPassword));
    }
}
