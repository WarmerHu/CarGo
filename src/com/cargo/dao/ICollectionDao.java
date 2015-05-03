package com.cargo.dao;

import com.cargo.model.Car;
import com.cargo.model.Collection;


public interface ICollectionDao extends IBaseDao<Collection>{
	
	public void removeCar(Collection collection, Car car);

}
