//package com.cargo.dao;
//
//import java.util.List;
//
//import org.hibernate.Criteria;
//import org.hibernate.Query;
//import org.hibernate.Session;
//import org.hibernate.criterion.Criterion;
//import org.hibernate.criterion.Restrictions;
//import org.springframework.stereotype.Repository;
//
//import com.cargo.model.Car;
//import com.cargo.model.Order;
//import com.cargo.model.Selection;
//
//@Repository
//public class CarDao extends BaseDao<Car> implements ICarDao {
//	
//	public CarDao(){
//		super();
//		setClazz(Car.class);
//	}
//	
//	
//	@Override
//	public void update(Car car) {
//		Car old = find(car.getId());
//		car.setAccount(old.getAccount());
//		if(car.getStock() == 0) car.setStock(old.getStock());
//		if(car.getPicture() == null) car.setPicture(old.getPicture());
//		if(car.getBrand() == null) car.setBrand(old.getBrand());
//		if(car.getModel() == null) car.setModel(old.getModel());
//		if(car.getType() == null) car.setType(old.getType());
//		if(car.getDescription() == null) car.setDescription(old.getDescription());
//		if(car.getPrice() == 0) car.setPrice(old.getPrice());
//		super.update(car);
//	}
//	
//	@SuppressWarnings("unchecked")
//	public List<Car> findByArgs(String keyword, String type, String brand, String model, int loprice,int hiprice){
//		Session session = this.getCurrentSession();
//		Criteria criteria=session.createCriteria(Car.class);
//		criteria.createCriteria("owner");
//		Criterion criterion = null;
//		if(brand != null)	criterion = Restrictions.eq("brand", brand);
//		if(model != null){
//			if(criterion == null)	criterion = Restrictions.eq("model", model);
//			else	criterion = Restrictions.and(criterion, Restrictions.eq("model", model));
//		}
//		if(type != null){
//			if(criterion == null)	criterion = Restrictions.eq("type", type);
//			else	criterion = Restrictions.and(criterion, Restrictions.eq("type", type));
//		}
//		if(hiprice){
//			if(criterion == null)	criterion = Restrictions.between("price", selection.getLowPrice(), selection.getHiPrice());
//			else	criterion = Restrictions.and(criterion, Restrictions.between("price", selection.getLowPrice(), selection.getHiPrice()));
//		}
//		
//		if(selection.getCity()!=null || selection.getSuppliers()!=null){
//			Query query = null;
//			List<Long> id = null;
//			if(selection.getCity() != null){
//				if(selection.getSuppliers() != null)	query = session.createQuery("select id from Account where city="+selection.getCity()+"and name="+selection.getSuppliers());
//				else	query = session.createQuery("select id from Account where city="+selection.getCity());
//			}
//			else if(selection.getSuppliers() != null)	query = session.createQuery("select id from Account where name="+selection.getSuppliers());
//			id = query.list();
//			if(criterion == null)	criterion = Restrictions.in("owner", id);
//			else	criterion = Restrictions.and(criterion, Restrictions.in("owner", id));
//		}
//		
//		return criteria.add(
//				Restrictions.or(
//						Restrictions.like("description", "%"+selection.getKeyword()+"%"),
//						criterion
//						)
//				).list();
//	}
//
//
//	@Override
//	public List<Order> getOrders(Long car_id) {
//		@SuppressWarnings("unchecked")
//		List<Order> list = getCurrentSession().createQuery("from Order as o where o.car = ?")
//						.setEntity(0, find(car_id))
//						.list();
//		return list;
//	}
//	
//}
