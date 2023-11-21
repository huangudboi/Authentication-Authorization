package com.example.securityapp.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "messages")
public class Message implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name="username")
    private String userName;
    @Column(name="password")
    private String password;
    @Column(name="messages")
    private String messages;
    @Column(name="status")
    private Integer status;
    @Column(name="created_on")
    private String created_on;

}
