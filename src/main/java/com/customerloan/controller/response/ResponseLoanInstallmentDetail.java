package com.customerloan.controller.response;

import java.math.BigDecimal;
import java.util.Date;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class ResponseLoanInstallmentDetail {
	private BigDecimal amount;
    private BigDecimal paidAmount;
    private Date dueDate;
    private Date paymentDate;
    private boolean isPaid;
}