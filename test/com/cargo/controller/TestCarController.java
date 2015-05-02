package com.cargo.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
import com.cargo.model.Car;
import com.cargo.model.Car.CarType;


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
	
	public void initialDate(){
		for(int i=1; i<=3; i++){
			Car car = new Car();
			car.setBrand(String.valueOf(i));
			car.setModel("testc1");
			car.setStock(20001);
			car.setPicture("f://...");
			car.setDescription("testc1");
			car.setPrice(20001);
			car.setType(CarType.New);
			car.setOwner(accountDao.first());
			dao.create(car);
		}
		for(int i=1; i<=3; i++){
			Car car = new Car();
			car.setBrand("testc1");
			car.setModel(String.valueOf(i));
			car.setStock(20002);
			car.setPicture("f://...");
			car.setDescription("testc1");
			car.setPrice(20002);
			car.setType(CarType.New);
			car.setOwner(accountDao.first());
			dao.create(car);
		}
		for(int i=1; i<=3; i++){
			Car car = new Car();
			car.setBrand("testc1");
			car.setModel("testc1");
			car.setStock(20003);
			car.setPicture("f://...");
			car.setDescription("testc"+String.valueOf(i));
			car.setPrice(20001);
			car.setType(CarType.Used);
			car.setOwner(accountDao.first());
			dao.create(car);
		}
	}
	
	@Before
	public void setup(){
		mocMvc = MockMvcBuilders.standaloneSetup(controller).build();
		initialDate();
	}
	
	
	@Test
	public void saveCar() throws Exception{
		String requestBody="{\"description\":\"testc2\"," +
							"\"brand\":\"testc2\"," +
							"\"model\":\"testc2\"," +
							"\"picture\":\"f://...\"," +
							"\"type\":\"Used\"," +
							"\"stock\":20004," +
							"\"price\":20004}";
		
		mocMvc.perform(post("/accounts/{account_id}/cars",accountDao.first().getId())
				.contentType(MediaType.APPLICATION_JSON)
				.content(requestBody))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.id").exists())
				.andExpect(jsonPath("$.owner").exists())
				.andExpect(jsonPath("$.description").value("testc2"))
				.andExpect(jsonPath("$.brand").value("testc2"))
				.andExpect(jsonPath("$.model").value("testc2"))
				.andExpect(jsonPath("$.type").value("Used"))
				.andExpect(jsonPath("$.picture").value("f://..."))
				.andExpect(jsonPath("$.stock").value(20004))
				.andExpect(jsonPath("$.price").value(20004))
				.andReturn();
	}
	
	@Test
	public void getList() throws Exception{
		mocMvc.perform(get("/accounts/cars")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().is(200))
				.andExpect(jsonPath("$").isArray())
				.andReturn();
	}
	
	@Test
	public void gets() throws Exception{
		Car car = dao.first();
		mocMvc.perform(get("/accounts/cars/{id}",car.getId())
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id").exists())
				.andExpect(jsonPath("$.owner").exists())
				.andExpect(jsonPath("$.description").value(car.getDescription()))
				.andExpect(jsonPath("$.price").value(car.getPrice()))
				.andExpect(jsonPath("$.brand").value(car.getBrand()))
				.andExpect(jsonPath("$.model").value(car.getModel()))
				.andExpect(jsonPath("$.stock").value(car.getStock()))
				.andExpect(jsonPath("$.type").value(car.getType().name()))
				.andExpect(jsonPath("$.picture").value(car.getPicture()))
				.andReturn();
	}
	
	@Test
	public void update() throws Exception{
		Car car = dao.first();
		String content = "{\"price\":20005}";
		mocMvc.perform(patch("/accounts/{account_id}/cars/{id}",car.getOwner().getId(),car.getId())
				.contentType(MediaType.APPLICATION_JSON)
				.content(content))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id").exists())
				.andExpect(jsonPath("$.owner").exists())
				.andExpect(jsonPath("$.description").value(car.getDescription()))
				.andExpect(jsonPath("$.brand").value(car.getBrand()))
				.andExpect(jsonPath("$.model").value(car.getModel()))
				.andExpect(jsonPath("$.stock").value(car.getStock()))
				.andExpect(jsonPath("$.type").value(car.getType().name()))
				.andExpect(jsonPath("$.picture").value(car.getPicture()))
				.andExpect(jsonPath("$.price").value(20005))
				.andReturn();
	}
	
	@Test
	public void deletes() throws Exception{
		Car car = dao.first();
		mocMvc.perform(delete("/accounts/{account_id}/cars/{id}",car.getOwner().getId(), car.getId())
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNoContent())
				.andReturn();
		Assert.isNull(dao.find(car.getId()));
	}
	
	@Test
	public void search() throws Exception{
		String requestBody="{\"setKeyword\":\"1\"," +
				"\"type\":\"Used\"," +
				"\"hiPrice\":20001}";
		mocMvc.perform(post("/cars")
				.contentType(MediaType.APPLICATION_JSON)
				.content(requestBody))
				.andExpect(status().is(200))
				.andExpect(jsonPath("$").isArray())
				.andReturn();
	}
	
	@After
	public void setdown(){
//		dao.deleteAll();
//		accountDao.deleteAll();
	}

	
}
