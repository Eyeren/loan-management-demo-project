package com.customerloan.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.customerloan.dao.entity.Loan;

public interface LoanRepository extends JpaRepository<Loan, Long> {
	
}