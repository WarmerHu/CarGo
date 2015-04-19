package com.cargo.controller;

import java.util.List;

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

import com.cargo.dao.IAccountDao;
import com.cargo.dao.ICarDao;
import com.cargo.model.Car;

@Controller
public class CarController {
	
	@Autowired
	private ICarDao dao;
	@Autowired
	private IAccountDao accountDao;
	
	@RequestMapping(value="/accounts/{account_id}/cars",method=RequestMethod.POST)
	@ResponseStatus(value=HttpStatus.CREATED)
	public @ResponseBody JSONObject create(@RequestBody Car car,@PathVariable Long account_id){
		car.setOwner(accountDao.find(account_id));
		return dao.create(car).toJSON();
	}
	
	@RequestMapping(value="/accounts/{account_id}/cars/{id}",method=RequestMethod.GET)
	public @ResponseBody JSONObject show(@PathVariable Long id){
		return dao.find(id).toJSON();
	}
	
	@RequestMapping(value="/accounts/{account_id}/cars",method=RequestMethod.GET)
	public @ResponseBody List<Car> list(){
		return dao.findAll();
	}
	
	@RequestMapping(value="/accounts/{account_id}/cars/{id}",method=RequestMethod.DELETE)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public @ResponseBody void delete(@PathVariable Long id){
		dao.deleteById(id);
	}
	
	@RequestMapping(value="/accounts/{account_id}/cars/{id}",method=RequestMethod.PATCH)
	public @ResponseBody JSONObject patch(@RequestBody Car car,@PathVariable Long account_id,@PathVariable Long id){
		car.setCarid(id);
		dao.update(car);
		return dao.find(id).toJSON();
	}
}
