package com.library.publisher.controller;

import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.library.publisher.dto.PublisherDto;
import com.library.publisher.service.PublisherServiceImpl;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ContextConfiguration(classes = {PublisherController.class})
@ExtendWith(SpringExtension.class)
class PublisherControllerTest {
    @Autowired
    private PublisherController publisherController;

    @MockBean
    private PublisherServiceImpl publisherServiceImpl;


    @Test
    void testGetAllPublishers() throws Exception {
        when(this.publisherServiceImpl.getAllPublishers()).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/publishers");
        MockMvcBuilders.standaloneSetup(this.publisherController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }


    @Test
    void testGet() throws Exception {
        PublisherDto publisherDto = new PublisherDto();
        publisherDto.setPublisherId(123L);
        publisherDto.setPublisherName(" hari");
        publisherDto.setPublishingYear(2020L);
        when(this.publisherServiceImpl.getPublisher(anyLong())).thenReturn(publisherDto);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/publishers/{id}", 1);
        MockMvcBuilders.standaloneSetup(this.publisherController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string("{\"publisherId\":123,\"publisherName\":\" hari\",\"publishingYear\":2020}"));
    }


    @Test
    void testAdd() throws Exception {
        when(this.publisherServiceImpl.getAllPublishers()).thenReturn(new ArrayList<>());

        PublisherDto publisherDto = new PublisherDto();
        publisherDto.setPublisherId(123L);
        publisherDto.setPublisherName(" hari");
        publisherDto.setPublishingYear(2020L);
        String content = (new ObjectMapper()).writeValueAsString(publisherDto);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/publishers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        MockMvcBuilders.standaloneSetup(this.publisherController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }

    @Test
    void testDelete() throws Exception {
        doNothing().when(this.publisherServiceImpl).deletePublisher(anyLong());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/publishers/{id}", 123L);
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(this.publisherController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isNoContent());
    }


}

