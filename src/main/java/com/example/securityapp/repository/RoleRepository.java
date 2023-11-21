package com.example.securityapp.repository;

import com.example.securityapp.model.ERole;
import com.example.securityapp.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role,Integer> {
    Optional<Role> findByRoleName(ERole roleName);
}