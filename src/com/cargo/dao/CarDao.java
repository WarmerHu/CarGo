package com.cargo.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.cargo.model.Account;
import com.cargo.model.Car;
import com.cargo.model.Car.CarType;
import com.cargo.model.Order;

@Repository
public class CarDao extends BaseDao<Car> implements ICarDao {
	
	public CarDao(){
		super();
		setClazz(Car.class);
	}
	
	
	@Override
	public void update(Car car) {
		Car old = find(car.getId());
		car.setAccount(old.getAccount());
		if(car.getStock() == 0) car.setStock(old.getStock());
		if(car.getPicture() == null) car.setPicture(old.getPicture());
		if(car.getBrand() == null) car.setBrand(old.getBrand());
		if(car.getModel() == null) car.setModel(old.getModel());
		if(car.getType() == null) car.setType(old.getType());
		if(car.getDescription() == null) car.setDescription(old.getDescription());
		if(car.getPrice() == 0) car.setPrice(old.getPrice());
		super.update(car);
	}
	
	@SuppressWarnings("unchecked")
	public List<Car> findByArgs(String keyword, String type, String brand, String model, String city, String suppliers, String loprice, String hiprice){
		Session session = getCurrentSession();
		Criteria criteria=session.createCriteria(Car.class);
		Criterion criterion = null;
		if(!brand.equals(""))	
			criterion = Restrictions.eq("brand", brand);
		if(!model.equals("")){
			if(criterion == null)	criterion = Restrictions.eq("model", model);
			else	criterion = Restrictions.and(criterion, Restrictions.eq("model", model));
		}
		if(!type.equals("")){
			if(criterion == null)	criterion = Restrictions.eq("type", CarType.valueOf(type).ordinal());
			else	criterion = Restrictions.and(criterion, Restrictions.eq("type", CarType.valueOf(type)));
		}
		if(hiprice.equals("")) hiprice = "999999";
		if(loprice.equals("")) loprice = "0";
		if(criterion == null)	criterion = Restrictions.between("price", Integer.parseInt(loprice), Integer.parseInt(hiprice));
		else	criterion = Restrictions.and(criterion, Restrictions.between("price", Integer.parseInt(loprice), Integer.parseInt(hiprice)));
		
		if(!city.equals("") || !suppliers.equals("")){
			Query query = null;
			List<Account> a = null;
			if(!city.equals("")){
				if(!suppliers.equals(""))	
					query = session.createQuery("from Account as a where a.city=? and a.name=?")
						.setString(0, city)
						.setString(1, suppliers);
				else	
					query = session.createQuery("from Account as a where a.city=?")
						.setString(0, city);
			}
			else if(!suppliers.equals(""))	
				query = session.createQuery("from Account as a where a.name=?")
					.setString(0, suppliers);
			if(query != null){
				System.out.println(query);
				try{
					a = query.list();
				}
				catch (Exception ex){
					System.out.println(ex);
				}
				if(criterion == null)	criterion = Restrictions.in("account", a);
				else	criterion = Restrictions.and(criterion, Restrictions.in("account", a));

			}
		}
		
		return criteria.add(
				Restrictions.or(
						Restrictions.like("description", keyword, MatchMode.ANYWHERE),
						criterion
						)
				).list();
	}


	@Override
	public List<Order> getOrders(Long car_id) {
		@SuppressWarnings("unchecked")
		List<Order> list = getCurrentSession().createQuery("from Order as o where o.car = ?")
						.setEntity(0, find(car_id))
						.list();
		System.out.println(getCurrentSession().createQuery("from Order as o where o.car = ?")
						.setEntity(0, find(car_id)));
		System.out.println(list);
		return list;
	}
	
}
