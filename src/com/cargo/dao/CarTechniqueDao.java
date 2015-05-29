package com.cargo.dao;

import org.springframework.stereotype.Repository;

import com.cargo.model.CarTechnique;

@Repository
public class CarTechniqueDao extends BaseDao<CarTechnique> implements ICarTechniqueDao {

	public CarTechniqueDao(){
		super();
		setClazz(CarTechnique.class);
	}
}
