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
import com.cargo.model.Account;
import com.cargo.model.Account.Gender;
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
		account.setName("testa2");
		account.setPassword("testa1");
		account.setEmail("testa1@test.com");
		account.setAddress("testa1");
		account.setCity("testa1");
		account.setGender(Gender.Lady);
		account.setTelephone("10000000001");
		account.setType(ProfileType.Buyer);
		dao.create(account);
	}
	
	@Test
	public void saveAccount() throws Exception{
		String requestBody="{\"name\":\"testa1\"," +
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
				.andExpect(jsonPath("$.name").value("testa1"))
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
		String requestbody = "{\"name\":\"testa2\",\"password\":\"testa1\"}";
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
//		dao.deleteAll();
	}

	
}
