package com.customerloan.service.customerloanapplication;

import org.springframework.stereotype.Service;
import com.customerloan.controller.request.RequestCreateLoan;
import com.customerloan.controller.response.ResponseCreateLoanStatus;
import com.customerloan.service.customerloanapplication.intf.CustomerLoanApplicationService;

@Service
public class CustomerLoanApplicationServiceImpl implements CustomerLoanApplicationService {
	public ResponseCreateLoanStatus createLoan(RequestCreateLoan request) {
		return null;
	}
	
}