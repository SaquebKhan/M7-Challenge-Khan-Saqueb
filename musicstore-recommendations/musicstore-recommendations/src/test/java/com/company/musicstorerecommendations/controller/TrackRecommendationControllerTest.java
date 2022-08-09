package com.company.musicstorerecommendations.controller;

import static org.junit.Assert.*;


import com.company.musicstorerecommendations.model.TrackRecommendation;
import com.company.musicstorerecommendations.repository.TrackRecommendationRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
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
@WebMvcTest(TrackRecommendationController.class)
@AutoConfigureMockMvc(addFilters = false)
public class TrackRecommendationControllerTest {
    @MockBean
    private TrackRecommendationRepository repo;

    @Autowired
    private ObjectMapper mapper;


    @Autowired
    MockMvc mockMvc;
    public void setUpProduceServiceMock(){
        TrackRecommendation halo =new TrackRecommendation(525,1,2,true);
        TrackRecommendation haloWithoutId =new TrackRecommendation(1,2,true);
        List<TrackRecommendation> albumList= Arrays.asList(halo);
        doReturn(albumList).when(repo).findAll();
        doReturn(halo).when(repo).save(haloWithoutId);

    }
    @Test
    public void getAllTrackRecommendationsShouldReturnListAnd200()throws Exception{
        TrackRecommendation halo =new TrackRecommendation(525,1,2,true);
        List<TrackRecommendation> trackRecommendationList= Arrays.asList(halo);
        String expectedJsonValue =mapper.writeValueAsString(trackRecommendationList);
        doReturn(trackRecommendationList).when(repo).findAll();
        mockMvc.perform(MockMvcRequestBuilders.get("/trackRecommendation"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(expectedJsonValue));

    }
    @Test
    public void shouldReturnNewTrackRecommendationOnPostRequest() throws Exception{

        TrackRecommendation inTrackRecommendation = new TrackRecommendation(525,1,2,true);
        TrackRecommendation outTrackRecommendation = new TrackRecommendation(525,1,2,true);
        inTrackRecommendation.setId(1);
        String inputJson = mapper.writeValueAsString(inTrackRecommendation);
        String outputJson = mapper.writeValueAsString(outTrackRecommendation);
        when(repo.save(inTrackRecommendation)).thenReturn(outTrackRecommendation);

        mockMvc.perform(post("/trackRecommendation")
                        .content(inputJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().json(outputJson));
    }

    @Test
    public void createTrackRecommendationShouldReturnNewLabel()throws Exception{
        TrackRecommendation outputTrackRecommendation=new TrackRecommendation(525,1,2,true);
        TrackRecommendation inputTrackRecommendation= new TrackRecommendation(525,1,2,true);
        String outputTrackRecommendationJson=mapper.writeValueAsString(outputTrackRecommendation);
        String inputTrackRecommendationJson = mapper.writeValueAsString(inputTrackRecommendation);
        when(repo.save(inputTrackRecommendation)).thenReturn(outputTrackRecommendation);
        //doReturn(outputTrackRecommendation).when(repo).save(inputTrackRecommendation);
        mockMvc.perform(post("/trackRecommendation")
                        .content(inputTrackRecommendationJson)
                        .contentType(MediaType.APPLICATION_JSON))

                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().json(outputTrackRecommendationJson));
    }
    @Test
    public void getOneArtistShouldReturn()throws Exception{
        TrackRecommendation artist=new TrackRecommendation(525,1,2,true);
        String expectedJsonValue=mapper.writeValueAsString(artist);

        doReturn(Optional.of(artist)).when(repo).findById(525);

        ResultActions result = mockMvc.perform(
                        MockMvcRequestBuilders.get("/trackRecommendation/525"))
                .andExpect(status().isOk())
                .andExpect((content().json(expectedJsonValue))
                );
    };


    @Test
    public void shouldUpdateByIdAndReturn200StatusCode() throws Exception {
        TrackRecommendation artist = new TrackRecommendation(1,2,true);
        String expectedJsonValue=mapper.writeValueAsString(artist);
        mockMvc.perform(
                        put("/trackRecommendation/525")
                                .content(expectedJsonValue)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk());
    }
    @Test
    public void shouldDeleteByIdAndReturn200StatusCode() throws Exception {
        TrackRecommendation artist = new TrackRecommendation(525,1,2,true);
        mockMvc.perform(MockMvcRequestBuilders.delete("/trackRecommendation/525")).andExpect(status().isNoContent());
    }



}
