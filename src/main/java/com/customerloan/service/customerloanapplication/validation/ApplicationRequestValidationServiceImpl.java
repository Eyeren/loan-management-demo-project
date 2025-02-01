package com.customerloan.service.customerloanapplication.validation;

import org.springframework.stereotype.Service;

import com.customerloan.controller.request.RequestCreateLoan;
import com.customerloan.controller.response.ResponseCreateLoanStatus;
import com.customerloan.service.customerloanapplication.validation.handler.CustomerLoanApplicationValidationHandlerImpl;

@Service
public class ApplicationRequestValidationServiceImpl extends CustomerLoanApplicationValidationHandlerImpl {
	private static final String REQUESTED_CUSTOMER_INFO_NOT_VALIDATED = "Request is invalid. Requested Customer Information must be provided.";
	private static final String REQUESTED_LOAN_AMOUNT_INFO_NOT_VALIDATED = "Request is invalid. Requested Loan Information must be provided.";
	
	public ResponseCreateLoanStatus validate(RequestCreateLoan request) {
		ResponseCreateLoanStatus response = new ResponseCreateLoanStatus();
		if (!isRequestedCustomerInformationValid(request)) {
			response.setLoanApplicationStatusDescription(REQUESTED_CUSTOMER_INFO_NOT_VALIDATED); 
		} else if (!isRequestedLoanInformationValid(request)) {
			response.setLoanApplicationStatusDescription(REQUESTED_LOAN_AMOUNT_INFO_NOT_VALIDATED); 
		}
		
		return response;
	}
	
	private boolean isRequestedCustomerInformationValid(RequestCreateLoan request) {
		return request != null && request.getRequestCreateLoanCustomerDetail() != null;
	}
	
	private boolean isRequestedLoanInformationValid(RequestCreateLoan request) {
		return request.getRequestCreateLoanCustomerDetail().getRequestedLoanDetail() != null;
	}
}