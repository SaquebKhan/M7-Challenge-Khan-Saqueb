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
        Artist orange =new Artist(111,"Breaking Benjamin", "orange","orangeorange");
        Artist orangeWithoutId =new Artist("orangey", "orange","orangeorange");
        List<Artist> labelList= Arrays.asList(orange);
        doReturn(labelList).when(repo).findAll();
        doReturn(orange).when(repo).save(orangeWithoutId);

    }
    @Test
    public void getAllArtistShouldReturnListAnd200()throws Exception{
        Artist orange =new Artist(111,"orangey", "orange","orangeorange");
        List<Artist> artistList= Arrays.asList(orange);
        String expectedJsonValue =mapper.writeValueAsString(artistList);
        mockMvc.perform(get("/artist"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(expectedJsonValue));

    }
    @Test
    public void createArtistShouldReturnNewLabel()throws Exception{
        Artist outputArtist=new Artist(111,"orangey", "orange","orangeorange");
        Artist inputArtist= new Artist("orangey", "orange","orangeorange");
        String outputArtistJson=mapper.writeValueAsString(outputArtist);
        String inputArtistJson = mapper.writeValueAsString(inputArtist);

        mockMvc.perform(post("/artist")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(inputArtistJson))
                .andDo(print())
                .andExpect(status().isCreated())            // Assert
                .andExpect(content().json(outputArtistJson));  // Assert
    }
    @Test
    public void getOneArtistShouldReturn()throws Exception{
        Artist artist=new Artist(111,"orangey", "orange","orangeorange");
        String expectedJsonValue=mapper.writeValueAsString(artist);



        doReturn(Optional.of(artist)).when(repo).findById(111);

        ResultActions result = mockMvc.perform(
                        get("/artist/111"))
                .andExpect(status().isOk())
                .andExpect((content().json(expectedJsonValue))
                );
    };


    @Test
    public void shouldUpdateByIdAndReturn200StatusCode() throws Exception {
        Artist artist = new Artist( 111,"orangey", "orange","orangeorange");
        //Artist expectedValue =new Artist("orangey", "orange","orangeorange");
        String expectedJsonValue=mapper.writeValueAsString(artist);
        mockMvc.perform(
                        put("/artist/111")
                                .content(expectedJsonValue)
                                .contentType(MediaType.APPLICATION_JSON)

                )
                .andExpect(status().isOk());

    }
    @Test
    public void shouldDeleteByIdAndReturn200StatusCode() throws Exception {
        Artist artist = new Artist( 1,"orangey", "orange","orangeorange");
        mockMvc.perform(delete("/artist/1")).andExpect(status().isNoContent());
    }



}
