package com.cargo.dao;

import org.springframework.stereotype.Repository;

import com.cargo.model.CarBody;

@Repository
public class CarBodyDao extends BaseDao<CarBody> implements ICarBodyDao {

	public CarBodyDao(){
		super();
		setClazz(CarBody.class);
	}
}
