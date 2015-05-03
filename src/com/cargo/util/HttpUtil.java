package com.cargo.util;

import java.io.IOException;

import org.springframework.web.context.request.WebRequest;

import com.cargo.dao.IAccountDao;
import com.cargo.model.Account;

public class HttpUtil {
	
	
	private IAccountDao dao;
	
	public HttpUtil(IAccountDao dao){
		this.dao = dao;
	}
	
	public Account getCurrentUser(WebRequest request){
		Account account = ((Account)request.getAttribute("current_user", 2));
		if(account != null){
			return account;
		}else{
			String encrypt = request.getHeader("Authorization");
			String token = null;
			try {
				token = Encrypter.decode(encrypt);
			} catch (IOException e) {
				e.printStackTrace();
			}
			String[] tokens = token.split(" ");
			account = dao.findByName(tokens[0]);
			if(account!=null){
				request.setAttribute("current_user", account, 2);
				return account;
			}
		} 
		return null;
	}

}
