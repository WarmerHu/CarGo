package com.cargo.controller;

import java.util.Date;
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
import com.cargo.dao.IOrderDao;
import com.cargo.model.Account;
import com.cargo.model.Order;
import com.cargo.model.Order.Result;
import com.cargo.util.HttpUtil;

@Controller
public class OrderController {

	@Autowired
	private IOrderDao dao;
	@Autowired
	private ICarDao carDao;
	@Autowired
	private IAccountDao accountDao;

	@RequestMapping(value="/cars/{car_id}/orders",method=RequestMethod.POST)
	@ResponseStatus(value=HttpStatus.CREATED)
	public @ResponseBody JSONObject create(@RequestBody Order order,@PathVariable Long car_id, WebRequest request){
		order.setBuyer(getCurrentUser(request));
		order.setCar(carDao.find(car_id));
		order.setBook_time(new Date());
		order.setResult(Result.Booking);
		return dao.create(order).toJSON();
	} 
	
	@RequestMapping(value="/orders/{id}",method=RequestMethod.PATCH)
	public @ResponseBody JSONObject patch(@RequestBody Order order, @PathVariable Long id ,WebRequest request){
		order.setId(id);
		dao.update(order);
		return dao.find(id).toJSON();
		
	}
	
	@RequestMapping(value="/cars/{car_id}/orders",method=RequestMethod.GET)
	public @ResponseBody JSONArray carOrderList(@PathVariable Long car_id,WebRequest request){
		JSONArray array = new JSONArray();
		List<Order> os = carDao.getOrders(car_id);
		for(Order o : os){
			array.add(o.toJSON());
		}
		return array;
	}
	
	@RequestMapping(value="/orders",method=RequestMethod.GET)
	public @ResponseBody JSONArray accountOrderList(WebRequest request){
		JSONArray array = new JSONArray();
		List<Order> os = accountDao.getOrders(getCurrentUser(request).getId());
		for(Order o : os){
			array.add(o.toJSON());
		}
		return array;
	} 
	
	private Account getCurrentUser(WebRequest request) {
		return new HttpUtil(accountDao).getCurrentUser(request);
	}
}
