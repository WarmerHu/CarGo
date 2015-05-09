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
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.Assert;

import com.cargo.dao.IAccountDao;
import com.cargo.dao.ICarDao;
import com.cargo.model.Account;
import com.cargo.model.Car;
import com.cargo.model.Account.Gender;
import com.cargo.model.Account.ProfileType;
import com.cargo.model.Car.CarType;
import com.cargo.util.Encrypter;


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
		dao.create(car);
		
		car.setBrand("testc1");
		car.setModel("testc1");
		car.setStock(20001);
		car.setPicture("f://...");
		car.setDescription("testc1");
		car.setPrice(20001);
		car.setType(CarType.New);
		car.setAccount(accountDao.first());
		dao.create(car);
		
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
		
		mocMvc.perform(post("/cars")
				.contentType(MediaType.APPLICATION_JSON)
				.header("Authorization", Encrypter.encode(accountDao.first()))
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
		mocMvc.perform(get("/cars")
				.contentType(MediaType.APPLICATION_JSON)
				.header("Authorization", Encrypter.encode(accountDao.first())))
				.andExpect(status().is(200))
				.andExpect(jsonPath("$").isArray())
				.andExpect(jsonPath("$[0].isFavor").value(false))
				.andDo(MockMvcResultHandlers.print())
				.andReturn();
	}
	
	@Test
	public void gets() throws Exception{
		Car car = dao.first();
		mocMvc.perform(get("/cars/{id}",car.getId())
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
		mocMvc.perform(patch("/cars/{id}",car.getId())
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
		mocMvc.perform(delete("/cars/{id}", car.getId())
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNoContent())
				.andReturn();
		Assert.isNull(dao.find(car.getId()));
	}
	
	@Test
	public void searchs() throws Exception{
		mocMvc.perform(get("/cars/search/{keyword}&{type}&{brand}&{model}&{city}&{suppliers}&{loprice}&{hiprice}",
				"testa1","New","testc1",null,"testa1",null,"1000",null)
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().is(200))
				.andExpect(jsonPath("$").isArray())
				.andReturn();
	}
	
	@After
	public void setdown(){
		dao.deleteAll();
		accountDao.deleteAll();
	}

	
}
