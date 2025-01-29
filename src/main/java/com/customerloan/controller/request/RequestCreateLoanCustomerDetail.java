package com.customerloan.controller.request;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class RequestCreateLoanCustomerDetail {
	@NotNull(message = "name cannot be null.")
	private String name;
	@NotNull(message = "surname cannot be null.")
    private String surname;
    private RequestCreateLoanDetail requestedLoanDetail;
    @NotNull(message = "interestRate cannot be null.")
    private BigDecimal interestRate;
}