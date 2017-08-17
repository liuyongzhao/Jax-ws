package com.example.demo;

import static org.junit.Assert.*;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;


@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration 
public class country {
	
	@Autowired
    private WebApplicationContext webApplicationContext;
	private MockMvc mockMvc;
	
    @Before
    public void setUp() throws Exception {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }
   
	@Test
	public void rightResponse() throws Exception {
        String queryResult = 
        		mockMvc.perform(MockMvcRequestBuilders.get("http://localhost:8080/ws/countries.wsdl"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("/")))
                .andReturn().getResponse().getContentAsString();
        		System.out.println("----------**----------\n" + queryResult);
	}
	
	
	@Test
	public void abnormalResponse() {
		fail("Not yet implemented");
		
	}
}
