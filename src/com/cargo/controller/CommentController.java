package com.cargo.controller;

import java.util.Date;
import java.util.Set;

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
import com.cargo.dao.ICommentDao;
import com.cargo.model.Comment;
import com.cargo.util.HttpUtil;

@Controller
public class CommentController {
	
	@Autowired
	private ICommentDao dao;
	@Autowired
	private ICarDao carDao;
	@Autowired
	private IAccountDao accountDao;

	@RequestMapping(value="/cars/{car_id}/comments",method=RequestMethod.POST)
	@ResponseStatus(value=HttpStatus.CREATED)
	public @ResponseBody JSONObject create(@RequestBody Comment comment, @PathVariable Long car_id, WebRequest request){
		comment.setOwner(new HttpUtil(accountDao).getCurrentUser(request));
		comment.setCar(carDao.find(car_id));
		comment.setTime(new Date());
		return dao.create(comment).toJSON();
	} 
	
	@RequestMapping(value="/cars/{car_id}/comments/{id}",method=RequestMethod.DELETE)
	@ResponseStatus(value=HttpStatus.NO_CONTENT)
	public void delete(@PathVariable Long id ,WebRequest request){
		dao.deleteById(id);
	} 
	
	@RequestMapping(value="/cars/{car_id}/comments",method=RequestMethod.GET)
	public @ResponseBody JSONArray list(@PathVariable Long car_id,WebRequest request){
		JSONArray array = new JSONArray();
		Set<Comment> cs =  carDao.find(car_id).getComments();
		for(Comment c : cs){
			array.add(c.toJSON());
		}
		return array;
	} 
}
