package com.cargo.dao;

import org.springframework.stereotype.Repository;

import com.cargo.model.Order;



@Repository
public class OrderDao extends BaseDao<Order> implements IOrderDao {

	public OrderDao(){
		super();
		setClazz(Order.class);
	}
	
//	update
}
