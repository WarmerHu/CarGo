package com.cargo.controller;

import java.util.List;

import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;

import com.cargo.dao.IAccountDao;
import com.cargo.dao.ICarDao;
import com.cargo.dao.IFavoriteDao;
import com.cargo.model.Account;
import com.cargo.model.Car;
import com.cargo.util.HttpUtil;

//test
@Controller
public class CarController {
	
	@Autowired
	private ICarDao dao;
	@Autowired
	private IAccountDao accountDao;
	@Autowired
	private IFavoriteDao favorDao;
	
	@RequestMapping(value="/cars",method=RequestMethod.POST)
	@ResponseStatus(value=HttpStatus.CREATED)
	public @ResponseBody JSONObject create(@RequestBody Car car,WebRequest request){
		car.setAccount(new HttpUtil(accountDao).getCurrentUser(request));
		System.out.println(car.getCarBody());
		return dao.create(car).toJSON();
	}
	
	@RequestMapping(value="/cars",method=RequestMethod.GET)
	public @ResponseBody JSONArray list(WebRequest request){
		Account currentUser = new HttpUtil(accountDao).getCurrentUser(request);
		JSONArray array = new JSONArray();
		
		List<Car> cars = dao.findAll();
		JSONObject obj;
		for(Car car : cars){
			obj = car.toJSON();
			obj.put("isFavor", favorDao.isFavor(currentUser, car));
			array.add(obj);
		}
		return array;
	}
	
	@RequestMapping(value="/cars/{id}",method=RequestMethod.GET)
	public @ResponseBody JSONObject show(@PathVariable Long id){
		return dao.find(id).toJSON();
	}
	
	@RequestMapping(value="/cars/{id}",method=RequestMethod.PATCH)
	public @ResponseBody JSONObject patch(@RequestBody JSONObject obj,@PathVariable Long id){
		Car car = dao.find(id);
		dao.update(car,obj);
		return dao.find(id).toJSON();
	}
	
	@RequestMapping(value="/cars/{id}",method=RequestMethod.DELETE)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public @ResponseBody void delete(@PathVariable Long id){
//		Session s = dao.getCurrentSession();
//		Car c = (Car)s.load(Car.class,id);
		dao.deleteById(id);
//		s.delete(c); 
		
	}
	
//	@RequestMapping(value="/cars/search/{keyword}&{type}&{brand}&{model}&{city}&{suppliers}&{loprice}&{hiprice}",method=RequestMethod.GET)
	@RequestMapping(value="/cars/search/{brand}&{type}&{model}&{loprice}&{hiprice}&{gearBox}&{displacement}",method=RequestMethod.GET)
	public @ResponseBody JSONArray list(@PathVariable String brand,@PathVariable String type,@PathVariable String model,@PathVariable String loprice,@PathVariable String hiprice,@PathVariable String gearBox, @PathVariable String displacement){
		JSONArray array = new JSONArray();
		List<Car> cars = dao.findByArgs(brand,type,model,loprice,hiprice, gearBox, displacement);
		for(Car car : cars){
			array.add(car.toJSON());
		}
		return array;
	}
	
}
