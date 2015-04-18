package com.cargo.dao;

import org.springframework.stereotype.Repository;

import com.cargo.model.Customer;



@Repository
public class CustomerDao extends BaseDao<Customer> implements ICustomerDao {

	public CustomerDao(){
		super();
		setClazz(Customer.class);
	}
	
	
}
