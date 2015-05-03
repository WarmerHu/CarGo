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
import org.springframework.web.context.request.WebRequest;

import com.cargo.dao.IAccountDao;
import com.cargo.dao.IAdvertisementDao;
import com.cargo.model.Advertisement;
import com.cargo.util.HttpUtil;

@Controller
public class AdvertisementController {
	
	@Autowired
	private IAdvertisementDao adDao;
	@Autowired
	private IAccountDao accountDao;

	@RequestMapping(value="/ads",method=RequestMethod.POST)
	@ResponseStatus(value=HttpStatus.CREATED)
	public @ResponseBody JSONObject create(@RequestBody Advertisement ad,WebRequest request){
		ad.setOwner(new HttpUtil(accountDao).getCurrentUser(request));
		return adDao.create(ad).toJSON();
	}
	
//	@RequestMapping(value="/ads/{id}",method=RequestMethod.DELETE)
//	@ResponseStatus(HttpStatus.NO_CONTENT)
//	public @ResponseBody void delete(@PathVariable Long id){
//		adDao.deleteById(id);
//	}
	
	@RequestMapping(value="/ads/{id}",method=RequestMethod.PATCH)
	public @ResponseBody JSONObject patch(@RequestBody Advertisement ad,@PathVariable Long id){
		ad.setId(id);
		adDao.update(ad);
		return adDao.find(ad.getId()).toJSON();
	}
	
	@RequestMapping(value="/ads",method=RequestMethod.GET)
	public @ResponseBody List<Advertisement> list(){
		return adDao.findAll();
	}
	
	@RequestMapping(value="/ads/{id}",method=RequestMethod.GET)
	public @ResponseBody JSONObject show(@PathVariable Long id){
		return adDao.find(id).toJSON();
	}
	
}
