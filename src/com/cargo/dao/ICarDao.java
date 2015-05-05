package com.cargo.dao;

import java.util.List;

import com.cargo.model.Car;
import com.cargo.model.Order;

public interface ICarDao extends IBaseDao<Car>{
	List<Car> findByArgs(String keyword, String type, String brand, String model, int loprice,int hiprice);

	List<Order> getOrders(Long car_id);
}
