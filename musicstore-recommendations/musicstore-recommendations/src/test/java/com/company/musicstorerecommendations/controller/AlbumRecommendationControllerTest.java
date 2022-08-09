package com.company.musicstorerecommendations.controller;

import static org.junit.Assert.*;


import com.company.musicstorerecommendations.model.AlbumRecommendation;
import com.company.musicstorerecommendations.repository.AlbumRecommendationRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;

import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@WebMvcTest(AlbumRecommendationController.class)
@AutoConfigureMockMvc(addFilters = false)
public class AlbumRecommendationControllerTest {
    @MockBean
    private AlbumRecommendationRepository repo;

    @Autowired
    private ObjectMapper mapper;


    @Autowired
    MockMvc mockMvc;
    public void setUpProduceServiceMock(){
        AlbumRecommendation albumOne =new AlbumRecommendation(525,1,2,true);
        AlbumRecommendation albumOneWithoutId =new AlbumRecommendation(1,2,true);
        List<AlbumRecommendation> albumRecommendationList= Arrays.asList(albumOne);
        doReturn(albumRecommendationList).when(repo).findAll();
        doReturn(albumOne).when(repo).save(albumOneWithoutId);

    }
    @Test
    public void getAllAlbumRecommendationsShouldReturnListAnd200()throws Exception{
        AlbumRecommendation BattleField =new AlbumRecommendation(1,2,true);
        List<AlbumRecommendation> albumRecommendationList= Arrays.asList(BattleField);
        String expectedJsonValue =mapper.writeValueAsString(albumRecommendationList);
        doReturn(albumRecommendationList).when(repo).findAll();
        mockMvc.perform(MockMvcRequestBuilders.get("/albumRecommendation"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(expectedJsonValue));

    }

    @Test
    public void createAlbumRecommendationShouldReturnNewLabel()throws Exception{
        AlbumRecommendation outputAlbumRecommendation=new AlbumRecommendation(525,1,2,true);
        AlbumRecommendation inputAlbumRecommendation= new AlbumRecommendation(1,2,true);
        String outputAlbumRecommendationJson=mapper.writeValueAsString(outputAlbumRecommendation);
        String inputAlbumRecommendationJson = mapper.writeValueAsString(inputAlbumRecommendation);
        when(repo.save(inputAlbumRecommendation)).thenReturn(outputAlbumRecommendation);

        mockMvc.perform(MockMvcRequestBuilders.post("/albumRecommendation")
                        .content(inputAlbumRecommendationJson)
                        .contentType(MediaType.APPLICATION_JSON))

                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().json(outputAlbumRecommendationJson));
    }
    @Test
    public void getOneAlbumShouldReturn()throws Exception{
        AlbumRecommendation album=new AlbumRecommendation(525,1,2,true);
        String expectedJsonValue=mapper.writeValueAsString(album);

        doReturn(Optional.of(album)).when(repo).findById(525);

        ResultActions result = mockMvc.perform(
                        MockMvcRequestBuilders.get("/albumRecommendation/525"))
                .andExpect(status().isOk())
                .andExpect((content().json(expectedJsonValue))
                );
    };


    @Test
    public void shouldUpdateByIdAndReturn200StatusCode() throws Exception {
        AlbumRecommendation albumRecommendation = new AlbumRecommendation(1,2,true);
        String expectedJsonValue=mapper.writeValueAsString(albumRecommendation);
        mockMvc.perform(
                        put("/albumRecommendation/525")
                                .content(expectedJsonValue)
                                .contentType(MediaType.APPLICATION_JSON)

                )
                .andExpect(status().isOk());

    }
    @Test
    public void shouldDeleteByIdAndReturn200StatusCode() throws Exception {
        AlbumRecommendation artist = new AlbumRecommendation(525,1,2,true);
        mockMvc.perform(MockMvcRequestBuilders.delete("/albumRecommendation/1")).andExpect(status().isNoContent());
    }



}