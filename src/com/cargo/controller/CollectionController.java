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
import com.cargo.dao.ICollectionDao;
import com.cargo.model.Account;
import com.cargo.model.Car;
import com.cargo.model.Collection;
import com.cargo.util.HttpUtil;

//test
@Controller
public class CollectionController {
	
	@Autowired
	private ICollectionDao dao;
	@Autowired
	private IAccountDao accountDao;
	@Autowired
	private ICarDao carDao;
	
	@RequestMapping(value="/collections",method=RequestMethod.POST)
	@ResponseStatus(value=HttpStatus.CREATED)
	public @ResponseBody JSONObject create(@RequestBody JSONObject obj,WebRequest request){
		Collection c = new Collection();
		c.setOwner(getCurrentUser(request));
		c.setName((String) obj.get("name"));
		return dao.create(c).toJSON();
	}
	
	@RequestMapping(value="/collections/{id}",method=RequestMethod.GET)
	public @ResponseBody JSONObject get(@PathVariable Long id, WebRequest request){
		return dao.find(id).toJSON();
	}
	
	@RequestMapping(value="/collections/{id}",method=RequestMethod.POST)
	@ResponseStatus(value=HttpStatus.CREATED)
	public void collect(@RequestBody JSONObject obj,@PathVariable Long id, WebRequest request){
		Collection collection = dao.find(id);
		Car car = carDao.find(new Long((Integer)obj.get("car_id")));
		if(car != null){
			collection.addCar(car);
		}
		dao.update(collection);
	}
	
	@RequestMapping(value="/collections/{id}",method=RequestMethod.DELETE)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remove(@RequestBody JSONObject obj,@PathVariable Long id, WebRequest request){
		Collection collection = dao.find(id);
		if(obj.containsKey("car_id")){
			Car car = carDao.find(new Long((Integer)obj.get("car_id")));
			System.out.println(String.valueOf(collection.getCars().contains(car)));
			if(car != null){
				collection.removeCar(car);
			}
			dao.update(collection);
		}else{
			dao.delete(collection);
		}
	}
	
	@RequestMapping(value="/collections",method=RequestMethod.GET)
	public @ResponseBody JSONArray list(WebRequest request){
		JSONArray array = new JSONArray();
		List<Collection> cs = dao.findAll();
		for(Collection c : cs){
			array.add(c.toJSON());
		}
		return array;
	}
	
	private Account getCurrentUser(WebRequest request) {
		return new HttpUtil(accountDao).getCurrentUser(request);
	}
	
}
