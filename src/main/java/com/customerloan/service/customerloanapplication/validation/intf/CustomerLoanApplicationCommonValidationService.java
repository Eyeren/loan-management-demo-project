package com.customerloan.service.customerloanapplication.validation.intf;

import com.customerloan.controller.request.RequestCreateLoan;
import com.customerloan.controller.response.ResponseCreateLoanStatus;

public interface CustomerLoanApplicationCommonValidationService {
	public ResponseCreateLoanStatus validateApplication(RequestCreateLoan request);
	
}