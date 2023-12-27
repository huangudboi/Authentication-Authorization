package com.example.securityapp.controller;

import com.example.securityapp.Dto.response.PokemonResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.example.securityapp.model.Order;
import com.example.securityapp.service.OrderService;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@WebMvcTest(controllers = OrderController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class OrderControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OrderService orderService;

    @Autowired
    private ObjectMapper objectMapper;

    private Order order;
    private Order order2;

    @BeforeEach
    public void init() {
        order = Order.builder().nameSender("Pham Van Huan").phoneSender("0981729501").addressSender("Hanoi").emailSender("dsadsadsadsad@gmail.com")
                .nameReceiver("Nguyen Van A").phoneReceiver("0345353578").addressReceiver("Hcm").emailReceiver("ttttttt@gmail.com")
                .latitude(33).latitude(45)
                .build();
        order2 = Order.builder().nameSender("Pham Van Huan").phoneSender("0981729501").addressSender("Hanoi").emailSender("dsadsadsadsad@gmail.com")
                .nameReceiver("Nguyen Van A").phoneReceiver("0345353578").addressReceiver("Hcm").emailReceiver("ttttttt@gmail.com")
                .latitude(33).latitude(45)
                .build();
    }

    @Test
    public void OrderController_CreateOrder_ReturnCreated() throws Exception {
        given(orderService.save(ArgumentMatchers.any())).willAnswer((invocation -> invocation.getArgument(0)));

        ResultActions response = mockMvc.perform(post("/api/v1/order/createOrder")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(order)));

        response.andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.nameSender", CoreMatchers.is(order.getNameSender())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.phoneSender", CoreMatchers.is(order.getPhoneSender())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.addressSender", CoreMatchers.is(order.getAddressSender())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.emailSender", CoreMatchers.is(order.getEmailSender())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.nameReceiver", CoreMatchers.is(order.getNameReceiver())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.phoneReceiver", CoreMatchers.is(order.getPhoneReceiver())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.addressReceiver", CoreMatchers.is(order.getAddressReceiver())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.emailReceiver", CoreMatchers.is(order.getEmailReceiver())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.latitude", CoreMatchers.is(order.getLatitude())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.latitude", CoreMatchers.is(order.getLatitude())));
    }

    @Test
    public void OrderController_GetAllOrder_ReturnListOrder() throws Exception {
        List<Order> orderList = new ArrayList<Order>();

        orderList.add(order);
        orderList.add(order2);

        when(orderService.findAll()).thenReturn(orderList);

        ResultActions response = mockMvc.perform(get("/api/v1/order/getAllOrder")
                .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()", CoreMatchers.is(orderList.size())));
    }


    @Test
    public void OrderController_DeleteOrder_ReturnString() throws Exception {
        long orderId = 1L;
        doNothing().when(orderService).deleteOrderById(1);

        ResultActions response = mockMvc.perform(delete("/api/v1/order/deleteOrder/1")
                .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(MockMvcResultMatchers.status().isOk());
    }

}
