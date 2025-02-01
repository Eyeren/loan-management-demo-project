package com.customerloan.service.customerloanapplication.intf;

import com.customerloan.controller.request.RequestCreateLoan;
import com.customerloan.controller.response.ResponseCreateLoanStatus;

public interface CustomerLoanApplicationService {
	public ResponseCreateLoanStatus createLoan(RequestCreateLoan request);
	
}