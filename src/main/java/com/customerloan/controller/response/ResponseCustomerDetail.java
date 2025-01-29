package com.customerloan.controller.response;

import java.math.BigDecimal;
import java.util.List;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class ResponseCustomerDetail {
	private String name;
    private String surname;
    private BigDecimal creditLimit;
    private BigDecimal usedCreditLimit;
    private List<ResponseLoanDetail> loanList;
}