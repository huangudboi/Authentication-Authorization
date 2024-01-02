package com.example.securityapp.service;

import com.example.securityapp.dto.ChangePasswordDTO;
import com.example.securityapp.dto.LoginDTO;
import com.example.securityapp.dto.RegisterDTO;
import com.example.securityapp.dto.UpdateUserDTO;
import com.example.securityapp.dto.response.AuthenticationResponse;
import org.springframework.http.ResponseEntity;

public interface UserService {

    AuthenticationResponse register(RegisterDTO registerDTO);
    AuthenticationResponse login(LoginDTO loginDTO);
    ResponseEntity<?> updateUser(UpdateUserDTO updateUserDTO);
    ResponseEntity<?> deleteUser(int userId);
    ResponseEntity<?> updatePassword(ChangePasswordDTO changePasswordDTO);

}
