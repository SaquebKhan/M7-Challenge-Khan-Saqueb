package com.company.musicstorecatalog.controller;

import static org.junit.Assert.*;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.company.musicstorecatalog.model.Track;
import com.company.musicstorecatalog.repository.TrackRepository;
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
@WebMvcTest(TrackController.class)
@AutoConfigureMockMvc(addFilters = false)
public class TrackControllerTest {
    @MockBean
    private TrackRepository repo;

    @Autowired
    private ObjectMapper mapper;


    @Autowired
    MockMvc mockMvc;

    //        @Before
//    public void setUp()throws Exception{
//        setUpProduceServiceMock();
//    }
    public void setUpProduceServiceMock(){
        Track orange =new Track(111,1,"orangey", 2);
        Track orangeWithoutId =new Track(1,"orangey", 2);
        List<Track> albumList= Arrays.asList(orange);
        doReturn(albumList).when(repo).findAll();
        doReturn(orange).when(repo).save(orangeWithoutId);

    }
    @Test
    public void getAllTracksShouldReturnListAnd200()throws Exception{
        Track orange =new Track(111,1,"orangey", 2);
        List<Track> trackList= Arrays.asList(orange);
        String expectedJsonValue =mapper.writeValueAsString(trackList);
        doReturn(trackList).when(repo).findAll();
        mockMvc.perform(MockMvcRequestBuilders.get("/track"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(expectedJsonValue));

    }
    @Test
    public void shouldReturnNewTrackOnPostRequest() throws Exception{

        //Arrange
        Track inTrack = new Track();

        inTrack.setTitle("Ready to Die");
        inTrack.setAlbumId(1);
        inTrack.setRunTime(200);

        //
        Track outTrack = new Track();
        inTrack.setId(1);
        inTrack.setTitle("Ready to Die");
        inTrack.setAlbumId(1);
        inTrack.setRunTime(200);

        String inputJson = mapper.writeValueAsString(inTrack);
        String outputJson = mapper.writeValueAsString(outTrack);
        //act
        when(repo.save(inTrack)).thenReturn(outTrack);

        mockMvc.perform(post("/track")
                        .content(inputJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().json(outputJson));
    }

    @Test
    public void createTrackShouldReturnNewLabel()throws Exception{
        Track outputTrack=new Track(1,1,"orangey", 2);
        Track inputTrack= new Track(1,"orangey", 2);
        String outputTrackJson=mapper.writeValueAsString(outputTrack);
        String inputTrackJson = mapper.writeValueAsString(inputTrack);
        when(repo.save(inputTrack)).thenReturn(outputTrack);
        //doReturn(outputTrack).when(repo).save(inputTrack);
        mockMvc.perform(post("/track")
                        .content(inputTrackJson)
                        .contentType(MediaType.APPLICATION_JSON))

                .andDo(print())
                .andExpect(status().isCreated())            // Assert
                .andExpect(content().json(outputTrackJson));  // Assert
    }
    @Test
    public void getOneArtistShouldReturn()throws Exception{
        Track artist=new Track(111,1,"orangey", 2);
        String expectedJsonValue=mapper.writeValueAsString(artist);

        doReturn(Optional.of(artist)).when(repo).findById(111);

        ResultActions result = mockMvc.perform(
                        MockMvcRequestBuilders.get("/track/111"))
                .andExpect(status().isOk())
                .andExpect((content().json(expectedJsonValue))
                );
    };


    @Test
    public void shouldUpdateByIdAndReturn200StatusCode() throws Exception {
        Track artist = new Track( 111,1,"orangey", 2);
        //Artist expectedValue =new Artist("orangey", "orange","orangeorange");
        String expectedJsonValue=mapper.writeValueAsString(artist);
        mockMvc.perform(
                        put("/track/111")
                                .content(expectedJsonValue)
                                .contentType(MediaType.APPLICATION_JSON)

                )
                .andExpect(status().isOk());

    }
    @Test
    public void shouldDeleteByIdAndReturn200StatusCode() throws Exception {
        Track artist = new Track( 111,1,"orangey", 2);
        mockMvc.perform(MockMvcRequestBuilders.delete("/track/1")).andExpect(status().isNoContent());
    }



}
