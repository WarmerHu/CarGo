package com.cargo.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

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
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.Assert;

import com.cargo.dao.IAccountDao;
import com.cargo.dao.ICarDao;
import com.cargo.model.Account;
import com.cargo.model.Account.ProfileType;
import com.cargo.model.Car;


/**
 * 
 * 示例网址	http://jinnianshilongnian.iteye.com/blog/2004660
 * @author Wealong
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:database-servlet.xml")
public class TestCarController extends AbstractJUnit4SpringContextTests{

	private MockMvc mocMvc;
	
	@Autowired
	private CarController controller;
	
	@Autowired
	private ICarDao dao;
	@Autowired
	private IAccountDao accountDao;
	
	@Before
	public void setup(){
		mocMvc = MockMvcBuilders.standaloneSetup(controller).build();
		Account account = new Account();
		account.setName("test");
		account.setPassword("test");
		account.setEmail("test@test.com");
		account.setGender("boy");
		account.setTelephone("13456780123");
		account.setType(ProfileType.Solder);
		account = accountDao.create(account);
		Car car = new Car();
		car.setDescription("good car");
		car.setOwner(account);
		car.setPrice("$8000");
		dao.create(car);
	}
	
	@Test
	public void save() throws Exception{
		String requestBody="{\"description\":\"good car\"," +
							"\"price\":\"$8000\"}";
		
		mocMvc.perform(post("/accounts/{account_id}/cars",accountDao.first().getId())
				.contentType(MediaType.APPLICATION_JSON)
				.content(requestBody))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.id").exists())
				.andExpect(jsonPath("$.description").value("good car"))
				.andExpect(jsonPath("$.price").value("$8000"))
				.andExpect(jsonPath("$.owner.id").exists())
				.andReturn();
	}
	
	@Test
	public void gets() throws Exception{
		Car car = dao.first();
		mocMvc.perform(get("/accounts/{account_id}/cars/{id}",car.getOwner().getId(),car.getId())
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id").exists())
				.andExpect(jsonPath("$.description").value(car.getDescription()))
				.andExpect(jsonPath("$.price").value(car.getPrice()))
				.andExpect(jsonPath("$.owner.id").exists())
				.andReturn();
	}
	
	@Test
	public void update() throws Exception{
		Car car = dao.first();
		String content = "{\"price\":\"$7000\" }";
		mocMvc.perform(patch("/accounts/{account_id}/cars/{id}",car.getOwner().getId(),car.getId())
				.contentType(MediaType.APPLICATION_JSON)
				.content(content))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.price").value("$7000"))
				.andExpect(jsonPath("$.id").exists())
				.andExpect(jsonPath("$.description").value(car.getDescription()))
				.andExpect(jsonPath("$.owner.id").exists())
				.andReturn();
	}
	
	@Test
	public void getList() throws Exception{
		mocMvc.perform(get("/accounts/{id}/cars",dao.first().getOwner().getId())
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().is(200))
				.andExpect(jsonPath("$").isArray())
				.andReturn();
	}
	
	@Test
	public void deletes() throws Exception{
		List<Car> cars = dao.findAll();
		Car car = cars.get(cars.size() - 1);
		mocMvc.perform(delete("/accounts/{id}/cars/{id}",car.getOwner().getId(), car.getId())
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNoContent())
				.andReturn();
		Assert.isNull(dao.find(car.getId()));
	}
	
	@After
	public void setdown(){
		dao.deleteAll();
		accountDao.deleteAll();
	}

	
}
