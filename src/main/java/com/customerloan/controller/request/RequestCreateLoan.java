package com.customerloan.controller.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class RequestCreateLoan {
	@NotNull
    private RequestCreateLoanCustomerDetail requestCreateLoanCustomerDetail;
}