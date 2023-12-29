package com.example.securityapp.utils;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.example.securityapp.model.Order;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelGenerator {

    private List <Order> orderList;
    private XSSFWorkbook workbook;
    private XSSFSheet sheet;

    public ExcelGenerator(List <Order> orderList) {
        this.orderList = orderList;
        workbook = new XSSFWorkbook();
    }
    private void writeHeader() {
        sheet = workbook.createSheet("Order");
        Row row = sheet.createRow(0);
        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setFontHeight(16);
        style.setFont(font);
        createCell(row, 0, "orderId", style);
        createCell(row, 1, "nameSender", style);
        createCell(row, 2, "phoneSender", style);
        createCell(row, 3, "addressSender", style);
        createCell(row, 4, "emailSender", style);
        createCell(row, 5, "nameReceiver", style);
        createCell(row, 6, "phoneReceiver", style);
        createCell(row, 7, "addressReceiver", style);
        createCell(row, 8, "emailReceiver", style);
        createCell(row, 9, "longitude", style);
        createCell(row, 10, "latitude", style);
    }
    private void createCell(Row row, int columnCount, Object valueOfCell, CellStyle style) {
        sheet.autoSizeColumn(columnCount);
        Cell cell = row.createCell(columnCount);
        if (valueOfCell instanceof Integer) {
            cell.setCellValue((Integer) valueOfCell);
        } else if (valueOfCell instanceof Long) {
            cell.setCellValue((Long) valueOfCell);
        } else if (valueOfCell instanceof String) {
            cell.setCellValue((String) valueOfCell);
        } else {
            cell.setCellValue((Boolean) valueOfCell);
        }
        cell.setCellStyle(style);
    }
    private void write() {
        int rowCount = 1;
        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setFontHeight(14);
        style.setFont(font);
        for (Order record: orderList) {
            Row row = sheet.createRow(rowCount++);
            int columnCount = 0;
            createCell(row, columnCount++, record.getOrderId(), style);
            createCell(row, columnCount++, record.getNameSender(), style);
            createCell(row, columnCount++, record.getPhoneSender(), style);
            createCell(row, columnCount++, record.getAddressSender(), style);
            createCell(row, columnCount++, record.getEmailSender(), style);
            createCell(row, columnCount++, record.getNameReceiver(), style);
            createCell(row, columnCount++, record.getPhoneReceiver(), style);
            createCell(row, columnCount++, record.getAddressReceiver(), style);
            createCell(row, columnCount++, record.getEmailReceiver(), style);
            createCell(row, columnCount++, record.getLongitude(), style);
            createCell(row, columnCount++, record.getLatitude(), style);
        }
    }

    public void generateExcelFile(HttpServletResponse response) throws IOException {
        configResponse(response);
        writeHeader();
        write();
        ServletOutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        workbook.close();
        outputStream.close();
    }

    private void configResponse(HttpServletResponse response){
        response.setContentType("application/octet-stream");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=ListOrders-" + currentDateTime + ".xlsx";
        response.setHeader(headerKey, headerValue);
    }

}
