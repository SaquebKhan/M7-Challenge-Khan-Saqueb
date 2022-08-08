package com.company.musicstorecatalog.controller;

import com.company.musicstorecatalog.repository.ArtistRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.company.musicstorecatalog.model.Artist;

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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(ArtistController.class)
public class ArtistControllerTest {
    @MockBean
    private ArtistRepository repo;

    private ObjectMapper mapper =new ObjectMapper();

    @Autowired
    MockMvc mockMvc;

    @Before
    public void setUp()throws Exception{
        setUpProduceServiceMock();
    }
    public void setUpProduceServiceMock(){
        Artist naruto =new Artist(14,"naruto", "naruto","@naruto");
        Artist narutoWithoutId =new Artist("naruto", "naruto","@naruto");
        List<Artist> labelList= Arrays.asList(naruto);
        doReturn(labelList).when(repo).findAll();
        doReturn(naruto).when(repo).save(narutoWithoutId);

    }
    @Test
    public void getOneArtistShouldReturn()throws Exception{
        Artist artist=new Artist(447,"Mr.Test", "MTest","@Mrtest");
        String expectedJsonValue=mapper.writeValueAsString(artist);



        doReturn(Optional.of(artist)).when(repo).findById(447);

        ResultActions result = mockMvc.perform(
                        get("/artist/447"))
                .andExpect(status().isOk())
                .andExpect((content().json(expectedJsonValue))
                );
    };


    @Test
    public void shouldUpdateByIdAndReturn200StatusCode() throws Exception {
        Artist artist = new Artist( 14,"naruto", "naruto","@naruto");
        String expectedJsonValue=mapper.writeValueAsString(artist);
        mockMvc.perform(
                        put("/artist/14")
                                .content(expectedJsonValue)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk());

    }
    @Test
    public void shouldDeleteByIdAndReturn200StatusCode() throws Exception {
        Artist artist = new Artist( 1,"Last Test", "LastTest","@LastTest");
        mockMvc.perform(delete("/artist/1")).andExpect(status().isNoContent());
    }



}
