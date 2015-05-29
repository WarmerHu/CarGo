package com.cargo.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
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
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.Assert;

import com.cargo.dao.IAccountDao;
import com.cargo.dao.ICarBodyDao;
import com.cargo.dao.ICarDao;
import com.cargo.dao.ICarEngineDao;
import com.cargo.dao.ICarTechniqueDao;
import com.cargo.model.Account;
import com.cargo.model.Account.Gender;
import com.cargo.model.Account.ProfileType;
import com.cargo.model.Car;
import com.cargo.model.Car.CarType;
import com.cargo.model.CarBody;
import com.cargo.model.CarEngine;
import com.cargo.model.CarEngine.IntakeType;
import com.cargo.model.CarEngine.OilFeedType;
import com.cargo.model.CarTechnique;
import com.cargo.model.CarTechnique.DriveType;
import com.cargo.model.CarTechnique.Gearbox;
import com.cargo.model.CarTechnique.ResistanceType;
import com.cargo.util.Encrypter;


//test
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
	@Autowired
	private ICarBodyDao bodyDao;
	@Autowired
	private ICarEngineDao engineDao;
	@Autowired
	private ICarTechniqueDao techniqueDao;
	
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
		car.setBrand("123");
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
		dao.create(car);
	}
	
	@Before
	public void setup(){
		mocMvc = MockMvcBuilders.standaloneSetup(controller).build();
		initialDate();
	}
	
	
	@Test
	public void saveCar() throws Exception{
		JSONObject cb = new JSONObject();
		cb.put("length", 500);
		cb.put("width", 150);
		cb.put("height", 250);
		cb.put("door", 3);
		cb.put("seat", 5);
		cb.put("wheelbase", 350);
		cb.put("weight", 150);
		cb.put("fuelTank", 350);
		cb.put("trunkSpace", 350);
		cb.put("groundClearance", 350);
		cb.put("guarantee", "test");
		
		JSONObject ct = new JSONObject();
		ct.put("gearbox", Gearbox.CVT);
		ct.put("maxSpeed", 110);
		ct.put("trye", "trye");
		ct.put("gearNum", 8);
		ct.put("driveType", DriveType.中置四驱);
		ct.put("resistanceType", ResistanceType.电动阻力);
		
		JSONObject ce = new JSONObject();
		ce.put("displacement", 5);
		ce.put("intake", IntakeType.涡轮增压);
		ce.put("cylinder", 5);
		ce.put("maxPower", 5);
		ce.put("maxTorque", 5);
		ce.put("fuelLabel", 5);
		ce.put("oilFeed", OilFeedType.单点电喷);
		ce.put("environmental", "environmental");
		
		JSONObject obj = new JSONObject(); 
		obj.put("description", "description");
		obj.put("picture", "picture");
		obj.put("type", CarType.三厢);
		obj.put("brand", "brand");
		obj.put("model", "model");
		obj.put("price", 30002);
		obj.put("discount", 30000);
		obj.put("stock", 30000);
		obj.put("carBody", cb);
		obj.put("carTechnique", ct);
		obj.put("carEngine", ce);
		System.out.println(accountDao.first());
		System.out.println(Encrypter.encode(accountDao.first()));

		mocMvc.perform(post("/cars")
				.contentType(MediaType.APPLICATION_JSON)
				.header("Authorization", Encrypter.encode(accountDao.first()))
				.content(obj.toString()))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.id").exists())
				.andExpect(jsonPath("$.owner").exists())
				.andExpect(jsonPath("$.carBody").exists())
				.andExpect(jsonPath("$.carTechnique").exists())
				.andExpect(jsonPath("$.carEngine").exists())
				.andExpect(jsonPath("$.description").value("description"))
				.andExpect(jsonPath("$.brand").value("brand"))
				.andExpect(jsonPath("$.model").value("model"))
				.andExpect(jsonPath("$.type").value("三厢"))
				.andExpect(jsonPath("$.picture").value("picture"))
				.andExpect(jsonPath("$.stock").value(30000))
				.andExpect(jsonPath("$.price").value(30002))
				.andExpect(jsonPath("$.discount").value(30000))
				.andReturn();
		
		Assert.isTrue(techniqueDao.findAll().size() >= 1);
		Assert.isTrue(bodyDao.findAll().size() >= 1);
		Assert.isTrue(engineDao.findAll().size() >= 1);
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
				.andDo(MockMvcResultHandlers.print())
				.andReturn();
	}
	
	@Test
	public void update() throws Exception{
		Car car = dao.first();
		JSONObject cb = new JSONObject();
		cb.put("door", 100);
		cb.put("guarantee", "guarantee");
		
		JSONObject ct = new JSONObject();
		ct.put("gearNum", 100);
		ct.put("driveType", "后置四驱");
		ct.put("tyre", "tyre");
		
		JSONObject ce = new JSONObject();
		ce.put("cylinder", 100);
		
		JSONObject obj = new JSONObject(); 
		obj.put("stock", 100);
		obj.put("brand", "100");
		obj.put("carBody", cb);
		obj.put("carTechnique", ct);
		obj.put("carEngine", ce);
		mocMvc.perform(patch("/cars/{id}",car.getId())
				.contentType(MediaType.APPLICATION_JSON)
				.content(obj.toString()))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id").exists())
				.andExpect(jsonPath("$.owner").exists())
				.andExpect(jsonPath("$.carBody.door").value(100))
				.andExpect(jsonPath("$.carTechnique").exists())
				.andExpect(jsonPath("$.carEngine").exists())
				.andDo(MockMvcResultHandlers.print())
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
		mocMvc.perform(get("/cars/search?brand={brand}&model={model}&hiprice={hiprice}&gearBox={gearBox}&displacement={displacement}",
				"100","testc1",20001,"AT",400)
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().is(200))
				.andExpect(jsonPath("$").isArray())
				.andReturn();
		
		mocMvc.perform(get("/cars/search?hiprice={hiprice}", 20001)
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().is(200))
				.andExpect(jsonPath("$").isArray())
				.andReturn();
	}
	
	@After
	public void setdown(){
		dao.deleteAll();
		accountDao.deleteAll();
		techniqueDao.deleteAll();
		engineDao.deleteAll();
		bodyDao.deleteAll();
	}

	
}
