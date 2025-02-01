package com.customerloan.controller.response;

import com.customerloan.dto.CustomerDTO;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class ResponseCreateLoanStatus {
    private String loanApplicationStatusDescription;
    private boolean loanApplicationCompletedSuccessful;
	@JsonIgnore
    private CustomerDTO customerRecordFoundByInquiry;
}