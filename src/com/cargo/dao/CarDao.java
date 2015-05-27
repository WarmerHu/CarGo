package com.cargo.dao;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import net.minidev.json.JSONObject;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.cargo.model.Car;
import com.cargo.model.Car.CarType;
import com.cargo.model.CarEngine;
import com.cargo.model.CarEngine.IntakeType;
import com.cargo.model.CarEngine.OilFeedType;
import com.cargo.model.CarTechnique;
import com.cargo.model.CarTechnique.DriveType;
import com.cargo.model.CarTechnique.Gearbox;
import com.cargo.model.CarTechnique.ResistanceType;
import com.cargo.model.Order;

@Repository
public class CarDao extends BaseDao<Car> implements ICarDao {
	
	public CarDao(){
		super();
		setClazz(Car.class);
	}
	
	
	@SuppressWarnings("unchecked")
	public void update(Car car, JSONObject obj) {
		System.out.println("car.gets:"+car.getStock());
		
		if(obj.get("stock").toString() != null){ 
			car.setStock((Integer) obj.get("stock"));
			System.out.println("obj.gets:"+obj.get("stock"));
		}
		if(obj.get("picture") != null){
			car.setPicture(obj.get("picture").toString());
			System.out.println("pic:"+obj.get("picture"));
		}
		if(obj.get("brand") != null) car.setBrand(obj.get("brand").toString());
		if(obj.get("model") != null) car.setModel(obj.get("model").toString());
		if(obj.get("type") != null) car.setType(CarType.valueOf(obj.get("type").toString()));
		if(obj.get("description") != null) car.setDescription(obj.get("Description").toString());
		if(obj.get("price") != null) car.setPrice((Integer) obj.get("price"));
		if(obj.get("discount") != null) car.setDiscount((Integer) obj.get("discount"));
		System.out.println("car.gets:"+car.getStock());
		if(obj.get("carBody") != null){
			Map<String,String> cb = (( LinkedHashMap<String, String>) obj.get("carBody"));
			System.out.println("cb:"+cb);
			System.out.println(car.getCarBody());
			System.out.println(car.getCarBody().getDoor());
			if(cb.get("door") != null) car.getCarBody().setDoor(Integer.parseInt(String.valueOf(cb.get("door"))));
			if(cb.get("fuelTank") != null) car.getCarBody().setFuelTank(Integer.parseInt(String.valueOf(cb.get("fuelTank"))));
			if(cb.get("groundClearance") != null) car.getCarBody().setGroundClearance(Integer.parseInt(String.valueOf(cb.get("groundClearance"))));
			if(cb.get("guarantee") != null) car.getCarBody().setGuarantee(cb.get("guarantee"));
			if(cb.get("height") != null) car.getCarBody().setHeight(Integer.parseInt(String.valueOf(cb.get("height"))));
			if(cb.get("length") != null) car.getCarBody().setLength(Integer.parseInt(String.valueOf(cb.get("length"))));
			if(cb.get("seat") != null) car.getCarBody().setSeat(Integer.parseInt(String.valueOf(cb.get("seat"))));
			if(cb.get("trunkSpace") != null) car.getCarBody().setTrunkSpace(Integer.parseInt(String.valueOf(cb.get("trunkSpace"))));
			if(cb.get("weight") != null) car.getCarBody().setWeight(Integer.parseInt(String.valueOf(cb.get("weight"))));
			if(cb.get("wheelbase") != null) car.getCarBody().setWheelbase(Integer.parseInt(String.valueOf(cb.get("wheelbase"))));
			if(cb.get("width") != null) car.getCarBody().setWidth(Integer.parseInt(String.valueOf(cb.get("width"))));
			if(cb.get("width") != null) car.getCarBody().setWidth(Integer.parseInt(String.valueOf(cb.get("width"))));
			System.out.println(car.getCarBody().getDoor());
		}
		if(obj.get("carTechnique") != null){
			Map<String,String> ct = (( LinkedHashMap<String, String>) obj.get("carTechnique"));
			System.out.println("ct:"+ct);
			System.out.println("ct.geth:"+car.getCarTechnique().getGearNum());
			if(ct.get("gearNum") != null) 
				car.getCarTechnique().setGearNum(Integer.parseInt(String.valueOf(ct.get("gearNum"))));
			
			if(ct.get("maxSpeed") != null)
				car.getCarTechnique().setMaxSpeed(Integer.parseInt(String.valueOf(ct.get("maxSpeed"))));
			if(ct.get("driveType") != null) 
				car.getCarTechnique().setDriveType(DriveType.valueOf(ct.get("driveType")));
			if(ct.get("gearbox") != null) car.getCarTechnique().setGearbox(Gearbox.valueOf(ct.get("gearbox").toString()));
			if(ct.get("resistanceType") != null) car.getCarTechnique().setResistanceType(ResistanceType.valueOf(ct.get("resistanceType").toString()));
			if(ct.get("tyre") != null) car.getCarTechnique().setTyre(ct.get("tyre").toString());
			System.out.println("car.geth:"+car.getCarTechnique().getGearNum());
		}
		if(obj.get("carEngine") != null){
			Map<String,String> ce = (( LinkedHashMap<String, String>) obj.get("carEngine"));
			System.out.println(ce);
			System.out.println(car.getCarEngine().getCylinder());
			if(ce.get("cylinder") != null) car.getCarEngine().setCylinder(Integer.parseInt(String.valueOf(ce.get("cylinder"))));
			if(ce.get("displacement") != null) car.getCarEngine().setDisplacement(Integer.parseInt(String.valueOf(ce.get("displacement"))));
			if(ce.get("fuelLabel") != null) car.getCarEngine().setFuelLabel(Integer.parseInt(String.valueOf(ce.get("fuelLabel"))));
			if(ce.get("maxPower") != null) car.getCarEngine().setMaxPower(Integer.parseInt(String.valueOf(ce.get("maxPower"))));
			if(ce.get("maxTorque") != null) car.getCarEngine().setMaxTorque(Integer.parseInt(String.valueOf(ce.get("maxTorque"))));
			if(ce.get("environmental") != null) car.getCarEngine().setEnvironmental(ce.get("environmental").toString());
			if(ce.get("intake") != null) car.getCarEngine().setIntake(IntakeType.valueOf(ce.get("intake").toString()));
			if(ce.get("oilFeed") != null) car.getCarEngine().setOilFeed(OilFeedType.valueOf(ce.get("oilFeed").toString()));
			System.out.println(car.getCarEngine().getCylinder());
		}
		super.update(car);
	}
	
