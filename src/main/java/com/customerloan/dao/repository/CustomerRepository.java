package com.customerloan.dao.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.customerloan.dao.entity.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
	List<Customer> findByNameAndSurname(String name, String surname);
	
}