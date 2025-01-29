package com.customerloan.dto;

import lombok.*;
import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Getter
@Setter
public class LoanInstallmentDTO {
	@JsonIgnore
	private Long id;
    private BigDecimal amount;
    private BigDecimal paidAmount;
    private Date dueDate;
    private Date paymentDate;
    private boolean isPaid;
}