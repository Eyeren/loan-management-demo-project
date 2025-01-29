package com.customerloan.test.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.customerloan.controller.AdminController;
import com.customerloan.controller.request.RequestCreateLoan;
import com.customerloan.controller.request.RequestLoanInquiryByCustomerDetail;
import com.customerloan.controller.request.RequestLoanInquiryByLoanDetail;
import com.customerloan.controller.response.ResponseCreateLoanStatus;
import com.customerloan.controller.response.ResponseInstallmentListInquiryResultByLoan;
import com.customerloan.controller.response.ResponseLoanInquiry;
import com.customerloan.service.customerloanapplication.intf.CustomerLoanApplicationService;
import com.customerloan.service.loaninquiry.intf.LoanInquiryService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@RunWith(MockitoJUnitRunner.class)
public class AdminControllerTest {
    private MockMvc mockMvc;
    @Mock
    private LoanInquiryService loanInquiryService;
    @Mock
    private CustomerLoanApplicationService customerLoanApplicationService;
    @InjectMocks
    private AdminController adminController;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(adminController).build();
    }

    @Test
    public void testCreateLoan() throws Exception {
        RequestCreateLoan request = new RequestCreateLoan();
        ResponseCreateLoanStatus response = new ResponseCreateLoanStatus();

        when(customerLoanApplicationService.createLoan(any(RequestCreateLoan.class))).thenReturn(response);

        mockMvc.perform(post("/admin/create-loan")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());

        verify(customerLoanApplicationService, times(1)).createLoan(any(RequestCreateLoan.class));
    }

    @Test
    public void testInquiryLoanByCustomer() throws Exception {
        RequestLoanInquiryByCustomerDetail request = new RequestLoanInquiryByCustomerDetail();
        ResponseLoanInquiry response = new ResponseLoanInquiry();

        when(loanInquiryService.inquiryLoanByCustomer(any(RequestLoanInquiryByCustomerDetail.class)))
                .thenReturn(response);

        mockMvc.perform(post("/admin/inquiry-loan-by-customer")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());

        verify(loanInquiryService, times(1)).inquiryLoanByCustomer(any(RequestLoanInquiryByCustomerDetail.class));
    }

    @Test
    public void testInquiryInstallmentByLoan() throws Exception {
        RequestLoanInquiryByLoanDetail request = new RequestLoanInquiryByLoanDetail();
        ResponseInstallmentListInquiryResultByLoan response = new ResponseInstallmentListInquiryResultByLoan();
        when(loanInquiryService.inquiryInstallmentByLoan(any(RequestLoanInquiryByLoanDetail.class)))
                .thenReturn(response);

        mockMvc.perform(post("/admin/inquiry-installment-by-loan")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());

        verify(loanInquiryService, times(1)).inquiryInstallmentByLoan(any(RequestLoanInquiryByLoanDetail.class));
    }
}