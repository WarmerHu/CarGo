package com.cargo.dao;

import org.springframework.stereotype.Repository;

import com.cargo.model.Collection;


@Repository
public class CollectionDao extends BaseDao<Collection> implements ICollectionDao {

	public CollectionDao(){
		super();
		setClazz(Collection.class);
	}
	
	
}
