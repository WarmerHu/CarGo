package com.cargo.dao;

import org.springframework.stereotype.Repository;

import com.cargo.model.Shop;



@Repository
public class ShopDao extends BaseDao<Shop> implements IShopDao {

	public ShopDao(){
		super();
		setClazz(Shop.class);
	}
	
}
