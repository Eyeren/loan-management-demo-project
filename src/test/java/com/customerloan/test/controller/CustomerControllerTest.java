package com.customerloan.test.controller;


import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.customerloan.controller.CustomerController;
import com.customerloan.controller.request.RequestLoanPayment;
import com.customerloan.controller.response.ResponsePaymentStatus;
import com.customerloan.service.loanpayment.intf.LoanPaymentService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@RunWith(MockitoJUnitRunner.class)
public class CustomerControllerTest {
    private MockMvc mockMvc;
    @Mock
    private LoanPaymentService loanPaymentService;
    @InjectMocks
    private CustomerController customerController;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(customerController).build();
    }

    @Test
    public void testOperatePaymentProcess() throws Exception {
        RequestLoanPayment request = new RequestLoanPayment();
        ResponsePaymentStatus response = new ResponsePaymentStatus();

        when(loanPaymentService.operatePaymentProcess(any(RequestLoanPayment.class)))
                .thenReturn(response);

        mockMvc.perform(post("/customer/operate-payment-process")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").exists());

        verify(loanPaymentService, times(1)).operatePaymentProcess(any(RequestLoanPayment.class));
    }
}