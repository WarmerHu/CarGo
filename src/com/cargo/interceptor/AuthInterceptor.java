package com.cargo.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.context.request.WebRequestInterceptor;

import com.cargo.dao.IAccountDao;
import com.cargo.model.Account;
import com.cargo.util.Encrypter;


public class AuthInterceptor implements WebRequestInterceptor{
	
	@Autowired
	private IAccountDao dao;

	@Override
	public void afterCompletion(WebRequest arg0, Exception arg1)
			throws Exception {
	}

	@Override
	public void postHandle(WebRequest arg0, ModelMap arg1) throws Exception {
	}

	@Override
	public void preHandle(WebRequest request) throws Exception {
		String encrypt = request.getHeader("Authorization");
		if(encrypt == null){
			System.out.println("AuthToken Not found!");
		}
		String token = Encrypter.decode(encrypt);
		String[] tokens = token.split(" ");
		Account account = dao.findByName(tokens[0]);
		if(account!=null&&account.getAuth_token().equals(encrypt)){
			System.out.println(String.format("Hello, %s !", account.getName()));
			request.setAttribute("current_user", account, 2);
		}
		else 
			System.out.println("Sorry , Please login !");
	}

}
