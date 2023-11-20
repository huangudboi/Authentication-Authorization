package com.example.securityapp.controller;

import com.example.securityapp.Dto.LoginDTO;
import com.example.securityapp.Dto.RegisterDTO;
import com.example.securityapp.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/v1/user")
@RequiredArgsConstructor
public class UserController {

    @Autowired
    private UserService service;

    @PostMapping(path = "/register")
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody RegisterDTO registerDTO
    ){
        return ResponseEntity.ok(service.register(registerDTO));
    }

    @PostMapping(path = "/login")
    public ResponseEntity<AuthenticationResponse> login(
            @RequestBody LoginDTO loginDTO
    ){
        return ResponseEntity.ok(service.login(loginDTO));
    }
}
