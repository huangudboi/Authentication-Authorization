package com.example.securityapp.service.impl;

import com.example.securityapp.Dto.OrderExcelDTO;
import com.example.securityapp.validator.OrderExcelImportValidator;
import com.example.securityapp.z_common.abs.ExcelImportValidator;
import com.example.securityapp.exceptions.OrderNotFoundException;
import com.example.securityapp.model.Order;
import com.example.securityapp.repository.OrderRepository;
import com.example.securityapp.service.OrderService;
import com.poiji.bind.Poiji;
import com.poiji.exception.PoijiExcelType;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

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
    public List<String> importAndValidateExcel(MultipartFile multipartFile){
        List<String> validationErrors = new ArrayList<>();

        try {
            List<OrderExcelDTO> data = Poiji.fromExcel(multipartFile.getInputStream(),
                    PoijiExcelType.XLSX, OrderExcelDTO.class);

            // Process the imported data
            for (OrderExcelDTO item : data) {
                System.out.println(item);
            }
        } catch (Exception e) {
            // Handle the exception
            e.printStackTrace();
        }

        try (InputStream inputStream = multipartFile.getInputStream()) {
            Workbook workbook = new XSSFWorkbook(inputStream);
            Sheet sheet = workbook.getSheetAt(0); // Assuming data is in the first sheet

            // Perform validation - Example: Check if column headers exist
            Row headerRow = sheet.getRow(0);
            if (headerRow == null) {
                validationErrors.add("Excel file is empty");
            } else {
                Iterator<Cell> cellIterator = headerRow.cellIterator();
                List<String> expectedHeaders = List.of("nameSender", "phoneSender", "addressSender", "emailSender",
                        "nameSender", "phoneReceiver", "addressReceiver", "emailReceiver", "longitude", "latitude");

                while (cellIterator.hasNext()) {
                    Cell cell = cellIterator.next();
                    if (!expectedHeaders.contains(cell.getStringCellValue())) {
                        validationErrors.add("Invalid column header: " + cell.getStringCellValue());
                    }
                }
            }
        }catch (IOException ex){
            ex.printStackTrace();
        }

        return validationErrors;

    }
}
