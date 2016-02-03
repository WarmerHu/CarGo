package com.cargo.dao;

import org.springframework.stereotype.Repository;

import com.cargo.model.Account;
import com.cargo.model.Car;
import com.cargo.model.Favorite;

@Repository
public class FavoriteDao extends BaseDao<Favorite> implements IFavoriteDao {

	public FavoriteDao(){
		super();
		setClazz(Favorite.class);
	}
	
	@Override
	public void update(Favorite favorite) {
		Favorite old = find(favorite.getId());
		if(favorite.getName() == null) favorite.setName(old.getName());
		if(favorite.getCars() == null) favorite.setCars(old.getCars());
		if(favorite.getOwner() == null) favorite.setOwner(old.getOwner());
		getCurrentSession().merge(favorite);
	}

	@Override
	public void removeCar(Favorite favorite, Car car) {
		getCurrentSession().createSQLQuery("delete from favorite_car where favoriteid = ? and carid = ?")
			.setParameter(0, favorite.getId())
			.setParameter(1,car.getId())
			.executeUpdate();
	}

	@Override
	public boolean isFavor(Account account, Car car) {
		for(Favorite favor : account.getCollections()){
			if(favor.getCars().contains(car))
				return true;
		}
		return false;
	}
}
