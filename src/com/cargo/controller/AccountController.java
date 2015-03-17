package com.cargo.controller;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import sun.misc.BASE64Encoder;

import com.cargo.dao.IAccountDao;
import com.cargo.model.Account;


@Controller
public class AccountController {
	
	@Autowired
	private IAccountDao dao;

	@RequestMapping(value="/accounts",method=RequestMethod.POST)
	public @ResponseBody Account create(@RequestBody Account account){
		return dao.find(dao.create(account));
	}
	
	@RequestMapping(value="/accounts/{id}",method=RequestMethod.GET)
	public @ResponseBody Account show(@PathVariable Long id){
		return dao.find(id);
	}
	
	@RequestMapping(value="/accounts",method=RequestMethod.GET)
	public @ResponseBody List<Account> list(){
		return dao.findAll();
	}
	
	@RequestMapping(value="/accounts/{id}",method=RequestMethod.DELETE)
	public @ResponseBody String delete(@PathVariable Long id){
		dao.deleteById(id);
		return "";
	}
	
	@RequestMapping(value="/accounts/{id}",method=RequestMethod.PATCH)
	public @ResponseBody Account patch(@RequestBody Account account,@PathVariable Long id){
		account.setId(id);
		dao.update(account);
		return dao.find(account.getId());
	}
	
	@RequestMapping(value="/login/{id}",method=RequestMethod.POST)
	public @ResponseBody Account login(@RequestBody Account a, @PathVariable Long id) throws NoSuchAlgorithmException{
		Account account = dao.find(id);
		if (account.getPassword().equals(a.getPassword())){
			String encrypt = new BASE64Encoder().encode((account.getName()+ " " + new String(encryptMD5(a.getPassword().getBytes()))).getBytes()).toString();
			account.setAuth_token(encrypt);
			dao.update(account);
		}
		return account;
	}

	private byte[] encryptMD5(byte[] data) throws NoSuchAlgorithmException {
		MessageDigest md5 = MessageDigest.getInstance("MD5");  
	    md5.update(data);  
	    return md5.digest(); 
	}
}
