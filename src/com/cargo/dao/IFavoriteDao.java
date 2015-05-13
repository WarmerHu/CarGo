package com.cargo.dao;

import com.cargo.model.Account;
import com.cargo.model.Car;
import com.cargo.model.Favorite;


public interface IFavoriteDao extends IBaseDao<Favorite>{
	
	public void removeCar(Favorite collection, Car car);
	public boolean isFavor(Account account, Car car);

}