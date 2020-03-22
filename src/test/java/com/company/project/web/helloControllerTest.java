package com.company.project.web;



import com.conpany.project.Tester;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.http.Cookie;

import static org.junit.Assert.*;

public class helloControllerTest extends Tester {

    @Autowired
    private helloController helloController;

    @Autowired
    private WebApplicationContext wac;

    private MockMvc mvc;

    private MockHttpSession session;

    private Cookie cookie;

    @Before
    public void setupMockMVc(){
        mvc = MockMvcBuilders.webAppContextSetup(wac).build();
        session = new MockHttpSession();
        cookie = new Cookie("token","808eacc23b0336e5ffd68750af6d6743");
    }

    @Test
    public void login() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/hello/test").cookie(cookie))
                .andDo(MockMvcResultHandlers.print());

    }
}
