package com.example.securityapp.service.impl;

import com.example.securityapp.model.ERole;
import com.example.securityapp.model.Role;
import com.example.securityapp.repository.RoleRepository;
import com.example.securityapp.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleRepository roleRepository;
    @Override
    public Optional<Role> findByRoleName(ERole roleName) {

        return roleRepository.findByRoleName(roleName);
    }
}
