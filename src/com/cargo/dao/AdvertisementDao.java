package com.cargo.dao;

import org.springframework.stereotype.Repository;

import com.cargo.model.Advertisement;



@Repository
public class AdvertisementDao extends BaseDao<Advertisement> implements IAdvertisementDao {

	public AdvertisementDao(){
		super();
		setClazz(Advertisement.class);
	}
	
	@Override
	public void update(Advertisement ad) {
		Advertisement old = find(ad.getId());
		ad.setOwner(old.getOwner());
		if(ad.getLength() == null)	ad.setLength(old.getLength());
		if(ad.getLink() == null)	ad.setLength(old.getLength());
		if(ad.getAdstate() == null)	ad.setAdstate(old.getAdstate());
		if(ad.getPicture() == null)	ad.setPicture(old.getPicture());
		if(ad.getPosition() == null)	ad.setPosition(old.getPosition());
		super.update(ad);
	}
	
}
