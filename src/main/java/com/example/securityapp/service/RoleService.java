package com.example.securityapp.service;

import com.example.securityapp.model.ERole;
import com.example.securityapp.model.Role;

import java.util.Optional;

public interface RoleService {
    Optional<Role> findByRoleName(ERole roleName);
}