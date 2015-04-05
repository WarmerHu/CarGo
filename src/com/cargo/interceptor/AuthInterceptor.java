package com.cargo.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.cargo.dao.IAccountDao;
import com.cargo.model.Account;
import com.cargo.util.Encrypter;



public class AuthInterceptor implements HandlerInterceptor{
	
	@Autowired
	private IAccountDao dao;

	public void afterCompletion(HttpServletRequest arg0,
			HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {
		// TODO Auto-generated method stub
		
	}

	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1,
			Object arg2, ModelAndView arg3) throws Exception {
		// TODO Auto-generated method stub
		
	}

	public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
			Object obj) throws Exception {
		String encrypt = request.getHeader("Authorization");
		
		if(encrypt == null){
			System.out.println("AuthToken不能为空！");
		}
		String token = Encrypter.decode(encrypt);
		System.out.println(token);
		String[] tokens = token.split(" ");
		Account account = dao.findByName(tokens[0]);
		if(account!=null&&account.getAuth_token().equals(encrypt))
			System.out.println("你成功登录");
		else 
			System.out.println("你尚未登录");
		return true;
	}

}
