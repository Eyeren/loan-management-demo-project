package com.customerloan.service.customerloanapplication.validation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.customerloan.controller.request.RequestCreateLoan;
import com.customerloan.controller.response.ResponseCreateLoanStatus;

@Service
public class CustomerLoanApplicationCommonValidationServiceImpl {
	@Autowired
	public ApplicationRequestValidationServiceImpl applicationRequestValidationServiceImpl;
	@Autowired
	public ApplicationCustomerRecordValidationServiceImpl applicationCustomerRecordValidationServiceImpl;
	@Autowired
	public ApplicationCustomerCreditLimitValidationServiceImpl applicationCustomerCreditLimitValidationServiceImpl;
	
	
	public ResponseCreateLoanStatus validateApplication(RequestCreateLoan request) {
		applicationRequestValidationServiceImpl.setNextStep(applicationCustomerRecordValidationServiceImpl);
		applicationCustomerRecordValidationServiceImpl.setNextStep(applicationCustomerCreditLimitValidationServiceImpl);
		ResponseCreateLoanStatus response = applicationRequestValidationServiceImpl.handle(request);
		return response;
	}
	
}