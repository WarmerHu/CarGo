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
import com.cargo.dao.ICollectionDao;
import com.cargo.model.Collection;

//test
@Controller
public class CollectionController {
	
	@Autowired
	private ICollectionDao dao;
	@Autowired
	private IAccountDao accountDao;
	@Autowired
	private ICarDao carDao;
	
	@RequestMapping(value="/accounts/{account_id}/collections",method=RequestMethod.POST)
	@ResponseStatus(value=HttpStatus.CREATED)
	public @ResponseBody JSONObject create(@RequestBody JSONObject obj, @PathVariable Long account_id){
		Collection c = new Collection();
		c.setOwner(accountDao.find(account_id));
		c.setCar(carDao.find(Long.parseLong((String) obj.get("car_id"))));
		return dao.create(c).toJSON();
	}
	
	@RequestMapping(value="/accounts/{account_id}/collections/{id}",method=RequestMethod.DELETE)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public @ResponseBody void delete(@PathVariable Long id){
		dao.deleteById(id);
	}
	
	@RequestMapping(value="/accounts/{account_id}/collections",method=RequestMethod.GET)
	public @ResponseBody List<Collection> list(){
		return dao.findAll();
	}
	
	
	
}
