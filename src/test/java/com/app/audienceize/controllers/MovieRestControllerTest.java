package com.app.audienceize.controllers;

import com.app.audienceize.dtos.requests.MovieRequest;
import com.app.audienceize.enums.Genre;
import com.app.audienceize.services.MovieService;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.junit.jupiter.api.Assertions.*;

@WebMvcTest(MovieRestController.class)
class MovieRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MovieService service;

    private MovieRequest req;

    @BeforeEach
    void setUp() {
        req = MovieRequest.builder()
                .title("Spider-man")
                .genre(Genre.COMEDY)
                .releasedOn("2022-12-12")
                .build();
    }
    @Test
    public void movieTestEndPointTest() throws Exception {
        mockMvc
                .perform(MockMvcRequestBuilders.get("/api/v1/movies/test"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("GET request on /test endpoint works"));
    }
    @Test
    public void addMovieEndPointTest() throws Exception {
        Mockito.when(service.addMovie(req)).thenReturn(req.getTitle()+" is added.");

        mockMvc
                .perform(MockMvcRequestBuilders.post("/api/v1/movies")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJSONString(req)))
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    private String toJSONString(MovieRequest req) throws JSONException {
        JSONObject object = new JSONObject();
        object.put("title", req.getTitle());
        object.put("genre", req.getGenre());
        object.put("releasedOn", req.getReleasedOn());
        return object.toString();
    }
}