package com.customerloan.service.customerloanapplication.validation.handler.intf;

import com.customerloan.controller.request.RequestCreateLoan;
import com.customerloan.controller.response.ResponseCreateLoanStatus;

public interface CustomerLoanApplicationValidationHandler {
	public void setNextStep(CustomerLoanApplicationValidationHandler validationHandler);
	
	public ResponseCreateLoanStatus handle(RequestCreateLoan request);
	
}