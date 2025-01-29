package com.customerloan.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.customerloan.controller.request.RequestLoanPayment;
import com.customerloan.controller.response.ResponsePaymentStatus;
import com.customerloan.service.loanpayment.intf.LoanPaymentService;

@RestController
@RequestMapping(value = "/customer")
public class CustomerController {
	@Autowired
	private LoanPaymentService loanPaymentService;

    @PostMapping("/operate-payment-process")
    public ResponseEntity<ResponsePaymentStatus> operatePaymentProcess(@RequestBody RequestLoanPayment request) {
    	ResponsePaymentStatus response = loanPaymentService.operatePaymentProcess(request);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}