package com.example.securityapp.Dto;

import com.poiji.annotation.ExcelCell;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderExcelDTO {

    @ExcelCell(0)
    @NotBlank(message = "nameSender_empty")
    @Size(min=10, max=30, message = "Name has from 10 to 30 characters")
    private String nameSender;

    @ExcelCell(1)
    @NotBlank(message = "phoneSender_empty")
    private String phoneSender;

    @ExcelCell(2)
    @NotBlank(message = "addressSender_empty")
    private String addressSender;

    @ExcelCell(3)
    @Email
    @NotBlank(message = "emailSender_empty")
    private String emailSender;

    @ExcelCell(4)
    @NotBlank(message = "nameReceiver_empty")
    @Size(min=10, max=30, message = "Name has from 10 to 30 characters")
    private String nameReceiver;

    @ExcelCell(5)
    @NotBlank(message = "phoneReceiver_empty")
    private String phoneReceiver;

    @ExcelCell(6)
    @NotBlank(message = "addressReceiver_empty")
    private String addressReceiver;

    @ExcelCell(7)
    @NotBlank(message = "emailReceiver_empty")
    @Email
    private String emailReceiver;

    @ExcelCell(8)
    @NotBlank(message = "longitude_empty")
    @Min(0) @Max(360)
    private Integer longitude;

    @ExcelCell(9)
    @NotBlank(message = "latitude_empty")
    @Min(0) @Max(360)
    private Integer latitude;

}
