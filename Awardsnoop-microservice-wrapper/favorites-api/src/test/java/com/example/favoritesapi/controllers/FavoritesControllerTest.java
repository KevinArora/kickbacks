package com.example.favoritesapi.controllers;

import com.example.favoritesapi.models.Favorite;
import com.example.favoritesapi.repositories.FavRepo;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;


import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;

import static org.mockito.BDDMockito.given;

@RunWith(SpringRunner.class)
@WebMvcTest(FavoritesController.class)
public class FavoritesControllerTest {
@Autowired
    private MockMvc mockMvc;

    private Favorite newFav;

    private Favorite updatedSecondFav;


    @Autowired
    private ObjectMapper jsonObjectMapper;

    @MockBean
    private FavRepo mockFavRepo;

    @Before
    public void setUp(){
        Favorite firstFav = new Favorite(
                "muh first fav",
                "muh first description"
        );
        Favorite secondFav = new Favorite(
                "my second favorite",
                "my second description"
        );

        newFav = new Favorite(
                "my new favorite kinda",
                "my new description maybe"
        );

        given(mockFavRepo.save(newFav)).willReturn(newFav);

        updatedSecondFav = new Favorite(
                "my updated second favorite",
                "my editted second description"
        );

        given(mockFavRepo.save(updatedSecondFav)).willReturn(updatedSecondFav);

        Iterable<Favorite> mockFavs =
                Stream.of(firstFav,secondFav).collect(Collectors.toList());

        given(mockFavRepo.findAll()).willReturn(mockFavs);
        given(mockFavRepo.findOne(1L)).willReturn(firstFav);
        given(mockFavRepo.findOne(4L)).willReturn(null);
        doAnswer(invocation -> {
            throw new EmptyResultDataAccessException("error from mock", 1234);
        }).when(mockFavRepo).delete(4L);

    }

    @Test
    public void findAllFavs_success_returnsStatusOK() throws Exception {
        this.mockMvc
                .perform(get("/"))
                .andExpect(status().isOk());
    }
    @Test
    public void findAllFavs_success_returnAllFavsAsJSON() throws Exception{
        this.mockMvc
                .perform(get("/"))
                .andExpect(jsonPath("$", hasSize(2)));
    }
    @Test
    public void findAllFavs_success_returnFavTitleForEachFav() throws Exception {
        this.mockMvc
                .perform(get("/"))
                .andExpect(jsonPath("$[0].title", is("muh first fav")));
    }
    @Test
    public void findAllFavs_success_returnFavDescriptionForEachFav() throws Exception {
       this.mockMvc
                .perform(get("/"))
                .andExpect(jsonPath("$[0].description", is("muh first description")));
    }
    @Test
    public void findFavbyID_success_returnsStatusOK() throws Exception {
        this.mockMvc
                .perform(get("/1"))
                .andExpect(status().isOk());
    }
    @Test
    public void findFavByID_success_returnTitle() throws Exception {
        this.mockMvc
                .perform(get("/1"))
                .andExpect(jsonPath("$.title", is("muh first fav")));
    }
    @Test
    public void findFavByID_success_returnDescription() throws Exception {
        this.mockMvc
                .perform(get("/1"))
                .andExpect(jsonPath("$.description", is("muh first description")));
    }
    @Test
    public void findFavByID_failure_favNotFoundReturns404() throws Exception {
        this.mockMvc
                .perform(get("/4"))
                .andExpect(status().reason(containsString("Favorite with ID 4 not found")));
    }

    @Test
    public void deleteFavByID_success_returnsStatusOk() throws Exception {

        this.mockMvc
                .perform(delete("/1"))
                .andExpect(status().isOk());
    }
    @Test
    public void deleteFavByID_success_deletesViaRepository() throws Exception {

        this.mockMvc.perform(delete("/1"));

        verify(mockFavRepo, times(1)).delete(1L);
    }
    @Test
    public void deleteFavByID_failure_userNotFoundReturns404() throws Exception {

        this.mockMvc
                .perform(delete("/4"))
                .andExpect(status().isNotFound());
    }
    @Test
    public void createFav_success_returnsStatusOk() throws Exception {

        this.mockMvc
                .perform(
                        post("/")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(jsonObjectMapper.writeValueAsString(newFav))
                )
                .andExpect(status().isOk());
    }
    @Test
    public void createFav_success_returnsTitle() throws Exception {

        this.mockMvc
                .perform(
                        post("/")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(jsonObjectMapper.writeValueAsString(newFav))
                )
                .andExpect(jsonPath("$.title", is("my new favorite kinda")));
    }
    @Test
    public void createFav_success_returnsDescription() throws Exception {
        this.mockMvc
                .perform(
                        post("/")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(jsonObjectMapper.writeValueAsString(newFav))
                )
                .andExpect(jsonPath("$.description", is("my new description maybe")));
    }
    @Test
    public void updateFavById_success_returnsStatusOk() throws Exception {

        this.mockMvc
                .perform(
                        patch("/1")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(jsonObjectMapper.writeValueAsString(updatedSecondFav))
                )
                .andExpect(status().isOk());
    }
    @Test
    public void updateFavByID_success_returnsUpdatedTitle()throws Exception{
        this.mockMvc
                .perform(
                        patch("/1")
                        .contentType(MediaType.APPLICATION_JSON)
                                .content(jsonObjectMapper.writeValueAsString(updatedSecondFav))
                )
                .andExpect(jsonPath("$.title", is("my updated second favorite")));
    }
    @Test
    public void updateFavById_success_returnsUpdatedDescription() throws Exception {

        this.mockMvc
                .perform(
                        patch("/1")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(jsonObjectMapper.writeValueAsString(updatedSecondFav))
                )
                .andExpect(jsonPath("$.description", is("my editted second description")));
    }
    @Test
    public void updateFavById_failure_favNotFoundReturns404() throws Exception {

        this.mockMvc
                .perform(
                        patch("/4")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(jsonObjectMapper.writeValueAsString(updatedSecondFav))
                )
                .andExpect(status().isNotFound());
    }

    @Test
    public void updateFavById_failure_favNotFoundReturnsNotFoundErrorMessage() throws Exception {

        this.mockMvc
                .perform(
                        patch("/4")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(jsonObjectMapper.writeValueAsString(updatedSecondFav))
                )
                .andExpect(status().reason(containsString("Fav with ID of 4 was not found!")));
    }



}
