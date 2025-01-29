package com.customerloan.util;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.customerloan.dao.entity.Customer;
import com.customerloan.dao.entity.Loan;
import com.customerloan.dao.entity.LoanInstallment;
import com.customerloan.dto.CustomerDTO;
import com.customerloan.dto.LoanDTO;
import com.customerloan.dto.LoanInstallmentDTO;

@Mapper
public interface CoreMapper {
	CoreMapper INSTANCE = Mappers.getMapper(CoreMapper.class);
	
	
	CustomerDTO toCustomerDto(Customer customer);	
	List<CustomerDTO> toCustomerDtoList(List<Customer> customerList);	
	Customer toCustomer(CustomerDTO customerDTO);	
	List<Customer> toCustomerList(List<CustomerDTO> customerDTOList);
	
	LoanDTO toLoanDTO(Loan loan);	
	List<LoanDTO> toLoanDTOList(List<Loan> loanList);	
	Loan toLoan(LoanDTO loanDTO);	
	List<Loan> toLoanList(List<LoanDTO> loanDTOList);
	
	LoanInstallmentDTO toLoanInstallmentDTO(LoanInstallment loanInstallment);	
	List<LoanInstallmentDTO> tLoanInstallmentDTOList(List<LoanInstallment> loanInstallmentList);	
	LoanInstallment toLoanInstallment(LoanInstallmentDTO loanInstallmentDTO);	
	List<LoanInstallment> toLoanInstallmentList(List<LoanInstallmentDTO> loanInstallmentDTOList);
}