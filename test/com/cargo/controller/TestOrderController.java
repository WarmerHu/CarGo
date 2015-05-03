package com.cargo.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Date;

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

import com.cargo.dao.IAccountDao;
import com.cargo.dao.ICarDao;
import com.cargo.dao.IOrderDao;
import com.cargo.model.Account;
import com.cargo.model.Account.Gender;
import com.cargo.model.Account.ProfileType;
import com.cargo.model.Car;
import com.cargo.model.Car.CarType;
import com.cargo.model.Order;
import com.cargo.model.Order.Result;
import com.cargo.util.Encrypter;


/**
 * 
 * 示例网址	http://jinnianshilongnian.iteye.com/blog/2004660
 * @author Wealong
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:database-servlet.xml")
public class TestOrderController extends AbstractJUnit4SpringContextTests{

	private MockMvc mocMvc;
	
	@Autowired
	private OrderController controller;
	
	@Autowired
	private IAccountDao accountDao;
	@Autowired
	private ICarDao carDao;
	@Autowired
	private IOrderDao dao;
	
	
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
		
		Order order = new Order();
		order.setCar(car);
		order.setBuyer(account);
		order.setBook_time(new Date());
		order.setResult(Result.Booked);
		dao.create(order);
	}
	
	@Test
	public void save() throws Exception{
		mocMvc.perform(post("/cars/{car_id}/orders",carDao.first().getId())
				.content("{}")
				.contentType(MediaType.APPLICATION_JSON)
				.header("Authorization", Encrypter.encode(accountDao.first())))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.car.price").value(20001))
				.andExpect(jsonPath("$.buyer.name").value("testa1"))
				.andExpect(jsonPath("$.result").value("Booked"))
				.andReturn();
	}
	
	@Test
	public void getAccountOrderList() throws Exception{
		mocMvc.perform(get("/orders")
				.contentType(MediaType.APPLICATION_JSON)
				.header("Authorization", Encrypter.encode(accountDao.first())))
				.andExpect(status().is(200))
				.andExpect(jsonPath("$").isArray())
				.andReturn();
	}
	
	@Test
	public void getCarOrderList() throws Exception{
		mocMvc.perform(get("/cars/{car_id}/orders",carDao.first().getId())
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().is(200))
				.andExpect(jsonPath("$").isArray())
				.andReturn();
	}
	
	@After
	public void setdown(){
		dao.deleteAll();
		carDao.deleteAll();
		accountDao.deleteAll();
	}

	
}