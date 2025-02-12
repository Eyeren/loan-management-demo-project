package com.customerloan.service.customerloanapplication;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.customerloan.controller.request.RequestCreateLoan;
import com.customerloan.controller.response.ResponseCreateLoanStatus;
import com.customerloan.dao.entity.Customer;
import com.customerloan.dao.repository.CustomerRepository;
import com.customerloan.dto.CustomerDTO;
import com.customerloan.dto.LoanDTO;
import com.customerloan.dto.LoanInstallmentDTO;
import com.customerloan.service.customerloanapplication.intf.CustomerLoanApplicationService;
import com.customerloan.service.customerloanapplication.validation.CustomerLoanApplicationCommonValidationServiceImpl;
import com.customerloan.util.CoreMapper;
import com.customerloan.util.DateUtil;

@Service
public class CustomerLoanApplicationServiceImpl implements CustomerLoanApplicationService {
	@Autowired
	public CustomerLoanApplicationCommonValidationServiceImpl commonValidationService;
	@Autowired
	public CustomerRepository customerRepository;
	public CoreMapper coreMapper;
	
	
	@Transactional
	public ResponseCreateLoanStatus createLoan(RequestCreateLoan request) {
		ResponseCreateLoanStatus responseCreateLoanStatus = commonValidationService.validateApplication(request);
		if (responseCreateLoanStatus.isLoanApplicationCompletedSuccessful()) {
			CustomerDTO customerRecord = responseCreateLoanStatus.getCustomerRecordFoundByInquiry();
			addRequestedLimitToUsedTotalLimit(customerRecord, request.getRequestCreateLoanCustomerDetail().getRequestedLoanDetail().getLoanAmount());
			calculateAndSetUpdatedLoanList(request, customerRecord);
			saveCustomerApplication(customerRecord);
			
		}
		return responseCreateLoanStatus;

		
	}
	
	private void calculateAndSetUpdatedLoanList(RequestCreateLoan request, CustomerDTO customerRecord) {
		List<LoanDTO> loanList = !CollectionUtils.isEmpty(customerRecord.getLoanList()) ? customerRecord.getLoanList() : new ArrayList<>();
		loanList.add(getCreatedLoanDetailList(request));
		customerRecord.setLoanList(loanList); 
	}
	
	
	private LoanDTO getCreatedLoanDetailList(RequestCreateLoan request) {
		LoanDTO newRequestedLoan = new LoanDTO();
		newRequestedLoan.setPaid(Boolean.FALSE); 
		BigDecimal newCalculatedLoanAmount = getCalculatedNewLoanAmount(request);
		Integer requestedNumberOfInstallment = request.getRequestCreateLoanCustomerDetail().getRequestedLoanDetail().getNumberOfInstallment();
		newRequestedLoan.setLoanAmount(newCalculatedLoanAmount);
		newRequestedLoan.setNumberOfInstallment(requestedNumberOfInstallment);	
		newRequestedLoan.setInstallmentList(getCalculatedInstallmentDetailList(requestedNumberOfInstallment, newCalculatedLoanAmount)); 
		return newRequestedLoan;
		
	}
	
	private BigDecimal getCalculatedNewLoanAmount(RequestCreateLoan request) {
		BigDecimal requestedLoanAmount = request.getRequestCreateLoanCustomerDetail().getRequestedLoanDetail().getLoanAmount();
		BigDecimal requestedInterestRate = request.getRequestCreateLoanCustomerDetail().getInterestRate().add(BigDecimal.ONE);
		return requestedLoanAmount.multiply(requestedInterestRate);	
	}
	
	private List<LoanInstallmentDTO> getCalculatedInstallmentDetailList(Integer requestedNumberOfInstallment, BigDecimal newCalculatedLoanAmount) {
		BigDecimal calculatedInstallmentAmount = getCalculatedInstallmentAmount(requestedNumberOfInstallment, newCalculatedLoanAmount);
		LoanInstallmentDTO loanInstallmentDTO = new LoanInstallmentDTO();
		loanInstallmentDTO.setPaid(Boolean.FALSE);
		loanInstallmentDTO.setPaidAmount(BigDecimal.ZERO); 
		loanInstallmentDTO.setAmount(calculatedInstallmentAmount);
		List<LoanInstallmentDTO> installmentList = new ArrayList<>(Collections.nCopies(requestedNumberOfInstallment, loanInstallmentDTO)); 
		calculateAndSetInstallmentDueDate(installmentList);
		return installmentList;		
		
	}
	
	private BigDecimal getCalculatedInstallmentAmount(Integer requestedNumberOfInstallment, BigDecimal newCalculatedLoanAmount) {
		return newCalculatedLoanAmount.divide(BigDecimal.valueOf(requestedNumberOfInstallment.longValue()));
		
	}	
	
	private void calculateAndSetInstallmentDueDate(List<LoanInstallmentDTO> installmentList) {
		Calendar calendar = DateUtil.getFirstDayOfNextMonthAsCalendar();
        installmentList.forEach(installmentDTO -> {
			installmentDTO.setDueDate(DateUtil.getDateFromCalendar(calendar));
			calendar.add(Calendar.MONTH, 1);
		}); 
	}
	
	private void addRequestedLimitToUsedTotalLimit(CustomerDTO customerRecord, BigDecimal requestedLoanAmount) {
		BigDecimal usedLimitWithNewLoanAmount = customerRecord.getUsedCreditLimit().add(requestedLoanAmount);
		customerRecord.setUsedCreditLimit(usedLimitWithNewLoanAmount); 
	}
	
	private void saveCustomerApplication(CustomerDTO customerRecord) {
		Customer customer = coreMapper.toCustomer(customerRecord);
		customerRepository.saveAndFlush(customer);
	}
	
}