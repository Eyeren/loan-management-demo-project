package com.customerloan.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.customerloan.dao.entity.LoanInstallment;

public interface LoanInstallmentRepository extends JpaRepository<LoanInstallment, Long> {
	
}