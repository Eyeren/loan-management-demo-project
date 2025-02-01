package com.customerloan.dao.entity;

import lombok.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import com.customerloan.constant.Constant;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

@Entity
@Table(name = Constant.EntityName.LOAN_ENTITY)
@Getter
@Setter
public class Loan implements Serializable {
	private static final long serialVersionUID = 5627217694835795163L;

	@Id
	@Column(name = Constant.CustomerEntityFieldName.ID)
	@SequenceGenerator(name = Constant.SequenceName.LOAN_ENTITY_SEQUENCE, sequenceName = Constant.SequenceName.LOAN_ENTITY_SEQUENCE, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = Constant.SequenceName.LOAN_ENTITY_SEQUENCE)
    private Long id;
	@ManyToOne
	@JsonBackReference
    @JoinColumn(name = Constant.LoanEntityFieldName.CUSTOMER_ID, nullable = false)
    private Customer customer;

    @Column(name = Constant.LoanEntityFieldName.LOAN_AMOUNT, nullable = false)
    private BigDecimal loanAmount;

    @Column(name = Constant.LoanEntityFieldName.NUMBER_OF_INSTALLMENT, nullable = false)
    private Integer numberOfInstallment;

    @Column(name = Constant.LoanEntityFieldName.CREATE_DATE, nullable = false)
    private Date createDate;
    @Column(name = Constant.LoanEntityFieldName.IS_PAID)
    private boolean isPaid = false;

    @OneToMany(mappedBy = "loan", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<LoanInstallment> installmentList;
    
    
    @PrePersist
    protected void persistCreateDate() {
    	this.createDate = new Date();
    }
}