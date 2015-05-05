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
import com.cargo.model.Favorite;
import com.cargo.util.HttpUtil;

//test
@Controller
public class FavoriteController {
	
	@Autowired
	private IFavoriteDao dao;
	@Autowired
	private IAccountDao accountDao;
	@Autowired
	private ICarDao carDao;
	
	@RequestMapping(value="/favorites",method=RequestMethod.POST)
	@ResponseStatus(value=HttpStatus.CREATED)
	public @ResponseBody JSONObject create(@RequestBody JSONObject obj,WebRequest request){
		Favorite c = new Favorite();
		c.setOwner(getCurrentUser(request));
		c.setName((String) obj.get("name"));
		return dao.create(c).toJSON();
	}
	
	@RequestMapping(value="/favorites/{id}",method=RequestMethod.GET)
	public @ResponseBody JSONObject get(@PathVariable Long id, WebRequest request){
		return dao.find(id).toJSON();
	}
	
	@RequestMapping(value="/favorites/{id}",method=RequestMethod.POST)
	@ResponseStatus(value=HttpStatus.CREATED)
	public void collect(@RequestBody JSONObject obj,@PathVariable Long id, WebRequest request){
		Favorite favorite = dao.find(id);
		Car car = carDao.find(new Long((Integer)obj.get("car_id")));
		if(car != null){
			favorite.addCar(car);
		}
		dao.update(favorite);
	}
	
	@RequestMapping(value="/favorites/{id}",method=RequestMethod.DELETE)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remove(@RequestBody JSONObject obj,@PathVariable Long id, WebRequest request){
		Favorite favorite = dao.find(id);
		if(obj.containsKey("car_id")){
			Car car = carDao.find(new Long((Integer)obj.get("car_id")));
			System.out.println(String.valueOf(favorite.getCars().contains(car)));
			if(car != null){
				favorite.removeCar(car);
			}
			dao.update(favorite);
		}else{
			dao.delete(favorite);
		}
	}
	
	@RequestMapping(value="/favorites",method=RequestMethod.GET)
	public @ResponseBody JSONArray list(WebRequest request){
		JSONArray array = new JSONArray();
		List<Favorite> cs = dao.findAll();
		for(Favorite c : cs){
			array.add(c.toJSON());
		}
		return array;
	}
	
	private Account getCurrentUser(WebRequest request) {
		return new HttpUtil(accountDao).getCurrentUser(request);
	}
	
	
}
