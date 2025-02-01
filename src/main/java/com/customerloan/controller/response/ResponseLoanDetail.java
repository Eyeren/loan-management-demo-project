package com.customerloan.controller.response;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class ResponseLoanDetail {
	private BigDecimal loanAmount;
    private Integer numberOfInstallment;
    private Date createDate;
    private boolean isPaid;
    private List<ResponseLoanInstallmentDetail> installmentList;
}