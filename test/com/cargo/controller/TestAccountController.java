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
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.Assert;

import com.cargo.dao.IAccountDao;
import com.cargo.dao.ICarDao;
import com.cargo.dao.IFavoriteDao;
import com.cargo.model.Account;
import com.cargo.model.Car;
import com.cargo.model.CarBody;
import com.cargo.model.CarEngine;
import com.cargo.model.CarTechnique;
import com.cargo.model.Favorite;
import com.cargo.model.Account.Gender;
import com.cargo.model.Account.ProfileType;
import com.cargo.model.Car.CarType;
import com.cargo.model.CarEngine.IntakeType;
import com.cargo.model.CarEngine.OilFeedType;
import com.cargo.model.CarTechnique.DriveType;
import com.cargo.model.CarTechnique.Gearbox;
import com.cargo.model.CarTechnique.ResistanceType;


/**
 * 
 * 示例网址	http://jinnianshilongnian.iteye.com/blog/2004660
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:database-servlet.xml")
public class TestAccountController extends AbstractJUnit4SpringContextTests{

	private MockMvc mocMvc;
	
	@Autowired
	private AccountController controller;
	
	@Autowired
	private IAccountDao dao;
	@Autowired
	private ICarDao carDao;
	@Autowired
	private IFavoriteDao favDao;
	
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
		dao.create(account);
		
		CarBody cb = new CarBody();
		cb.setDoor(4);
		cb.setFuelTank(4);
		cb.setGroundClearance(140);
		cb.setGuarantee("4年4万公里");
		cb.setHeight(140);
		cb.setLength(400);
		cb.setSeat(4);
		cb.setTrunkSpace(4);
		cb.setWeight(400);
		cb.setWheelbase(2040);
		cb.setWidth(240);
		
		CarTechnique ct = new CarTechnique();
		ct.setDriveType(DriveType.中置后驱);
		ct.setGearbox(Gearbox.AT);
		ct.setGearNum(4);
		ct.setMaxSpeed(120);
		ct.setResistanceType(ResistanceType.液压助力);
		ct.setTyre("185/60R14");
		
		CarEngine ce = new CarEngine();
		ce.setCylinder(4);
		ce.setDisplacement(400);
		ce.setEnvironmental("test环保标准001");
		ce.setFuelLabel(90);
		ce.setIntake(IntakeType.机械增压);
		ce.setMaxPower(80);
		ce.setMaxTorque(100);
		ce.setOilFeed(OilFeedType.化油器);
		
		Car car = new Car();
		car.setBrand("1234");
		car.setModel("testc1");
		car.setStock(20001);
		car.setPicture("f://...");
		car.setDescription("testc1");
		car.setPrice(20001);
		car.setDiscount(20000);
		car.setType(CarType.MPV商务车);
		car.setCarBody(cb);
		car.setCarEngine(ce);
		car.setCarTechnique(ct);
		car.setAccount(dao.first());
		carDao.create(car);
		
		Favorite c = new Favorite();
		c.setName("hello");
		c.setOwner(dao.first());
		c.addCar(car);
		favDao.create(c);
	}
	
	@Before
	public void setup(){
		mocMvc = MockMvcBuilders.standaloneSetup(controller).build();
		initialDate();
	}
	
	@Test
	public void saveAccount() throws Exception{
		String requestBody="{\"name\":\"testa6\"," +
							"\"password\":\"testa1\"," +
							"\"email\":\"testa1@test.com\"," +
							"\"address\":\"testa1\"," +
							"\"city\":\"testa1\"," +
							"\"gender\":\"Man\"," +
							"\"type\":\"Buyer\"," +
							"\"telephone\":\"10000000001\"}";
		
		mocMvc.perform(post("/accounts")
				.contentType(MediaType.APPLICATION_JSON)
				.content(requestBody))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.name").value("testa6"))
				.andExpect(jsonPath("$.password").value("testa1"))
				.andExpect(jsonPath("$.email").value("testa1@test.com"))
				.andExpect(jsonPath("$.address").value("testa1"))
				.andExpect(jsonPath("$.city").value("testa1"))
				.andExpect(jsonPath("$.gender").value("Man"))
				.andExpect(jsonPath("$.type").value("Buyer"))
				.andExpect(jsonPath("$.telephone").value("10000000001"))
				.andExpect(jsonPath("$.type").value("Buyer"))
				.andReturn();
	}
	
	@Test
	public void login() throws Exception{
		String requestbody = "{\"name\":\"testa1\",\"password\":\"testa1\"}";
		mocMvc.perform(post("/accounts/login")
				.contentType(MediaType.APPLICATION_JSON)
				.content(requestbody))
				.andExpect(jsonPath("$.auth_token").exists())
				.andReturn();
	}
	
	@Test
	public void getAccounts() throws Exception{
		mocMvc.perform(get("/accounts")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().is(200))
				.andExpect(jsonPath("$").isArray())
				.andReturn();
	}
	
	@Test
	public void getAccount() throws Exception{
		Account account = dao.first();
		mocMvc.perform(get("/accounts/{id}",account.getId())
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.name").value(account.getName()))
				.andExpect(jsonPath("$.email").value(account.getEmail()))
				.andExpect(jsonPath("$.address").value(account.getAddress()))
				.andExpect(jsonPath("$.city").value(account.getCity()))
				.andExpect(jsonPath("$.gender").value(account.getGender().name()))
				.andExpect(jsonPath("$.telephone").value(account.getTelephone()))
				.andExpect(jsonPath("$.type").value(account.getType().name()))
				.andReturn();
	}
	
	@Test
	public void updateAccount() throws Exception{
		Account account = dao.first();
		String content=	"{\"password\":\"testa3\"," +
				"\"email\":\"testa1@test.com\"," +
				"\"name\":\"testa2\"," +
				"\"address\":\"testa1\"," +
				"\"city\":\"testa1\"," +
				"\"gender\":\"Man\"," +
				"\"type\":\"Solder\"," +
				"\"telephone\":\"10000000003\"}";
		mocMvc.perform(patch("/accounts/{id}",account.getId())
				.contentType(MediaType.APPLICATION_JSON)
				.content(content))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.telephone").value("10000000003"))
				.andExpect(jsonPath("$.type").value("Solder"))
				.andReturn();
	}
	
	
	@Test
	public void deleteAccount() throws Exception{
		List<Account> accounts = dao.findAll();
		Account account = accounts.get(accounts.size() - 1);
		mocMvc.perform(delete("/accounts/{id}",account.getId())
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNoContent())
				.andReturn();
		Assert.isNull(dao.find(account.getId()));
	}
	

	
	@After
	public void setdown(){
		favDao.deleteAll();
		carDao.deleteAll();
		dao.deleteAll();
	}

	
}
