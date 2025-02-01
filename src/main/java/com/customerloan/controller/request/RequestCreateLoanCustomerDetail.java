package com.customerloan.controller.request;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class RequestCreateLoanCustomerDetail {
	@NotNull(message = "existingCustomerId cannot be null.")
    private Long existingCustomerId;
    private RequestCreateLoanDetail requestedLoanDetail;
    @NotNull(message = "interestRate cannot be null.")
    private BigDecimal interestRate;
}