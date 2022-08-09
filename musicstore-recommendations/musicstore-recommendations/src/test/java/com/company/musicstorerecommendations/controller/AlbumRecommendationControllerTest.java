package com.company.musicstorerecommendations.controller;

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

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;
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
        AlbumRecommendation orange =new AlbumRecommendation(117,"This is the one", 1, LocalDate.ofEpochDay(2009-21-25),1, BigDecimal.valueOf(99.99));
        AlbumRecommendation orangeWithoutId =new AlbumRecommendation("This is the one", 1, LocalDate.ofEpochDay(2009-21-25),1,BigDecimal.valueOf(99.99));
        List<AlbumRecommendation> albumRecommendationList= Arrays.asList(orange);
        doReturn(albumRecommendationList).when(repo).findAll();
        doReturn(orange).when(repo).save(orangeWithoutId);

    }
    @Test
    public void getAllAlbumRecommendationsShouldReturnListAnd200()throws Exception{
        AlbumRecommendation orange =new AlbumRecommendation(111,"orangey", 1, LocalDate.ofEpochDay(1999-10-13),1,BigDecimal.valueOf(10.99));
        List<AlbumRecommendation> albumRecommendationList= Arrays.asList(orange);
        String expectedJsonValue =mapper.writeValueAsString(albumRecommendationList);
        doReturn(albumRecommendationList).when(repo).findAll();
        mockMvc.perform(MockMvcRequestBuilders.get("/albumRecommendation"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(expectedJsonValue));

    }

    @Test
    public void createAlbumRecommendationShouldReturnNewLabel()throws Exception{
        AlbumRecommendation outputAlbumRecommendation=new AlbumRecommendation(111,"orangey", 1,LocalDate.of(1999,10,13),1,BigDecimal.valueOf(10.99));
        AlbumRecommendation inputAlbumRecommendation= new AlbumRecommendation("orangey", 1, LocalDate.of(1999,10,13),1,BigDecimal.valueOf(10.99));
        String outputAlbumRecommendationJson=mapper.writeValueAsString(outputAlbumRecommendation);
        String inputAlbumRecommendationJson = mapper.writeValueAsString(inputAlbumRecommendation);
        when(repo.save(inputAlbumRecommendation)).thenReturn(outputAlbumRecommendation);

        mockMvc.perform(MockMvcRequestBuilders.post("/albumRecommendation")
                        .content(inputAlbumRecommendationJson)
                        .contentType(MediaType.APPLICATION_JSON))

                .andDo(print())
                .andExpect(status().isCreated())            // Assert
                .andExpect(content().json(outputAlbumRecommendationJson));  // Assert
    }
    @Test
    public void getOneArtistShouldReturn()throws Exception{
        AlbumRecommendation artist=new AlbumRecommendation(111,"orangey", 1, LocalDate.of(1999,10,13),1,BigDecimal.valueOf(10.99));
        String expectedJsonValue=mapper.writeValueAsString(artist);

        doReturn(Optional.of(artist)).when(repo).findById(111);

        ResultActions result = mockMvc.perform(
                        MockMvcRequestBuilders.get("/albumRecommendation/111"))
                .andExpect(status().isOk())
                .andExpect((content().json(expectedJsonValue))
                );
    };


    @Test
    public void shouldUpdateByIdAndReturn200StatusCode() throws Exception {
        AlbumRecommendation artist = new AlbumRecommendation( 111,"orangey", 1, LocalDate.ofEpochDay(1999-10-13),1,BigDecimal.valueOf(10.99));
        //Artist expectedValue =new Artist("orangey", "orange","orangeorange");
        String expectedJsonValue=mapper.writeValueAsString(artist);
        mockMvc.perform(
                        put("/albumRecommendation/111")
                                .content(expectedJsonValue)
                                .contentType(MediaType.APPLICATION_JSON)

                )
                .andExpect(status().isOk());

    }
    @Test
    public void shouldDeleteByIdAndReturn200StatusCode() throws Exception {
        AlbumRecommendation artist = new AlbumRecommendation( 1,"orangey", 1,LocalDate.ofEpochDay(1999-10-13),1,BigDecimal.valueOf(10.99));
        mockMvc.perform(MockMvcRequestBuilders.delete("/albumRecommendation/1")).andExpect(status().isNoContent());
    }



}
