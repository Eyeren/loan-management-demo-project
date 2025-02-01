package com.customerloan.controller.request;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class RequestCreateLoanDetail {
	@NotNull(message = "loanAmount cannot be null.")
	private BigDecimal loanAmount;
	@NotNull(message = "numberOfInstallment cannot be null.")
    private Integer numberOfInstallment;
}