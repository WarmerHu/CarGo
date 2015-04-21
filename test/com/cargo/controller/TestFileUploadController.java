package com.cargo.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:database-servlet.xml")
public class TestFileUploadController extends AbstractJUnit4SpringContextTests{

	private MockMvc mocMvc;
	
	@Autowired
	private FileUploadController controller;
	
	@Before
	public void setup(){
		mocMvc = MockMvcBuilders.standaloneSetup(controller).build();
	}
	
	@Test
    public void test() throws Exception {

        MockMultipartFile firstFile = new MockMultipartFile("data", "filename.txt", "text/plain", "some xml".getBytes());

        mocMvc.perform(MockMvcRequestBuilders.fileUpload("/upload")
                        .file(firstFile))
                        .andDo(MockMvcResultHandlers.print())
                        .andExpect(status().is(200))
                        .andExpect(jsonPath("$.url").exists());
    }
}
