package com.example.securityapp.controller;

import com.example.securityapp.Dto.ChangePasswordDTO;
import com.example.securityapp.Dto.LoginDTO;
import com.example.securityapp.Dto.RegisterDTO;
import com.example.securityapp.Dto.UpdateUserDTO;
import com.example.securityapp.Dto.response.AuthenticationResponse;
import com.example.securityapp.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/v1/user")
@RequiredArgsConstructor
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping(path = "/register")
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody RegisterDTO registerDTO
    ){
        return ResponseEntity.ok(userService.register(registerDTO));
    }

    @PostMapping(path = "/login")
    public ResponseEntity<AuthenticationResponse> login(
            @RequestBody LoginDTO loginDTO
    ){
        return ResponseEntity.ok(userService.login(loginDTO));
    }

    @PutMapping("/updateUser")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> updateUser(@RequestBody UpdateUserDTO updateUserDTO) {
        return ResponseEntity.ok(userService.updateUser(updateUserDTO));
    }

    @DeleteMapping("/deleteUser")
    @PreAuthorize(" hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> deleteUser(Integer userId){
        return ResponseEntity.ok(userService.deleteUser(userId));
    }

    @PostMapping("/updatePassword")
    public ResponseEntity<?> updatePassword(@RequestBody ChangePasswordDTO changePasswordDTO){
        System.out.println(changePasswordDTO);
        return ResponseEntity.ok(userService.updatePassword(changePasswordDTO));
    }

}
