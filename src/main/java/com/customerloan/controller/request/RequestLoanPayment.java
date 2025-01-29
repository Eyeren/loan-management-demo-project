package com.customerloan.controller.request;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class RequestLoanPayment {
	@NotNull(message = "id (loan_id) cannot be null.")
	private Long id;
	@NotNull(message = "paymentAmount cannot be null.")
    private BigDecimal paymentAmount;
}