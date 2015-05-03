package com.cargo.dao;

import java.util.List;

import com.cargo.model.Account;
import com.cargo.model.Order;

public interface IAccountDao extends IBaseDao<Account>{

	Account findByName(String string);

	List<Order> getOrders(Long id);
	
}
