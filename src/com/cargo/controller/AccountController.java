package com.cargo.controller;

import java.security.NoSuchAlgorithmException;
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
import com.cargo.model.Account;
import com.cargo.util.Encrypter;

@Controller
public class AccountController {
	
	@Autowired
	private IAccountDao accountDao;

	@RequestMapping(value="/accounts",method=RequestMethod.POST)
	@ResponseStatus(value=HttpStatus.CREATED)
	public @ResponseBody JSONObject create(@RequestBody Account account){
		return accountDao.create(account).toJSON();
	}
	
	
	@RequestMapping(value="/accounts/login",method=RequestMethod.POST)
	public @ResponseBody Account login(@RequestBody Account a) throws NoSuchAlgorithmException{
		Account account = accountDao.findByName(a.getName());
		System.out.println(account);
		if (account != null && account.getPassword().equals(a.getPassword())){
			String encrypt = Encrypter.encode(a.getName() + " " + a.getPassword());
			account.setAuth_token(encrypt);
			accountDao.update(account);
		} else {
			account = null;
		}
		return account;
	}
	
	@RequestMapping(value="/accounts",method=RequestMethod.GET)
	public @ResponseBody List<Account> list(WebRequest request){
		return accountDao.findAll();
	}
	
	@RequestMapping(value="/accounts/{id}",method=RequestMethod.GET)
	public @ResponseBody JSONObject show(@PathVariable Long id){
		return accountDao.find(id).toJSON();
	}
	
	@RequestMapping(value="/accounts/{id}",method=RequestMethod.PATCH)
	public @ResponseBody JSONObject patch(@RequestBody Account account,@PathVariable Long id){
		account.setId(id);
		accountDao.update(account);
		return accountDao.find(account.getId()).toJSON();
	}
		
	@RequestMapping(value="/accounts/{id}",method=RequestMethod.DELETE)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public @ResponseBody void delete(@PathVariable Long id){
		accountDao.deleteById(id);
	}
	
}
