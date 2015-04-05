package com.cargo.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
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
import com.cargo.model.Account;
import com.cargo.model.Account.ProfileType;


/**
 * 
 * 示例网址	http://jinnianshilongnian.iteye.com/blog/2004660
 * @author Wealong
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:database-servlet.xml")
public class TestAccountController extends AbstractJUnit4SpringContextTests{

	private MockMvc mocMvc;
	
	@Autowired
	private AccountController accountController;
	
	@Autowired
	private IAccountDao dao;
	
	@Before
	public void setup(){
		mocMvc = MockMvcBuilders.standaloneSetup(accountController).build();
		Account account = new Account();
		account.setName("test");
		account.setPassword("test");
		account.setEmail("test@test.com");
		account.setGender("boy");
		account.setTelephone("13456780123");
		account.setType(ProfileType.Buyer);
		dao.create(account);
	}
	
	@Test
	public void saveAccount() throws Exception{
		String requestBody="{\"name\":\"test\"," +
							"\"password\":\"test\"," +
							"\"email\":\"test@test.com\"," +
							"\"gender\":\"boy\"," +
							"\"password\":\"test\"," +
							"\"telephone\":\"13456780123\"," +
							"\"profileType\":\"Buyer\"}";
		
		mocMvc.perform(post("/accounts")
				.contentType(MediaType.APPLICATION_JSON)
				.content(requestBody))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.name").value("test"))
				.andExpect(jsonPath("$.email").value("test@test.com"))
				.andExpect(jsonPath("$.gender").value("boy"))
				.andExpect(jsonPath("$.telephone").value("13456780123"))
				.andExpect(jsonPath("$.profileType").value("Buyer"))
				.andReturn();
	}
	
	@Test
	public void getAccount() throws Exception{
		Account account = dao.findAll().get(0);
		mocMvc.perform(get("/accounts/{id}",account.getId())
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.name").value(account.getName()))
				.andExpect(jsonPath("$.email").value(account.getEmail()))
				.andExpect(jsonPath("$.gender").value(account.getGender()))
				.andExpect(jsonPath("$.telephone").value(account.getTelephone()))
				.andExpect(jsonPath("$.profileType").value(account.getType().name()))
				.andReturn();
	}
	
	@Test
	public void updateAccount() throws Exception{
		Account account = dao.findAll().get(0);
		String content = "{\"telephone\":\"123456789\" }";
		mocMvc.perform(patch("/accounts/{id}",account.getId())
				.contentType(MediaType.APPLICATION_JSON)
				.content(content))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.telephone").value("123456789"))
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
	public void deleteAccount() throws Exception{
		List<Account> accounts = dao.findAll();
		Account account = accounts.get(accounts.size() - 1);
		mocMvc.perform(delete("/accounts/{id}",account.getId())
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNoContent())
				.andReturn();
		Assert.isNull(dao.find(account.getId()));
	}
	
	@Test
	public void login() throws Exception{
		String requestbody = "{\"name\":\"test\",\"password\":\"test\"}";
		mocMvc.perform(post("/accounts/login")
				.contentType(MediaType.APPLICATION_JSON)
				.content(requestbody))
				.andExpect(jsonPath("$.auth_token").exists())
				.andReturn();
	}
	
	@After
	public void setdown(){
		for(Account account : dao.findAll()){
			if(account.getName() == null || account.getName().equals("test"))
				dao.delete(account);
		}
	}

	
}
