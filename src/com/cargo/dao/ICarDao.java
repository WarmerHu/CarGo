package com.cargo.dao;

import java.util.List;

import net.minidev.json.JSONObject;

import com.cargo.model.Car;
import com.cargo.model.Order;

//test
public interface ICarDao extends IBaseDao<Car>{
	List<Car> findByArgs(String...args);

	List<Order> getOrders(Long car_id);
	
	public void update(Car car, JSONObject obj);
}
