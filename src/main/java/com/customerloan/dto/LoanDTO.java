package com.customerloan.dto;

import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Getter
@Setter
public class LoanDTO {
	@JsonIgnore
	private Long id;
    private BigDecimal loanAmount;
    private Integer numberOfInstallment;
    private Date createDate;
    private boolean isPaid;
    private List<LoanInstallmentDTO> installmentList;
}