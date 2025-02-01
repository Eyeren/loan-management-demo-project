package com.customerloan.service.loanpayment.intf;

import com.customerloan.controller.request.RequestLoanPayment;
import com.customerloan.controller.response.ResponsePaymentStatus;

public interface LoanPaymentService {
	public ResponsePaymentStatus operatePaymentProcess(RequestLoanPayment request);
	
}