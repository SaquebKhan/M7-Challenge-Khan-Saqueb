package com.company.musicstorecatalog.controller;

import static org.junit.Assert.*;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.company.musicstorecatalog.model.Album;
import com.company.musicstorecatalog.repository.AlbumRepository;
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
@WebMvcTest(AlbumController.class)
@AutoConfigureMockMvc(addFilters = false)
public class AlbumControllerTest {
    @MockBean
    private AlbumRepository repo;

    @Autowired
    private ObjectMapper mapper;


    @Autowired
    MockMvc mockMvc;
    public void setUpProduceServiceMock(){
        Album orange =new Album(117,"This is the one", 1, LocalDate.ofEpochDay(2009-21-25),1,BigDecimal.valueOf(99.99));
        Album orangeWithoutId =new Album("This is the one", 1, LocalDate.ofEpochDay(2009-21-25),1,BigDecimal.valueOf(99.99));
        List<Album> albumList= Arrays.asList(orange);
        doReturn(albumList).when(repo).findAll();
        doReturn(orange).when(repo).save(orangeWithoutId);

    }
    @Test
    public void getAllAlbumsShouldReturnListAnd200()throws Exception{
        Album BattleField =new Album(216,"BattleField", 1, LocalDate.ofEpochDay(2016-11-20),1,BigDecimal.valueOf(10.99));
        List<Album> albumList= Arrays.asList(BattleField);
        String expectedJsonValue =mapper.writeValueAsString(albumList);
        doReturn(albumList).when(repo).findAll();
        mockMvc.perform(MockMvcRequestBuilders.get("/album"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(expectedJsonValue));

    }

    @Test
    public void createAlbumShouldReturnNewLabel()throws Exception{
        Album outputAlbum=new Album(616,"Naruto", 1,LocalDate.of(2001,11,12),1,BigDecimal.valueOf(19.99));
        Album inputAlbum= new Album("Halo", 1, LocalDate.of(2001,10,15),1,BigDecimal.valueOf(15.99));
        String outputAlbumJson=mapper.writeValueAsString(outputAlbum);
        String inputAlbumJson = mapper.writeValueAsString(inputAlbum);
        when(repo.save(inputAlbum)).thenReturn(outputAlbum);

        mockMvc.perform(MockMvcRequestBuilders.post("/album")
                        .content(inputAlbumJson)
                        .contentType(MediaType.APPLICATION_JSON))

                .andDo(print())
                .andExpect(status().isCreated())            // Assert
                .andExpect(content().json(outputAlbumJson));  // Assert
    }
    @Test
    public void getOneAlbumShouldReturn()throws Exception{
        Album album=new Album(117,"Halo", 1, LocalDate.of(2001,10,3),1,BigDecimal.valueOf(20.99));
        String expectedJsonValue=mapper.writeValueAsString(album);

        doReturn(Optional.of(album)).when(repo).findById(117);

        ResultActions result = mockMvc.perform(
                        MockMvcRequestBuilders.get("/album/117"))
                .andExpect(status().isOk())
                .andExpect((content().json(expectedJsonValue))
                );
    };


    @Test
    public void shouldUpdateByIdAndReturn200StatusCode() throws Exception {
        Album artist = new Album( 117,"Halo", 1, LocalDate.ofEpochDay(2001-5-1),1,BigDecimal.valueOf(15.99));
        String expectedJsonValue=mapper.writeValueAsString(artist);
        mockMvc.perform(
                        put("/album/117")
                                .content(expectedJsonValue)
                                .contentType(MediaType.APPLICATION_JSON)

                )
                .andExpect(status().isOk());

    }
    @Test
    public void shouldDeleteByIdAndReturn200StatusCode() throws Exception {
        Album artist = new Album( 1,"Halo", 1,LocalDate.ofEpochDay(2001-5-1),1,BigDecimal.valueOf(15.99));
        mockMvc.perform(MockMvcRequestBuilders.delete("/album/1")).andExpect(status().isNoContent());
    }



}