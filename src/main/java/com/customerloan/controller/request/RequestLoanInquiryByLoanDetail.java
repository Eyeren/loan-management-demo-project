package com.customerloan.controller.request;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class RequestLoanInquiryByLoanDetail {
	@NotNull(message = "id (loan_id) cannot be null.")
	private Long id;
    private BigDecimal loanAmount;
    private Integer numberOfInstallment;
}