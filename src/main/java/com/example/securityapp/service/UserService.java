package com.example.securityapp.service;

import com.example.securityapp.Dto.LoginDTO;
import com.example.securityapp.Dto.RegisterDTO;
import com.example.securityapp.controller.AuthenticationResponse;

public interface UserService {
     AuthenticationResponse register(RegisterDTO registerDTO);
     AuthenticationResponse login(LoginDTO loginDTO);
}
