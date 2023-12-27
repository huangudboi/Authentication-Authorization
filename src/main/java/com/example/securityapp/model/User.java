package com.example.securityapp.model;

import jakarta.persistence.*;
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
@Table(name="user")
@Entity
public class User {
    @Id
    @Column(name="user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int userId;
    @Column(name="full_name", length = 255)
    private String fullName;
    @Column(name="user_name", length = 255)
    private String userName;
    @Column(name="email", length = 255)
    private String email;
    @Column(name="password", length = 255)
    private String password;
    @Column(name="code", length = 255)
    private String code;

    @Enumerated(EnumType.STRING)
    private State state;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "User_Role", joinColumns = @JoinColumn(name = "UserId"),
            inverseJoinColumns = @JoinColumn(name = "RoleId"))
    private Set<Role> listRoles = new HashSet<>();

    public User(String fullName, String userName, String email, String password, String code, State state, Set<Role> listRoles) {
        this.fullName = fullName;
        this.userName = userName;
        this.email = email;
        this.password = password;
        this.code = code;
        this.state = state;
        this.listRoles = listRoles;
    }
}
