package com.cargo.dao;

import org.springframework.stereotype.Repository;

import com.cargo.model.Car;

@Repository
public class CarDao extends BaseDao<Car> implements ICarDao {
	
	public CarDao(){
		super();
		setClazz(Car.class);
	}
	
}
