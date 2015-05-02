package com.cargo.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.text.SimpleDateFormat;
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
import com.cargo.dao.IAdvertisementDao;
import com.cargo.model.Advertisement;
import com.cargo.model.Advertisement.ADState;
import com.cargo.model.Advertisement.Position;

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
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Advertisement a = new Advertisement();
		a.setAdstate(ADState.Approval);
		a.setLength(df.format(new Date()));
		a.setLink(null);
		a.setOwner(accountDao.first());
		a.setPicture("f：//。。。");
		a.setPosition(Position.Home);
		dao.create(a);
	}
	
	@Test
	public void saveCar() throws Exception{
		String requestBody="{\"length\":\"2016-3-3 1:1:1\"," +
							"\"link\":\"www.baidu.com\"," +
							"\"picture\":\"f://...\"," +
							"\"adstate\":\"Apply\"," +
							"\"position\":\"Top\"}";
		
		mocMvc.perform(post("/accounts/{account_id}/ads",accountDao.first().getId())
				.contentType(MediaType.APPLICATION_JSON)
				.content(requestBody))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.id").exists())
				.andExpect(jsonPath("$.owner").exists())
				.andExpect(jsonPath("$.length").value("2016-3-3 1:1:1"))
				.andExpect(jsonPath("$.link").value("www.baidu.com"))
				.andExpect(jsonPath("$.adstate").value("Apply"))
				.andExpect(jsonPath("$.position").value("Top"))
				.andExpect(jsonPath("$.picture").value("f://..."))
				.andReturn();
	}
	
	@Test
	public void update() throws Exception{
		Advertisement a = dao.first();
		String content = "{\"adstate\":\"Fail\"}";
		mocMvc.perform(patch("/accounts/{account_id}/ads/{id}",a.getOwner().getId(),a.getId())
				.contentType(MediaType.APPLICATION_JSON)
				.content(content))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id").exists())
				.andExpect(jsonPath("$.owner").exists())
				.andExpect(jsonPath("$.adstate").value("Fail"))
				.andReturn();
	}
	
	@Test
	public void getList() throws Exception{
		mocMvc.perform(get("/accounts/{account_id}/ads",accountDao.first().getId())
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().is(200))
				.andExpect(jsonPath("$").isArray())
				.andReturn();
	}
	
	@Test
	public void gets() throws Exception{
		Advertisement a = dao.first();
		mocMvc.perform(get("/accounts/ads/{id}", a.getId())
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andReturn();
	}
	
	@After
	public void setdown(){
//		dao.deleteAll();
//		accountDao.deleteAll();
	}

	
}
