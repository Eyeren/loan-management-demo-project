package com.customerloan.service.customerloanapplication.validation.handler;

import com.customerloan.controller.request.RequestCreateLoan;
import com.customerloan.controller.response.ResponseCreateLoanStatus;
import com.customerloan.service.customerloanapplication.validation.handler.intf.CustomerLoanApplicationValidationHandler;

public abstract class CustomerLoanApplicationValidationHandlerImpl implements CustomerLoanApplicationValidationHandler {
	public CustomerLoanApplicationValidationHandler validationHandler;	
	
	@Override
	public void setNextStep(CustomerLoanApplicationValidationHandler validationHandler) {
		this.validationHandler = validationHandler;
	};
	
	public ResponseCreateLoanStatus handle(RequestCreateLoan request) {
		ResponseCreateLoanStatus responseValidation = validate(request);
		if (isValidationChainCompletedAndFailed(responseValidation)) {
			return responseValidation;
		}
		if (validationHandler != null) {
			return validationHandler.handle(request);
		}
		return responseValidation;
	}
	
	private boolean isValidationChainCompletedAndFailed(ResponseCreateLoanStatus responseValidation) {
		return !responseValidation.isLoanApplicationCompletedSuccessful() && responseValidation.getLoanApplicationStatusDescription() != null;
	}
	
	public abstract ResponseCreateLoanStatus validate(RequestCreateLoan request);
	
}