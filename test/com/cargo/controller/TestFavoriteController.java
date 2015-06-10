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
	
public void initialDate(){
		
		Account account = new Account();
		account.setName("testfavor1");
		account.setPassword("testa1");
		account.setEmail("testa1@test.com");
		account.setAddress("testa1");
		account.setCity("testa1");
		account.setGender(Gender.Lady);
		account.setTelephone("10000000001");
		account.setType(ProfileType.Buyer);
		accountDao.create(account);
		
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
		car.setBrand("favor");
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
		car.setAccount(accountDao.first());
		carDao.create(car);

		Favorite c = new Favorite();
		c.setName("favor");
		c.setOwner(accountDao.first());
		c.addCar(car);

		
//		car = new Car();
//		car.setBrand("321");
//		car.setModel("testc2");
//		car.setStock(20002);
//		car.setPicture("f://...");
//		car.setDescription("testc2");
//		car.setPrice(20002);
//		car.setType(CarType.SUV越野车);
//		car.setAccount(accountDao.first());
//		carDao.create(car);
//
//		c.addCar(car);
		dao.create(c);


}
	
	@Before
	public void setup(){
		mocMvc = MockMvcBuilders.standaloneSetup(controller).build();
//		this.initialDate();
	}
	
	@Test
	public void create() throws Exception{
		int i  = accountDao.first().getCollections().size();
		JSONObject obj = new JSONObject();
		obj.put("name","favor2");
		mocMvc.perform(post("/favorites")
				.content(obj.toJSONString())
				.header("Authorization", Encrypter.encode(accountDao.first()))
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().is(201))
				.andExpect(jsonPath("$.name").value("favor2"))
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
//		dao.deleteAll();
//		carDao.deleteAll();
//		accountDao.deleteAll();
	}

	
}
