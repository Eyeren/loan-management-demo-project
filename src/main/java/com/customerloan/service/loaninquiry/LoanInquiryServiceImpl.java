package com.customerloan.service.loaninquiry;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import com.customerloan.controller.request.RequestLoanInquiryByCustomerDetail;
import com.customerloan.controller.request.RequestLoanInquiryByLoanDetail;
import com.customerloan.controller.response.ResponseCustomerDetail;
import com.customerloan.controller.response.ResponseInstallmentListInquiryResultByLoan;
import com.customerloan.controller.response.ResponseLoanDetail;
import com.customerloan.controller.response.ResponseLoanInquiry;
import com.customerloan.controller.response.ResponseLoanInstallmentDetail;
import com.customerloan.dao.entity.Customer;
import com.customerloan.dao.entity.Loan;
import com.customerloan.dao.repository.CustomerRepository;
import com.customerloan.dao.repository.LoanRepository;
import com.customerloan.dto.CustomerDTO;
import com.customerloan.dto.LoanDTO;
import com.customerloan.service.loaninquiry.intf.LoanInquiryService;
import com.customerloan.util.CoreMapper;

@Service
public class LoanInquiryServiceImpl implements LoanInquiryService {
	@Autowired
	public CustomerRepository customerRepository;
	@Autowired
	public LoanRepository loanRepository;
	public CoreMapper coreMapper;
	
	@Transactional
	public ResponseLoanInquiry inquiryLoanByCustomer(RequestLoanInquiryByCustomerDetail request) {
		ResponseLoanInquiry response = new ResponseLoanInquiry();
		CustomerDTO customerInquiryResult = null;
		Optional<Customer> customerLoanInquiryResult = customerRepository.findById(request.getExistingCustomerId());
		if (customerLoanInquiryResult.isPresent()) {
			Customer customerLoanDetail = customerLoanInquiryResult.get();
			customerInquiryResult = coreMapper.toCustomerDto(customerLoanDetail);
		} else {
			List<Customer> customerInquiryResultList = customerRepository.findByNameAndSurname(request.getName(), request.getSurname());
			if (!CollectionUtils.isEmpty(customerInquiryResultList)) {
				Customer inquiredCustomerDetail = customerInquiryResultList.stream().findFirst().orElse(null); 
				customerInquiryResult = coreMapper.toCustomerDto(inquiredCustomerDetail);
			}
		}
		
		if (customerInquiryResult != null) {
			ResponseCustomerDetail responseCustomerDetail = new ResponseCustomerDetail();
			responseCustomerDetail.setCreditLimit(customerInquiryResult.getCreditLimit());
			responseCustomerDetail.setName(customerInquiryResult.getName());
			responseCustomerDetail.setSurname(customerInquiryResult.getSurname());
			responseCustomerDetail.setUsedCreditLimit(customerInquiryResult.getUsedCreditLimit());
			if(!CollectionUtils.isEmpty(customerInquiryResult.getLoanList())) {
				List<ResponseLoanDetail> loanList = new ArrayList<>();
				customerInquiryResult.getLoanList().forEach(loanDetail -> {
					ResponseLoanDetail responseLoanDetail = new ResponseLoanDetail();
					responseLoanDetail.setCreateDate(loanDetail.getCreateDate());
					responseLoanDetail.setLoanAmount(loanDetail.getLoanAmount());
					responseLoanDetail.setNumberOfInstallment(loanDetail.getNumberOfInstallment());
					responseLoanDetail.setPaid(loanDetail.isPaid());
					loanList.add(responseLoanDetail);
				});
				responseCustomerDetail.setLoanList(loanList); 
			}
			response.setResponse(responseCustomerDetail);
		}
		
		return response;
	}
	
	public ResponseInstallmentListInquiryResultByLoan inquiryInstallmentByLoan(RequestLoanInquiryByLoanDetail request) {
		ResponseInstallmentListInquiryResultByLoan response = new ResponseInstallmentListInquiryResultByLoan();
		LoanDTO loanResultDTO = null;
		Optional<Loan> loanInquiryResult = loanRepository.findById(request.getId());
		if (loanInquiryResult.isPresent()) {
			Loan loanDetail = loanInquiryResult.get();
			loanResultDTO = coreMapper.toLoanDTO(loanDetail);
		}
		if (loanResultDTO != null && !CollectionUtils.isEmpty(loanResultDTO.getInstallmentList())) {
			List<ResponseLoanInstallmentDetail> installmentList = new ArrayList<>();
			loanResultDTO.getInstallmentList().forEach(installment -> {
				ResponseLoanInstallmentDetail responseLoanInstallmentDetail = new ResponseLoanInstallmentDetail();
				responseLoanInstallmentDetail.setAmount(installment.getAmount());
				responseLoanInstallmentDetail.setDueDate(installment.getDueDate());
				responseLoanInstallmentDetail.setPaid(installment.isPaid());
				responseLoanInstallmentDetail.setPaidAmount(installment.getPaidAmount());
				responseLoanInstallmentDetail.setPaymentDate(installment.getPaymentDate());
				
				installmentList.add(responseLoanInstallmentDetail);
			});
			response.setInstallmentList(installmentList);  
		}
		
		
		return response;
	}
	
}