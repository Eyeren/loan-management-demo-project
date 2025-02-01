package com.customerloan.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.customerloan.controller.request.RequestCreateLoan;
import com.customerloan.controller.request.RequestLoanInquiryByCustomerDetail;
import com.customerloan.controller.request.RequestLoanInquiryByLoanDetail;
import com.customerloan.controller.response.ResponseCreateLoanStatus;
import com.customerloan.controller.response.ResponseInstallmentListInquiryResultByLoan;
import com.customerloan.controller.response.ResponseLoanInquiry;
import com.customerloan.service.customerloanapplication.intf.CustomerLoanApplicationService;
import com.customerloan.service.loaninquiry.intf.LoanInquiryService;

@RestController
@RequestMapping(value = "/admin")
public class AdminController {
	@Autowired
	private LoanInquiryService loanInquiryService;
	@Autowired
	private CustomerLoanApplicationService customerLoanApplicationService;

    @PostMapping("/create-loan")
    public ResponseEntity<ResponseCreateLoanStatus> createLoan(@RequestBody RequestCreateLoan request) {
    	ResponseCreateLoanStatus response = customerLoanApplicationService.createLoan(request);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
    
    @PostMapping("/inquiry-loan-by-customer")
    public ResponseEntity<ResponseLoanInquiry> inquiryLoanByCustomer(@RequestBody RequestLoanInquiryByCustomerDetail request) {
    	ResponseLoanInquiry response = loanInquiryService.inquiryLoanByCustomer(request);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
    
    @PostMapping("/inquiry-installment-by-loan")
    public ResponseEntity<ResponseInstallmentListInquiryResultByLoan> inquiryInstallmentByLoan(@RequestBody RequestLoanInquiryByLoanDetail request) {
    	ResponseInstallmentListInquiryResultByLoan response = loanInquiryService.inquiryInstallmentByLoan(request);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}