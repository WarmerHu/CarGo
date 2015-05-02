package com.cargo.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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

import com.cargo.dao.IAccountDao;
import com.cargo.dao.ICarDao;
import com.cargo.dao.ICollectionDao;
import com.cargo.model.Collection;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:database-servlet.xml")
public class TestCollectionController extends AbstractJUnit4SpringContextTests{

	private MockMvc mocMvc;
	
	@Autowired
	private CollectionController controller;
	
	@Autowired
	private ICollectionDao dao;
	@Autowired
	private ICarDao carDao;
	@Autowired
	private IAccountDao accountDao;
	
	
	@Before
	public void setup(){
		mocMvc = MockMvcBuilders.standaloneSetup(controller).build();
		Collection c = new Collection();
		c.setCar(carDao.first());
		c.setOwner(accountDao.first());
		dao.create(c);
	}
	
	@Test
	public void saves() throws Exception{
		String requestBody="{\"car_id\":\"2\"}";
		
		mocMvc.perform(post("/accounts/{account_id}/collections",accountDao.first().getId())
				.contentType(MediaType.APPLICATION_JSON)
				.content(requestBody))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.car").exists())
				.andExpect(jsonPath("$.owner").exists())
				.andReturn();
	}
	
	
	@Test
	public void deletes() throws Exception{
		mocMvc.perform(delete("/accounts/{account_id}/collections/{id}",accountDao.first().getId(),dao.first().getId())
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNoContent())
				.andReturn();
	}
	
	@Test
	public void gets() throws Exception{
		mocMvc.perform(get("/accounts/{account_id}/collections",accountDao.first().getId(),carDao.first().getId())
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().is(200))
				.andExpect(jsonPath("$").isArray())
				.andReturn();
	}
	

	

	
	@After
	public void setdown(){
//		for(Account account : dao.findAll()){
//			if(account.getName() == null || account.getName().equals("testa1"))
//				dao.delete(account);
//				
//		}
	}

	
}
