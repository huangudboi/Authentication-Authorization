package com.example.securityapp.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterDTO {

    private String fullName;
    private String userName;
    private String email;
    private String password;
    private Set<String> listRoles = new HashSet<>();
}
