package com.cargo.dao;

import java.util.List;

import com.cargo.model.Car;
import com.cargo.model.Order;
import com.cargo.model.Selection;

public interface ICarDao extends IBaseDao<Car>{
	List<Car> findBySelection(Selection selection);

	List<Order> getOrders(Long car_id);
}
