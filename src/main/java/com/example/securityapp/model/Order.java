package com.example.securityapp.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name="`order`")
@Entity
public class Order{
    @Id
    @Column(name="order_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;

    @Column(name="nameSender")
    private String nameSender;

    @Column(name="phoneSender")
    private String phoneSender;

    @Column(name="addressSender")
    private String addressSender;

    @Column(name="emailSender")
    private String emailSender;

    @Column(name="nameReceiver")
    private String nameReceiver;

    @Column(name="phoneReceiver")
    private String phoneReceiver;

    @Column(name="addressReceiver")
    private String addressReceiver;

    @Column(name="emailReceiver")
    private String emailReceiver;

    @Column(name="longitude")
    private Integer longitude;

    @Column(name="latitude")
    private Integer latitude;

    public Order(String nameSender, String phoneSender, String addressSender, String emailSender, String nameReceiver, String phoneReceiver, String addressReceiver, String emailReceiver, Integer longitude, Integer latitude) {
        this.nameSender = nameSender;
        this.phoneSender = phoneSender;
        this.addressSender = addressSender;
        this.emailSender = emailSender;
        this.nameReceiver = nameReceiver;
        this.phoneReceiver = phoneReceiver;
        this.addressReceiver = addressReceiver;
        this.emailReceiver = emailReceiver;
        this.longitude = longitude;
        this.latitude = latitude;
    }
}
