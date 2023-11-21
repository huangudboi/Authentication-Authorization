package com.example.securityapp.Dto.response;

import lombok.Data;

@Data
public class ChangePassResponse {
    private String message;

    public ChangePassResponse() {
    }
    public ChangePassResponse(String message) {
        this.message = message;
    }
}
