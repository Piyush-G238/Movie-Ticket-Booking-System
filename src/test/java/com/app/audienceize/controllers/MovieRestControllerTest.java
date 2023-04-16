package com.app.audienceize.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.junit.jupiter.api.Assertions.*;

@WebMvcTest(MovieRestController.class)
class MovieRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void movieTestEndPointTest() throws Exception {
        mockMvc
                .perform(MockMvcRequestBuilders.get("/api/v1/movies/test"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("GET request on /test endpoint works"));
    }
}