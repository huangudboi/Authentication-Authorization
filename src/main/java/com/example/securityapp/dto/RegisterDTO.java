package com.example.securityapp.dto;

import com.example.securityapp.model.ERole;
import com.example.securityapp.model.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterDTO {

    private String fullName;
    private String userName;
    private String email;
    private String password;
    private Set<ERole> listRoles = new HashSet<>();
}
