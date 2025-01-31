package com.customerloan.service.customerloanapplication.validation;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import com.customerloan.controller.request.RequestCreateLoan;
import com.customerloan.controller.response.ResponseCreateLoanStatus;
import com.customerloan.dao.entity.Customer;
import com.customerloan.dao.repository.CustomerRepository;
import com.customerloan.dto.CustomerDTO;
import com.customerloan.service.customerloanapplication.validation.handler.CustomerLoanApplicationValidationHandlerImpl;
import com.customerloan.util.CoreMapper;

public class ApplicationCustomerRecordValidationServiceImpl extends CustomerLoanApplicationValidationHandlerImpl {
	private static final String CUSTOMER_RECORD_NOT_FOUND = "Customer Record not found with given customer id.";
	@Autowired
	public CoreMapper coreMapper;
	@Autowired
	public CustomerRepository customerRepository;
	
	public ResponseCreateLoanStatus validate(RequestCreateLoan request) {
		ResponseCreateLoanStatus response = new ResponseCreateLoanStatus();
		
		Optional<Customer> customerInquiryResult = customerRepository.findById(request.getRequestCreateLoanCustomerDetail().getExistingCustomerId());
		if (!isCustomerFoundByCustomerId(customerInquiryResult)) {
			response.setLoanApplicationStatusDescription(CUSTOMER_RECORD_NOT_FOUND); 
		} else {
			CustomerDTO customerDTO = coreMapper.toCustomerDto(customerInquiryResult.get());
			response.setCustomerRecordFoundByInquiry(customerDTO); 
		}
		
		return response;
	}
	
	private boolean isCustomerFoundByCustomerId(Optional<Customer> customerInquiryResult) {
		return customerInquiryResult.isPresent();
	}
}