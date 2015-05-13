package com.cargo.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Date;
import java.util.List;

import net.minidev.json.JSONObject;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.Assert;

import com.cargo.dao.IAccountDao;
import com.cargo.dao.ICarDao;
import com.cargo.dao.ICommentDao;
import com.cargo.model.Account;
import com.cargo.model.Account.Gender;
import com.cargo.model.Account.ProfileType;
import com.cargo.model.Car;
import com.cargo.model.Car.CarType;
import com.cargo.model.Comment;
import com.cargo.util.Encrypter;


/**
 * 
 * 示例网址	http://jinnianshilongnian.iteye.com/blog/2004660
 * @author Wealong
 * @author Warmer
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:database-servlet.xml")
public class TestCommentController extends AbstractJUnit4SpringContextTests{

	private MockMvc mocMvc;
	
	@Autowired
	private CommentController controller;
	
	@Autowired
	private IAccountDao accountDao;
	@Autowired
	private ICarDao carDao;
	@Autowired
	private ICommentDao dao;
	
	
	@Before
	public void setup(){
		mocMvc = MockMvcBuilders.standaloneSetup(controller).build();
		Account account = new Account();
		account.setName("testa1");
		account.setPassword("testa1");
		account.setEmail("testa1@test.com");
		account.setAddress("testa1");
		account.setCity("testa1");
		account.setGender(Gender.Lady);
		account.setTelephone("10000000001");
		account.setType(ProfileType.Buyer);
		accountDao.create(account);
		
		Car car = new Car();
		car.setBrand("123");
		car.setModel("testc1");
		car.setStock(20001);
		car.setPicture("f://...");
		car.setDescription("testc1");
		car.setPrice(20001);
		car.setType(CarType.MPV商务车);
		car.setAccount(accountDao.first());
		carDao.create(car);
		
		Comment comment = new Comment();
		comment.setCar(car);
		comment.setOwner(account);
		comment.setTime(new Date());
		comment.setContent("hello is a good car");
		dao.create(comment);
	}
	
	@Test
	public void save() throws Exception{
		JSONObject obj = new JSONObject();
		obj.put("content", "content");
		
		mocMvc.perform(post("/cars/{car_id}/comments",carDao.first().getId())
				.contentType(MediaType.APPLICATION_JSON)
				.header("Authorization", Encrypter.encode(accountDao.first()))
				.content(obj.toJSONString()))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.content").value("content"))
				.andReturn();
	}
	
	@Test
	public void getList() throws Exception{
		mocMvc.perform(get("/cars/{car_id}/comments",carDao.first().getId())
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().is(200))
				.andExpect(jsonPath("$").isArray())
				.andReturn();
	}
	
	@Test
	public void deleteComment() throws Exception{
		List<Comment> comments = dao.findAll();
		Comment comment = comments.get(comments.size() - 1);
		mocMvc.perform(delete("/cars/{car_id}/comments/{id}",carDao.first().getId(),comment.getId())
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNoContent())
				.andReturn();
		Assert.isNull(dao.find(comment.getId()));
	}
	
	@After
	public void setdown(){
		dao.deleteAll();
		carDao.deleteAll();
		accountDao.deleteAll();
	}

	
}
