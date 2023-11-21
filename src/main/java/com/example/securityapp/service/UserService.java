package com.example.securityapp.service;

import com.example.securityapp.Dto.ChangePasswordDTO;
import com.example.securityapp.Dto.LoginDTO;
import com.example.securityapp.Dto.RegisterDTO;
import com.example.securityapp.Dto.UpdateUserDTO;
import com.example.securityapp.Dto.response.AuthenticationResponse;
import com.example.securityapp.model.User;
import org.springframework.http.ResponseEntity;

public interface UserService {

    AuthenticationResponse register(RegisterDTO registerDTO);

    AuthenticationResponse login(LoginDTO loginDTO);

    ResponseEntity<?> updateUser(UpdateUserDTO updateUserDTO);

    ResponseEntity<?> deleteUser(int userId);

    ResponseEntity<?> updatePassword(ChangePasswordDTO changePasswordDTO);
}
