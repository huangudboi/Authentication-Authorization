package com.example.securityapp.service.impl;

import com.example.securityapp.dto.OrderExcelDTO;
import com.example.securityapp.dto.response.OrderResponse;
import com.example.securityapp.exceptions.OrderNotFoundException;
import com.example.securityapp.model.Order;
import com.example.securityapp.repository.OrderRepository;
import com.example.securityapp.service.OrderService;
import com.poiji.bind.Poiji;
import com.poiji.exception.PoijiExcelType;
import jakarta.validation.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

@Service
@Slf4j
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Override
    public List<Order> findAll() {
        return orderRepository.findAll();
    }

    @Override
    public Order createOrder(Order order) {
       return orderRepository.save(order);
    }

    @Override
    public void deleteOrderById(long orderId) {
        Order order = orderRepository.findByOrderId(orderId).orElseThrow(() ->
                new OrderNotFoundException("OrderId could not be found"));
        orderRepository.delete(order);
    }

    @Override
    public Order findByOrderId(Long orderId) {
        Order order = orderRepository.findByOrderId(orderId).orElseThrow(() ->
                new OrderNotFoundException("OrderId could not be found"));
        return order;
    }

    @Override
    public OrderResponse importAndValidateExcel(MultipartFile multipartFile){
        List<OrderExcelDTO> data = new ArrayList<>();

        try (InputStream inputStream = multipartFile.getInputStream()) {
            Workbook workbook = new XSSFWorkbook(inputStream);
            Sheet sheet = workbook.getSheetAt(0); // Assuming data is in the first sheet

            // Perform validation - Example: Check if column headers exist
            Row headerRow = sheet.getRow(0);
            if (headerRow == null) {
                return new OrderResponse("Excel file is empty", HttpStatus.NOT_FOUND);
            } else {
                Iterator<Cell> cellIterator = headerRow.cellIterator();
                List<String> expectedHeaders = List.of("nameSender", "phoneSender", "addressSender", "emailSender",
                        "nameSender", "phoneReceiver", "addressReceiver", "emailReceiver", "longitude", "latitude");

                while (cellIterator.hasNext()) {
                    Cell cell = cellIterator.next();
                    if (!expectedHeaders.contains(cell.getStringCellValue())) {
                        return new OrderResponse("Invalid column header: " + cell.getStringCellValue()
                                , HttpStatus.NOT_MODIFIED);
                    }
                }

                try {
                    data = Poiji.fromExcel(multipartFile.getInputStream(),
                            PoijiExcelType.XLSX, OrderExcelDTO.class);

                    // Process the imported data
                    for (OrderExcelDTO item : data) {
                        Set<ConstraintViolation<OrderExcelDTO>> violations = Validation
                                .buildDefaultValidatorFactory().getValidator().validate(item);
                        for (ConstraintViolation<OrderExcelDTO> violation : violations) {
                            return new OrderResponse("Order at index " + (data.indexOf(item)+1) +
                                    ": " + violation.getPropertyPath() + " " + violation.getMessage()
                                    , HttpStatus.NOT_MODIFIED);
                        }
                    }
                } catch (IOException e) {
                    // Handle the exception
                    e.printStackTrace();
                } catch (ValidationException e){
                    e.printStackTrace();
                }
            }

        }catch (IOException ex){
            ex.printStackTrace();
        }

        return new OrderResponse(data,"Import successful", HttpStatus.OK);
    }
}
