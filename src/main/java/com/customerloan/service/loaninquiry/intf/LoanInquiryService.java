package com.customerloan.service.loaninquiry.intf;

import com.customerloan.controller.request.RequestLoanInquiryByCustomerDetail;
import com.customerloan.controller.request.RequestLoanInquiryByLoanDetail;
import com.customerloan.controller.response.ResponseInstallmentListInquiryResultByLoan;
import com.customerloan.controller.response.ResponseLoanInquiry;

public interface LoanInquiryService {
	public ResponseLoanInquiry inquiryLoanByCustomer(RequestLoanInquiryByCustomerDetail request);
	
	public ResponseInstallmentListInquiryResultByLoan inquiryInstallmentByLoan(RequestLoanInquiryByLoanDetail request);
	
}