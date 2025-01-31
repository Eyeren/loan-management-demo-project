package com.customerloan.service.loanpayment;

import org.springframework.stereotype.Service;
import com.customerloan.controller.request.RequestLoanPayment;
import com.customerloan.controller.response.ResponsePaymentStatus;
import com.customerloan.service.loanpayment.intf.LoanPaymentService;

@Service
public class LoanPaymentServiceImpl implements LoanPaymentService {
	private static String SUCCESS_MESSAGE = "Payment accomplished successfully.";
	private static String FAILURE_MESSAGE = "An error occured during the payment process.";
	
	public ResponsePaymentStatus operatePaymentProcess(RequestLoanPayment request) {
		//To be implemented 
		return null;
	}
	
}