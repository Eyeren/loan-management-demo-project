package com.customerloan.controller.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;



@Getter
@Setter
public class RequestLoanInquiryByCustomerDetail {
	@Getter
	@NotNull(message = "customer id cannot be null.")
	private Long id;
	@NotNull(message = "name cannot be null.")
	private String name;
	@NotNull(message = "surname cannot be null.")
    private String surname;
}