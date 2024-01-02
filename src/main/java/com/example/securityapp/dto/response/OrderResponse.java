package com.example.securityapp.dto.response;

import com.example.securityapp.dto.OrderExcelDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderResponse {

    private List<OrderExcelDTO> listOrder;
    private String message;
    private HttpStatus httpStatus;

    public OrderResponse(String message, HttpStatus httpStatus) {
        this.message = message;
        this.httpStatus = httpStatus;
    }
}
