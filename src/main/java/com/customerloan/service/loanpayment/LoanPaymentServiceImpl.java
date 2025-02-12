package com.customerloan.service.loanpayment;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.customerloan.constant.Constant;
import com.customerloan.controller.request.RequestLoanPayment;
import com.customerloan.controller.response.ResponsePaymentStatus;
import com.customerloan.dao.entity.Loan;
import com.customerloan.dao.entity.LoanInstallment;
import com.customerloan.dao.repository.LoanInstallmentRepository;
import com.customerloan.dao.repository.LoanRepository;
import com.customerloan.dto.LoanDTO;
import com.customerloan.dto.LoanInstallmentDTO;
import com.customerloan.service.loanpayment.intf.LoanPaymentService;
import com.customerloan.util.CoreMapper;

@Service
public class LoanPaymentServiceImpl implements LoanPaymentService {
	@Autowired
	public LoanRepository loanRepository;
	@Autowired
	public LoanInstallmentRepository loanInstallmentRepository;
	public CoreMapper coreMapper;
	private final String SUCCESS_MESSAGE = "Payment accomplished successfully.";
	private final String UNSUCCESSFUL_PAYMENT_MESSAGE = "Payment attempt failed due to that conditions not provided.";
	private final String INSUFFICIENT_PAYMENT_AMOUNT_MESSAGE = "Payment amount is insufficient to pay any installment.";
	
	@Transactional
	public ResponsePaymentStatus operatePaymentProcess(RequestLoanPayment request) { 
		ResponsePaymentStatus response = new ResponsePaymentStatus(); 
		LoanDTO loanDTO = findRecordedLoanInformationIfExists(request.getLoanId());
		if (loanDTO != null) {
			if (isLoanInstallmentListOpenAndAvailableToBePaid(loanDTO, request.getPaymentAmount()))  {
				List<LoanInstallmentDTO> unpaidInstallmentList = getFilteredUnpaidInstallmentList(loanDTO); 
				response = executeAndGetPaymentResponseIfAllConditionsAreMet(unpaidInstallmentList, request);
				if (response != null) {
					return response;
				}
			}
		}
		response.setPaymentStatusDescription(UNSUCCESSFUL_PAYMENT_MESSAGE);  
		return response;
	}
	
	private ResponsePaymentStatus executeAndGetPaymentResponseIfAllConditionsAreMet(List<LoanInstallmentDTO> unpaidInstallmentList, RequestLoanPayment request) {
		ResponsePaymentStatus response = new ResponsePaymentStatus();
		if (isInstallmentListNotEmpty(unpaidInstallmentList)) {
			BigDecimal minimumInstallmentAmountToBePaid = getMinimumInstallmentAmountToBePaid(unpaidInstallmentList);
			BigDecimal requestedPaymentAmount = request.getPaymentAmount();
			if (isRequestedPaymentAmountSufficientForPayment(requestedPaymentAmount, minimumInstallmentAmountToBePaid)) {
				Integer maximumPaymentCountAllowed = getMaximumPaymentCountAllowed(requestedPaymentAmount, minimumInstallmentAmountToBePaid);
				sortUnpaidInstallmentListByDueDateAscending(unpaidInstallmentList);						
				updatePaymentInformationByAllowedPaymentRequest(unpaidInstallmentList, maximumPaymentCountAllowed, minimumInstallmentAmountToBePaid);
				savePaymentTransaction(unpaidInstallmentList);
				
				response.setPaymentStatusDescription(SUCCESS_MESSAGE);  
				response.setPaymentCompleted(Boolean.TRUE); 						
				response.setPaidInstallmentCount(maximumPaymentCountAllowed); 
				response.setTotalAmountSpent(getCalculatedTotalAmountSpent(minimumInstallmentAmountToBePaid, maximumPaymentCountAllowed));  
				response.setAllInstallmentsPaidCompletely(isAllInstallmentsPaidCompletely(unpaidInstallmentList, maximumPaymentCountAllowed));  
				return response;
				
			} else {
				response.setPaymentStatusDescription(INSUFFICIENT_PAYMENT_AMOUNT_MESSAGE);  
				return response;
			}
		}
		return null;  
	}
	
