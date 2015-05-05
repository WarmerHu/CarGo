package com.cargo.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
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

//test
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
	public @ResponseBody JSONObject create(@RequestBody JSONObject obj, @PathVariable Long car_id, WebRequest request){
		Order order = new Order();
		order.setBuyer(getCurrentUser(request));
		order.setCar(carDao.find(car_id));
//		order.setBook_time(new Date());
		order.setResult(Result.Booking);
		try {
			SimpleDateFormat sdf =   new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date date = sdf.parse((String) obj.get("book_time"));
			order.setBook_time(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return dao.create(order).toJSON();
	} 
	
	@RequestMapping(value="/orders/{id}",method=RequestMethod.PATCH)
	public @ResponseBody JSONObject update(@RequestBody JSONObject obj,@PathVariable Long id ,WebRequest request){
		Order order = dao.find(id);
		order.setResult(Result.valueOf((String) obj.get("result")));
		if((String) obj.get("book_time") != null){
//			SimpleDateFormat sdf =   new SimpleDateFormat( " yyyy-MM-dd HH:mm:ss " );
			Date date;
			try {
				date = new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss" ).parse((String) obj.get("book_time"));
				order.setBook_time(date);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
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
