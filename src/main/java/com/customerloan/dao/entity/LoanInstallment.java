package com.customerloan.dao.entity;

import lombok.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import com.customerloan.constant.Constant;
import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

@Entity
@Table(name = Constant.EntityName.CUSTOMER_ENTITY)
@Getter
@Setter
public class LoanInstallment implements Serializable {
	private static final long serialVersionUID = 5627217694835795163L;

	@Id
	@Column(name = Constant.CustomerEntityFieldName.ID)
	@SequenceGenerator(name = Constant.SequenceName.LOAN_INSTALLMENT_ENTITY_SEQUENCE, sequenceName = Constant.SequenceName.LOAN_INSTALLMENT_ENTITY_SEQUENCE, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = Constant.SequenceName.LOAN_INSTALLMENT_ENTITY_SEQUENCE)
    private Long id;
	@ManyToOne
	@JsonBackReference
    @JoinColumn(name = Constant.LoanInstallmentEntityFieldName.LOAN_ID, nullable = false)
    private Loan loan;

    @Column(name = Constant.LoanInstallmentEntityFieldName.AMOUNT, nullable = false)
    private BigDecimal amount;
    @Column(name = Constant.LoanInstallmentEntityFieldName.PAID_AMOUNT)
    private BigDecimal paidAmount;

    @Column(name = Constant.LoanInstallmentEntityFieldName.DUE_DATE, nullable = false)
    private Date dueDate;
    @Column(name = Constant.LoanInstallmentEntityFieldName.PAYMENT_DATE)
    private Date paymentDate;
    @Column(name = Constant.LoanInstallmentEntityFieldName.IS_PAID)
    private boolean isPaid = false;
}