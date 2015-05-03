package com.cargo.dao;

import org.springframework.stereotype.Repository;

import com.cargo.model.Car;
import com.cargo.model.Collection;

@Repository
public class CollectionDao extends BaseDao<Collection> implements ICollectionDao {

	public CollectionDao(){
		super();
		setClazz(Collection.class);
	}
	
	@Override
	public void update(Collection collection) {
		Collection old = find(collection.getId());
		if(collection.getName() == null) collection.setName(old.getName());
		if(collection.getCars() == null) collection.setCars(old.getCars());
		if(collection.getOwner() == null) collection.setOwner(old.getOwner());
		getCurrentSession().merge(collection);
	}

	@Override
	public void removeCar(Collection collection, Car car) {
		getCurrentSession().createSQLQuery("delete from collection_car where collectionid = ? and carid = ?")
			.setParameter(0, collection.getId())
			.setParameter(1,car.getId())
			.executeUpdate();
	}
}
