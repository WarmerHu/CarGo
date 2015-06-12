package com.cargo.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
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
	
	@RequestMapping(value="/cars/search",method=RequestMethod.GET)
	public @ResponseBody JSONArray list(@RequestParam(value="brand",required=false) String brand,
		   @RequestParam(value="type",required=false) String type,@RequestParam(value="model",required=false) String model,
		   @RequestParam(value="loprice",required=false) String loprice,@RequestParam(value="hiprice",required=false) String hiprice,
		   @RequestParam(value="gearBox",required=false) String gearBox, @RequestParam(value="displacement",required=false) String displacement, HttpServletRequest request){
		
		Map<String, String> args = new HashMap<String, String>();
		if(brand != null) args.put("brand", brand);
		if(type != null) args.put("type", type);
		if(model != null) args.put("model", model);
		if(loprice != null) args.put("loprice", loprice);
		if(hiprice != null) args.put("hiprice", hiprice);
		if(gearBox != null) args.put("gearBox", gearBox);
		if(displacement != null) args.put("displacement", displacement);
		JSONArray array = new JSONArray();
		List<Car> cars = dao.findByArgs(args);
		for(Car car : cars){
			array.add(car.toJSON());
		}
		return array;
	}
	
	@RequestMapping(value="/cars/search",method=RequestMethod.POST,produces = "application/json;charset=UTF-8")
	public @ResponseBody JSONArray list(@RequestBody JSONObject obj, HttpServletRequest request){
		String brand = (String) obj.get("brand");
		String model = (String) obj.get("model");
		String type = (String) obj.get("type");
		String loprice = (String) obj.get("loprice");
		String hiprice = (String) obj.get("hiprice");
		String gearBox = (String) obj.get("gearBox");
		String displacement = (String) obj.get("displacement");
		Map<String, String> args = new HashMap<String, String>();
		if(brand != null) args.put("brand", brand);
		if(type != null) args.put("type", type);
		if(model != null) args.put("model", model);
		if(loprice != null) args.put("loprice", loprice);
		if(hiprice != null) args.put("hiprice", hiprice);
		if(gearBox != null) args.put("gearBox", gearBox);
		if(displacement != null) args.put("displacement", displacement);
		JSONArray array = new JSONArray();
		List<Car> cars = dao.findByArgs(args);
		for(Car car : cars){
			array.add(car.toJSON());
		}
		return array;
	}
	
}
