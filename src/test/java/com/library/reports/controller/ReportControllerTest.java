package com.library.reports.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.library.reports.dto.ReportDto;
import com.library.reports.model.Report;
import com.library.reports.repository.ReportRepository;
import com.library.reports.service.ReportServiceImpl;

import java.time.LocalDate;
import java.util.ArrayList;

import org.junit.jupiter.api.Disabled;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.modelmapper.ModelMapper;
import org.modelmapper.internal.InheritingConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ContextConfiguration(classes = {ReportController.class})
@ExtendWith(SpringExtension.class)
class ReportControllerTest {
    @Autowired
    private ReportController reportController;

    @MockBean
    private ReportServiceImpl reportServiceImpl;


    @Test
    void testGetAllReports() throws Exception {
        when(this.reportServiceImpl.getAllReports()).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/report");
        MockMvcBuilders.standaloneSetup(this.reportController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }


    @Test
    void testGet() throws Exception {
        ReportDto reportDto = new ReportDto();
        reportDto.setIssueDate(LocalDate.ofEpochDay(1L));
        reportDto.setRegNo(1L);
        reportDto.setUserId(123);
        when(this.reportServiceImpl.getReport(anyLong())).thenReturn(reportDto);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/report/{id}", 123L);
        MockMvcBuilders.standaloneSetup(this.reportController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("{\"regNo\":1,\"userId\":123,\"issueDate\":[1970,1,2]}"));
    }

    @Test
    void testAdd() {
        Report report = new Report();
        report.setIssueDate(LocalDate.ofEpochDay(1L));
        report.setRegNo(1L);
        report.setUserId(123);
        ReportRepository reportRepository = mock(ReportRepository.class);
        when(reportRepository.save((Report) any())).thenReturn(report);
        ReportController reportController = new ReportController(
                new ReportServiceImpl(reportRepository, new ModelMapper()));

        ReportDto reportDto = new ReportDto();
        reportDto.setIssueDate(LocalDate.ofEpochDay(1L));
        reportDto.setRegNo(1L);
        reportDto.setUserId(123);
        ReportDto actualAddResult = reportController.add(reportDto);
        assertEquals("1970-01-02", actualAddResult.getIssueDate().toString());
        assertEquals(123, actualAddResult.getUserId());
        assertEquals(1L, actualAddResult.getRegNo());
        verify(reportRepository).save((Report) any());
    }


    @Test
    void testDelete() throws Exception {
        doNothing().when(this.reportServiceImpl).deleteReport(anyLong());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/report/{id}", 1);
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(this.reportController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isNoContent());
    }


}

