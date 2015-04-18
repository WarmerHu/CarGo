package com.cargo.dao;

import org.springframework.stereotype.Repository;

import com.cargo.model.Advertisement;



@Repository
public class AdvertisementDao extends BaseDao<Advertisement> implements IAdvertisementDao {

	public AdvertisementDao(){
		super();
		setClazz(Advertisement.class);
	}
	
}
