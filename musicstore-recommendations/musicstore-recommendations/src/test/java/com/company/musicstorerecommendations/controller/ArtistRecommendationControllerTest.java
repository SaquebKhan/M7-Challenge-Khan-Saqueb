package com.company.musicstorerecommendations.controller;

import com.company.musicstorerecommendations.model.ArtistRecommendation;
import com.company.musicstorerecommendations.repository.ArtistRecommendationRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(ArtistRecommendationController.class)
public class ArtistRecommendationControllerTest {
    @MockBean
    private ArtistRecommendationRepository repo;

    private ObjectMapper mapper =new ObjectMapper();

    @Autowired
    MockMvc mockMvc;

    @Before
    public void setUp()throws Exception{
        setUpProduceServiceMock();
    }
    public void setUpProduceServiceMock(){
        ArtistRecommendation naruto =new ArtistRecommendation(525,1,2,true);
        ArtistRecommendation narutoWithoutId =new ArtistRecommendation(1,2,true);
        List<ArtistRecommendation> labelList= Arrays.asList(naruto);
        doReturn(labelList).when(repo).findAll();
        doReturn(naruto).when(repo).save(narutoWithoutId);

    }
    @Test
    public void getOneArtistRecommendationShouldReturn()throws Exception{
        ArtistRecommendation artist=new ArtistRecommendation(525,1,2,true);
        String expectedJsonValue=mapper.writeValueAsString(artist);

        doReturn(Optional.of(artist)).when(repo).findById(525);

        ResultActions result = mockMvc.perform(
                        get("/artistRecommendation/525"))
                .andExpect(status().isOk())
                .andExpect((content().json(expectedJsonValue))
                );
    };


    @Test
    public void shouldUpdateByIdAndReturn200StatusCode() throws Exception {
        ArtistRecommendation artistRecommendation = new ArtistRecommendation(1,2,true);
        String expectedJsonValue=mapper.writeValueAsString(artistRecommendation);
        mockMvc.perform(
                        put("/artistRecommendation/525")
                                .content(expectedJsonValue)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk());

    }
    @Test
    public void shouldDeleteByIdAndReturn200StatusCode() throws Exception {
        ArtistRecommendation artist = new ArtistRecommendation(1,2,true);
        mockMvc.perform(delete("/artistRecommendation/1")).andExpect(status().isNoContent());
    }


}
