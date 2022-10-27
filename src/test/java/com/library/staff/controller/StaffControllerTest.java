package com.library.staff.controller;

import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.library.staff.dto.StaffDto;
import com.library.staff.service.StaffServiceImpl;

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

@ContextConfiguration(classes = {StaffController.class})
@ExtendWith(SpringExtension.class)
class StaffControllerTest {
    @Autowired
    private StaffController staffController;

    @MockBean
    private StaffServiceImpl staffServiceImpl;


    @Test
    void testGetAllStaffs() throws Exception {
        when(this.staffServiceImpl.getAllStaffs()).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/staff");
        MockMvcBuilders.standaloneSetup(this.staffController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }

    @Test
    void testGet() throws Exception {
        StaffDto staffDto = new StaffDto();
        staffDto.setStaffId(123L);
        staffDto.setStaffName(" banu");
        when(this.staffServiceImpl.getStaff(anyLong())).thenReturn(staffDto);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/staff/{id}", 1);
        MockMvcBuilders.standaloneSetup(this.staffController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("{\"staffId\":123,\"staffName\":\" banu\"}"));
    }

    @Test
    void testAdd() throws Exception {
        when(this.staffServiceImpl.getAllStaffs()).thenReturn(new ArrayList<>());

        StaffDto staffDto = new StaffDto();
        staffDto.setStaffId(123L);
        staffDto.setStaffName(" banu");
        String content = (new ObjectMapper()).writeValueAsString(staffDto);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/staff")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        MockMvcBuilders.standaloneSetup(this.staffController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }

    @Test
    void testDelete() throws Exception {
        doNothing().when(this.staffServiceImpl).deleteStaff(anyLong());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/staff/{id}", 123L);
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(this.staffController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isNoContent());
    }


}

