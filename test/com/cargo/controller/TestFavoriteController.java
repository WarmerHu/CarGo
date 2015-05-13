package com.cargo.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import net.minidev.json.JSONObject;

import org.junit.After;
import org.junit.Assert;
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

import com.cargo.dao.IAccountDao;
import com.cargo.dao.ICarDao;
import com.cargo.dao.IFavoriteDao;
import com.cargo.model.Account;
import com.cargo.model.Car;
import com.cargo.model.Favorite;
import com.cargo.model.Account.Gender;
import com.cargo.model.Account.ProfileType;
import com.cargo.model.Car.CarType;
import com.cargo.util.Encrypter;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:database-servlet.xml")
public class TestFavoriteController extends AbstractJUnit4SpringContextTests{

	private MockMvc mocMvc;
	
	@Autowired
	private FavoriteController controller;
	
	@Autowired
	private IFavoriteDao dao;
	@Autowired
	private ICarDao carDao;
	@Autowired
	private IAccountDao accountDao;
	
	
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
		car.setType(CarType.New);
		car.setAccount(accountDao.first());
		carDao.create(car);
		
		car = new Car();
		car.setBrand("321");
		car.setModel("testc2");
		car.setStock(20002);
		car.setPicture("f://...");
		car.setDescription("testc2");
		car.setPrice(20002);
		car.setType(CarType.New);
		car.setAccount(accountDao.first());
		carDao.create(car);
		
		Favorite c = new Favorite();
		c.setName("hello");
		c.setOwner(accountDao.first());
		c.addCar(car);
		dao.create(c);
	}
	
	@Test
	public void create() throws Exception{
		int i  = accountDao.first().getCollections().size();
		JSONObject obj = new JSONObject();
		obj.put("name","hello2");
		mocMvc.perform(post("/favorites")
				.content(obj.toJSONString())
				.header("Authorization", Encrypter.encode(accountDao.first()))
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().is(201))
				.andExpect(jsonPath("$.name").value("hello2"))
				.andReturn();
		Assert.assertEquals(accountDao.first().getCollections().size(), i + 1);
	}
	
	@Test
	public void gets() throws Exception{
		mocMvc.perform(get("/favorites/{id}",dao.first().getId())
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().is(200))
				.andExpect(jsonPath("$.cars").isArray())
				.andReturn();
	}
	
	@Test
	public void getList() throws Exception{
		mocMvc.perform(get("/favorites")
				.header("Authorization", Encrypter.encode(accountDao.first()))
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().is(200))
				.andExpect(jsonPath("$").isArray())
				.andReturn();
	}
	
	@Test
	public void collect() throws Exception{
		int i = dao.first().getCars().size();
		mocMvc.perform(post("/favorites/{id}",dao.first().getId())
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\"car_id\":"+dao.first().getCars().get(0).getId().toString()+"}"))
				.andExpect(status().is(201))
				.andReturn();
		Assert.assertEquals(dao.first().getCars().size(), i + 1);
	}
	
	@Test
	public void remove() throws Exception{
		int i = dao.first().getCars().size();
		mocMvc.perform(delete("/favorites/{id}/cars/{car_id}",dao.first().getId(),dao.first().getCars().get(0).getId())
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().is(204))
				.andReturn();
		Assert.assertEquals(dao.first().getCars().size(), i - 1);
	}
	
	@Test
	public void deletes() throws Exception{
		int i = dao.findAll().size();
		mocMvc.perform(delete("/favorites/{id}",dao.first().getId())
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().is(204))
				.andReturn();
		Assert.assertEquals(dao.findAll().size(), i - 1);
	}
	
	@After
	public void setdown(){
		dao.deleteAll();
		carDao.deleteAll();
		accountDao.deleteAll();
	}

	
}
