package com.example.securityapp.model;

import lombok.Data;

import jakarta.persistence.*;
import java.util.Date;
@Data
@Entity
public class PasswordResetToken {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String token;
    @OneToOne(targetEntity = User.class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "user_id")
    private User user;
    private Date startDate;
}
