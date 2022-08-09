package com.company.musicstorecatalog.controller;

import com.company.musicstorecatalog.repository.LabelRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.company.musicstorecatalog.model.Label;

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
@WebMvcTest(LabelController.class)
public class LabelControllerTest {
    @MockBean
    private LabelRepository repo;

    private ObjectMapper mapper =new ObjectMapper();

    @Autowired
    MockMvc mockMvc;

    @Before
    public void setUp()throws Exception{
        setUpProduceServiceMock();
    }
    public void setUpProduceServiceMock(){
        Label halo =new Label(117, "halo","halo.com");
        Label haloWithoutId =new Label("halo","halo.com");
        List<Label> labelList= Arrays.asList(halo);
        doReturn(labelList).when(repo).findAll();
        doReturn(halo).when(repo).save(haloWithoutId);

    }
    @Test
    public void getAllLabelShouldReturnListAnd200()throws Exception{
        Label halo =new Label(117, "halo","halo.com");
        List<Label> labelList= Arrays.asList(halo);
        String expectedJsonValue =mapper.writeValueAsString(labelList);
        mockMvc.perform(get("/label"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(expectedJsonValue));

    }
    @Test
    public void createLabelShouldReturnNewLabel()throws Exception{
        Label outputProduce=new Label(117, "halo","halo.com");
        Label inputProduce= new Label("halo","halo.com");
        String outputProduceJson=mapper.writeValueAsString(outputProduce);
        String inputProduceJson = mapper.writeValueAsString(inputProduce);

        mockMvc.perform(post("/label")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(inputProduceJson))
                .andDo(print())
                .andExpect(status().isCreated())            // Assert
                .andExpect(content().json(outputProduceJson));  // Assert
    }
    @Test
    public void getOneLabelShouldReturn()throws Exception{
        Label label=new Label(117, "halo","halo.com");
        String expectedJsonValue=mapper.writeValueAsString(label);



        doReturn(Optional.of(label)).when(repo).findById(117);

        ResultActions result = mockMvc.perform(
                        get("/label/117"))
                .andExpect(status().isOk())
                .andExpect((content().json(expectedJsonValue))
                );
    }


    @Test
    public void shouldUpdateByIdAndReturn200StatusCode() throws Exception {
        Label label = new Label( 117,"halo","halo.com");
        String expectedJsonValue=mapper.writeValueAsString(label);
        mockMvc.perform(
                        put("/label/117")
                                .content(expectedJsonValue)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk());

    }
    @Test
    public void shouldDeleteByIdAndReturn200StatusCode() throws Exception {
        Label label = new Label( 1,"halo","halo.com");
        mockMvc.perform(delete("/label/1")).andExpect(status().isNoContent());
    }


}