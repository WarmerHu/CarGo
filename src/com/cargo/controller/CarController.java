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
import com.cargo.model.Car;
import com.cargo.util.HttpUtil;

@Controller
public class CarController {
	
	@Autowired
	private ICarDao dao;
	@Autowired
	private IAccountDao accountDao;
	
	@RequestMapping(value="/cars",method=RequestMethod.POST)
	@ResponseStatus(value=HttpStatus.CREATED)
	public @ResponseBody JSONObject create(@RequestBody Car car,WebRequest request){
		car.setAccount(new HttpUtil(accountDao).getCurrentUser(request));
		return dao.create(car).toJSON();
	}
	
	@RequestMapping(value="/cars",method=RequestMethod.GET)
	public @ResponseBody JSONArray list(){
		JSONArray array = new JSONArray();
		List<Car> cars = dao.findAll();
		for(Car car : cars){
			array.add(car.toJSON());
		}
		return array;
	}
	
	@RequestMapping(value="/cars/{id}",method=RequestMethod.GET)
	public @ResponseBody JSONObject show(@PathVariable Long id){
		return dao.find(id).toJSON();
	}
	
	@RequestMapping(value="/cars/{id}",method=RequestMethod.PATCH)
	public @ResponseBody JSONObject patch(@RequestBody Car car,@PathVariable Long id){
		car.setId(id);
		dao.update(car);
		return dao.find(id).toJSON();
	}
	
	@RequestMapping(value="/cars/{id}",method=RequestMethod.DELETE)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public @ResponseBody void delete(@PathVariable Long id){
		dao.deleteById(id);
	}
	
//	@RequestMapping(value="/cars",method=RequestMethod.POST)
//	public @ResponseBody List<Car> search(@RequestBody Selection selection){
//		return dao.findBySelection(selection);
//	}
	
}