	@SuppressWarnings("unchecked")
	public List<Car> findByArgs(Map<String, String> args){
		Session session = getCurrentSession();
		Criteria criteria=session.createCriteria(Car.class);
		Criterion criterion = null;
		if(args.containsKey("brand"))	
			criterion = Restrictions.eq("brand", args.get("brand"));
		if(args.containsKey("type") && Arrays.asList(CarType.values()).contains(args.get("type"))){
			if(criterion == null)	criterion = Restrictions.eq("type", CarType.valueOf(args.get("type")));
			else criterion = Restrictions.and(criterion, Restrictions.eq("type", CarType.valueOf(args.get("type"))));
		}
		if(args.containsKey("model")){
			if(criterion == null)	criterion = Restrictions.eq("model", args.get("model"));
			else	criterion = Restrictions.and(criterion, Restrictions.eq("model", args.get("model")));
		}
		
		if(!args.containsKey("loprice")) args.put("loprice", "0");
		if(!args.containsKey("hiprice")) args.put("hiprice", "99999999");
		if(criterion == null)	criterion = Restrictions.between("price", Integer.parseInt(args.get("loprice")), Integer.parseInt(args.get("hiprice")));
		else	criterion = Restrictions.and(criterion, Restrictions.between("price", Integer.parseInt(args.get("loprice")), Integer.parseInt(args.get("hiprice"))));
		
		if(args.containsKey("gearBox") && Arrays.asList(Gearbox.values()).contains(args.get("gearBox"))){
			System.out.println(Gearbox.valueOf(args.get("gearBox")).ordinal());
			Query q = session.createQuery("from CarTechnique as c where c.gearbox=?")
					.setInteger(0, Gearbox.valueOf(args.get("gearBox")).ordinal());
			List<CarTechnique> a = q.list();
			if(criterion == null)	criterion = Restrictions.in("carTechnique", a);
			else	criterion = Restrictions.and(criterion, Restrictions.in("carTechnique", a));
		}
		
		if(args.containsKey("displacement")){
			Query q = session.createQuery("from CarEngine as c where c.displacement=?")
					.setInteger(0, Integer.parseInt(args.get("displacement")));
			List<CarEngine> a = q.list();
			if(!a.isEmpty()){
				if(criterion == null)	criterion = Restrictions.in("carEngine", a);
				else	criterion = Restrictions.and(criterion, Restrictions.in("carEngine", a));
			}
		}
		
		
		return criteria.add(criterion).list();
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
