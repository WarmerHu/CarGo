package com.cargo.controller;

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
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.cargo.dao.IAccountDao;
import com.cargo.dao.IAdvertisementDao;
import com.cargo.model.Account;
import com.cargo.model.Advertisement;
import com.cargo.model.Account.Gender;
import com.cargo.model.Account.ProfileType;
import com.cargo.model.Advertisement.ADState;
import com.cargo.model.Advertisement.Position;
import com.cargo.util.Encrypter;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:database-servlet.xml")
public class TestAdvertisementController extends AbstractJUnit4SpringContextTests{

	private MockMvc mocMvc;
	
	@Autowired
	private AdvertisementController adcontroller;
	
	@Autowired
	private IAdvertisementDao dao;
	@Autowired
	private IAccountDao accountDao;
	
	
	@Before
	public void setup(){
		mocMvc = MockMvcBuilders.standaloneSetup(adcontroller).build();
		
		Account account = new Account();
		account.setName("testa1");
		account.setPassword("testa1");
		account.setEmail("testa1@test.com");
		account.setAddress("testa1");
		account.setCity("testa1");
		account.setGender(Gender.Lady);
		account.setTelephone("10000000001");
		account.setType(ProfileType.Buyer);
		String encrypt = Encrypter.encode(account);
		account.setAuth_token(encrypt);
		accountDao.create(account);
		
		
//		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Advertisement a = new Advertisement();
		a.setAdstate(ADState.Approval);
//		a.setLength(df.format(new Date()));
		a.setLink(null);
		a.setWord(null);
		a.setOwner(accountDao.first());
		a.setPicture("f：//...");
		a.setPosition(Position.Home);
		dao.create(a);
	}
	
	@Test
	public void save() throws Exception{
		
		JSONObject obj = new JSONObject();
		obj.put("link", "testad1");
		obj.put("picture", "testad1://a/ad.jpg");
		obj.put("position", "Top");
		obj.put("word", "testad1");
		
		mocMvc.perform(post("/ads")
				.contentType(MediaType.APPLICATION_JSON)
				.header("Authorization", Encrypter.encode(accountDao.first()))
				.content(obj.toString()))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.id").exists())
//				.andExpect(jsonPath("$.length").value("2016-3-3 1:1:1"))
				.andExpect(jsonPath("$.link").value("testad1"))
				.andExpect(jsonPath("$.word").value("testad1"))
				.andExpect(jsonPath("$.adstate").value("Apply"))
				.andExpect(jsonPath("$.position").value("Top"))
				.andExpect(jsonPath("$.picture").value("testad1://a/ad.jpg"))
				.andReturn();
	}
	
	@Test
	public void update() throws Exception{
		Advertisement a = dao.first();
		String content = "{\"adstate\":\"Fail\"}";
		mocMvc.perform(patch("/ads/{id}",a.getId())
				.contentType(MediaType.APPLICATION_JSON)
				.content(content))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id").exists())
				.andExpect(jsonPath("$.adstate").value("Fail"))
				.andReturn();
	}
	
	@Test
	public void getList() throws Exception{
		mocMvc.perform(get("/ads")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().is(200))
				.andExpect(jsonPath("$").isArray())
				.andReturn();
	}
	
	@Test
	public void gets() throws Exception{
		Advertisement a = dao.first();
		mocMvc.perform(get("/ads/{id}", a.getId())
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andReturn();
	}
	
	@After
	public void setdown(){
		dao.deleteAll();
		accountDao.deleteAll();
	}

	
}
