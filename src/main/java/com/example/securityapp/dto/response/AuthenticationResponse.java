package com.example.securityapp.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationResponse {

    private String message;
    private Boolean status;
    private String userName;
    private String token;

    public AuthenticationResponse(String message, Boolean status) {
        this.message = message;
        this.status = status;
    }

    public AuthenticationResponse(String message, Boolean status, String token) {
        this.message = message;
        this.status = status;
        this.token = token;
    }
}
