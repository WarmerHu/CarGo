package com.cargo.dao;

import org.springframework.stereotype.Repository;

import com.cargo.model.CarEngine;

@Repository
public class CarEngineDao extends BaseDao<CarEngine> implements ICarEngineDao {

	public CarEngineDao(){
		super();
		setClazz(CarEngine.class);
	}
}
