package com.example.securityapp.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "Role")
@Data
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "RoleId")
    private int roleId;
    @Column(name = "RoleName")
    @Enumerated(EnumType.STRING)
    private ERole roleName;
}
