package com.customerloan.dao.entity;

import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import com.customerloan.constant.Constant;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

@Entity
@Table(name = Constant.EntityName.CUSTOMER_ENTITY)
@Getter
@Setter
public class Customer implements Serializable {
	private static final long serialVersionUID = 5627217694835795163L;

	@Id
	@Column(name = Constant.CustomerEntityFieldName.ID)
	@SequenceGenerator(name = Constant.SequenceName.CUSTOMER_ENTITY_SEQUENCE, sequenceName = Constant.SequenceName.CUSTOMER_ENTITY_SEQUENCE, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = Constant.SequenceName.CUSTOMER_ENTITY_SEQUENCE)
    private Long id;
	@Column(name = Constant.CustomerEntityFieldName.NAME)
    private String name;
	@Column(name = Constant.CustomerEntityFieldName.SURNAME)
    private String surname;
    @Column(name = Constant.CustomerEntityFieldName.CREDIT_LIMIT, nullable = false)
    private BigDecimal creditLimit;

    @Column(name = Constant.CustomerEntityFieldName.USED_CREDIT_LIMIT, nullable = false)
    private BigDecimal usedCreditLimit;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<Loan> loanList;
}