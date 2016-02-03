package com.cargo.dao;

import java.math.BigInteger;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.cargo.model.Order;



@Repository
public class OrderDao extends BaseDao<Order> implements IOrderDao {

	public OrderDao(){
		super();
		setClazz(Order.class);
	}
	
	public void deleteByCarId(long id){
		Session session = null;
		try {
		session = getCurrentSession();
		org.hibernate.Query query = session.createQuery("delete from Order s where s.car=?")
				.setBigInteger(0, new BigInteger(Long.toString(id)));
		
		query.executeUpdate();
		} catch (HibernateException e) {
		e.printStackTrace();
		}
	}
	
}
