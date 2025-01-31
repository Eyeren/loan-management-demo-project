package com.customerloan.service.customerloanapplication.validation;

import java.math.BigDecimal;
import com.customerloan.controller.request.RequestCreateLoan;
import com.customerloan.controller.response.ResponseCreateLoanStatus;
import com.customerloan.dto.CustomerDTO;
import com.customerloan.service.customerloanapplication.validation.handler.CustomerLoanApplicationValidationHandlerImpl;

public class ApplicationCustomerCreditLimitValidationServiceImpl extends CustomerLoanApplicationValidationHandlerImpl {
	private static final String CUSTOMER_CREDIT_LIMIT_NOT_SUFFICIENT = "Customer Credit Limit not sufficient.";
	private static final String REQUESTED_CREDIT_LIMIT_EXCESS = "Requested Loan Amount must be less than the permitted customer limit";
	
	public ResponseCreateLoanStatus validate(RequestCreateLoan request) {
		ResponseCreateLoanStatus response = new ResponseCreateLoanStatus();
		CustomerDTO customerDTO = response.getCustomerRecordFoundByInquiry();
		if (!isCustomerCreditLimitExists(customerDTO.getCreditLimit())) {
			response.setLoanApplicationStatusDescription(CUSTOMER_CREDIT_LIMIT_NOT_SUFFICIENT); 
			return response;
		}
		
		if (!isCustomerCreditUsePermittedByLimitation(request.getRequestCreateLoanCustomerDetail().getRequestedLoanDetail().getLoanAmount(), getCalculatedOpenCreditLimit(customerDTO))) {
			response.setLoanApplicationStatusDescription(REQUESTED_CREDIT_LIMIT_EXCESS); 
			return response;
			
		}
		response.setLoanApplicationCompletedSuccessful(Boolean.TRUE); 
		return response;
	}
	
	private boolean isCustomerCreditLimitExists(BigDecimal customerCreditLimit) {
		return customerCreditLimit != null && customerCreditLimit.compareTo(BigDecimal.ZERO) > 0;
	}
	
	private boolean isCustomerCreditUsePermittedByLimitation(BigDecimal requestedLoanAmount, BigDecimal permittedOpenCreditLimit) {
		return requestedLoanAmount.compareTo(permittedOpenCreditLimit) < 0;
	}
	
	private BigDecimal getCalculatedOpenCreditLimit(CustomerDTO customerDTO) {
		BigDecimal usedCreditLimit = customerDTO.getUsedCreditLimit() != null ? customerDTO.getUsedCreditLimit() : BigDecimal.ZERO;
		return customerDTO.getCreditLimit().subtract(usedCreditLimit).max(BigDecimal.ZERO); 
	}
}