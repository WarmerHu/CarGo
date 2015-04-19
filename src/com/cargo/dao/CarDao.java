package com.cargo.dao;

import org.springframework.stereotype.Repository;

import com.cargo.model.Car;

@Repository
public class CarDao extends BaseDao<Car> implements ICarDao {
	
	public CarDao(){
		super();
		setClazz(Car.class);
	}
	
	@Override
	public void update(Car car) {
		Car old = find(car.getCarid());
		if(car.getDescription() == null) car.setDescription(old.getDescription());
		if(car.getPrice() == null) car.setPrice(old.getPrice());
		if(car.getCtype() == null) car.setCtype(old.getCtype());
		super.update(car);
	}
	
}
