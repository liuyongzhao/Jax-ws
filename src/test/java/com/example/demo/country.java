package com.example.demo;

import static org.junit.Assert.*;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import io.spring.guides.gs_producing_web_service.GetCountryRequest;


@RunWith(SpringJUnit4ClassRunner.class)

public class country {
	
	

	/*@Mock*/
	private MockMvc mockMvc;
	/*private static GetCountryRequest getCountryRequest = new GetCountryRequest();*/
	@Autowired
    private WebApplicationContext webApplicationContext;
    
	/*@Test
    public void wrongResponse() {
    	getCountryRequest.getName();
		assertEquals(5,getCountryRequest.getName());
		
	}*/
    @Before
    public void setUp() throws Exception {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }
   
	/*@Test
	public void rightResponse() throws Exception {
        String queryResult = 
        		mockMvc.perform(MockMvcRequestBuilders.get("/ws"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("/")))
                .andReturn().getResponse().getContentAsString();
        System.out.println("----------**----------\n" + queryResult);
	}*/
	
	
	@Test
	public void abnormalResponse() {
		fail("Not yet implemented");
		
	}
}