	private LoanDTO findRecordedLoanInformationIfExists (Long loanId) {
		Optional<Loan> loanInquiryResult = loanRepository.findById(loanId);
		if (loanInquiryResult.isPresent()) {
			Loan loanDetail = loanInquiryResult.get();
			return coreMapper.toLoanDTO(loanDetail);
		}
		return null;	
	}
	
	private boolean isLoanInstallmentListOpenAndAvailableToBePaid(LoanDTO loanDTO, BigDecimal paymentAmount) {
		return loanDTO != null && !CollectionUtils.isEmpty(loanDTO.getInstallmentList()) && paymentAmount.compareTo(BigDecimal.ZERO) > 0;
	}
	
	private List<LoanInstallmentDTO> getFilteredUnpaidInstallmentList(LoanDTO loanDTO) {
		return loanDTO.getInstallmentList().stream().filter(installment -> !installment.isPaid()).collect(Collectors.toList());
	}
	
	private boolean isInstallmentListNotEmpty(List<LoanInstallmentDTO> unpaidInstallmentList) {
		return !CollectionUtils.isEmpty(unpaidInstallmentList);
	}
	
	private BigDecimal getMinimumInstallmentAmountToBePaid(List<LoanInstallmentDTO> unpaidInstallmentList) {
		return unpaidInstallmentList.stream().map(LoanInstallmentDTO::getAmount).findFirst().get();
	}
	
	private boolean isRequestedPaymentAmountSufficientForPayment(BigDecimal requestedPaymentAmount, BigDecimal minimumInstallmentAmountToBePaid) {
		return minimumInstallmentAmountToBePaid != null && requestedPaymentAmount.compareTo(minimumInstallmentAmountToBePaid) > 0;
	}
	
	private Integer getMaximumPaymentCountAllowed(BigDecimal requestedPaymentAmount, BigDecimal minimumInstallmentAmountToBePaid) {
		Integer paymentCount = requestedPaymentAmount.divide(minimumInstallmentAmountToBePaid, RoundingMode.DOWN).intValue(); 
		return paymentCount.compareTo(Constant.INT_THREE) > 0 ? Constant.INT_THREE : paymentCount;
	}
	
	private void sortUnpaidInstallmentListByDueDateAscending(List<LoanInstallmentDTO> unpaidInstallmentList) {
		unpaidInstallmentList.stream().sorted(Comparator.comparing(LoanInstallmentDTO::getDueDate));
	}
	
	private void updatePaymentInformationByAllowedPaymentRequest(List<LoanInstallmentDTO> unpaidInstallmentList, Integer maximumPaymentCountAllowed, BigDecimal minimumInstallmentAmountToBePaid) {
		unpaidInstallmentList.stream().limit(maximumPaymentCountAllowed).forEach(installment -> {
			installment.setPaidAmount(minimumInstallmentAmountToBePaid);
			installment.setPaid(Boolean.TRUE); 
			installment.setPaymentDate(new Date());
		}); 
	}
	
	private void savePaymentTransaction(List<LoanInstallmentDTO> unpaidInstallmentList) {
		List<LoanInstallment> loanInstallmentList = coreMapper.toLoanInstallmentList(unpaidInstallmentList);
		loanInstallmentRepository.saveAllAndFlush(loanInstallmentList);
	}
	
	private BigDecimal getCalculatedTotalAmountSpent(BigDecimal minimumInstallmentAmountToBePaid, Integer maximumPaymentCountAllowed) {
		return minimumInstallmentAmountToBePaid.multiply(BigDecimal.valueOf(maximumPaymentCountAllowed));
	}
	
	private boolean isAllInstallmentsPaidCompletely(List<LoanInstallmentDTO> unpaidInstallmentList, Integer maximumPaymentCountAllowed) {
		Long totalUnpaidInstallmentCount = unpaidInstallmentList.stream().count();
		return maximumPaymentCountAllowed.equals(totalUnpaidInstallmentCount.intValue());  
	}
	
}