package com.library.reports.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.library.exceptions.CustomNotFoundException.ReportNotFoundException;
import com.library.reports.dto.ReportDto;
import com.library.reports.model.Report;
import com.library.reports.repository.ReportRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;

import org.junit.jupiter.api.Disabled;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.modelmapper.ModelMapper;
import org.modelmapper.config.Configuration;
import org.modelmapper.internal.InheritingConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {ReportServiceImpl.class})
@ExtendWith(SpringExtension.class)
class ReportServiceImplTest {
    @MockBean
    private ModelMapper modelMapper;

    @MockBean
    private ReportRepository reportRepository;

    @Autowired
    private ReportServiceImpl reportServiceImpl;

    @Test
    void testGetAllReports() {
        when(this.reportRepository.findAll()).thenReturn(new ArrayList<>());
        assertTrue(this.reportServiceImpl.getAllReports().isEmpty());
        verify(this.reportRepository).findAll();
    }

    @Test
    void testGetAllReports3() {
        Report report = new Report();
        report.setIssueDate(LocalDate.ofEpochDay(1L));
        report.setRegNo(1L);
        report.setUserId(123);

        ArrayList<Report> reportList = new ArrayList<>();
        reportList.add(report);
        when(this.reportRepository.findAll()).thenReturn(reportList);
        when(this.modelMapper.map((Object) any(), (Class<ReportDto>) any()))
                .thenThrow(new ReportNotFoundException("Exception"));
        when(this.modelMapper.getConfiguration()).thenReturn(new InheritingConfiguration());
        assertThrows(ReportNotFoundException.class, () -> this.reportServiceImpl.getAllReports());
        verify(this.reportRepository).findAll();
        verify(this.modelMapper).map((Object) any(), (Class<ReportDto>) any());
        verify(this.modelMapper).getConfiguration();
    }

    @Test
    void testAddReport() {
        Report report = new Report();
        report.setIssueDate(LocalDate.ofEpochDay(1L));
        report.setRegNo(1L);
        report.setUserId(123);
        when(this.reportRepository.save((Report) any())).thenReturn(report);
        when(this.modelMapper.map((Object) any(), (Class<Object>) any()))
                .thenThrow(new ReportNotFoundException("Exception"));
        when(this.modelMapper.getConfiguration()).thenReturn(new InheritingConfiguration());

        ReportDto reportDto = new ReportDto();
        reportDto.setIssueDate(LocalDate.ofEpochDay(1L));
        reportDto.setRegNo(1L);
        reportDto.setUserId(123);
        assertThrows(ReportNotFoundException.class, () -> this.reportServiceImpl.addReport(reportDto));
        verify(this.modelMapper).map((Object) any(), (Class<Report>) any());
        verify(this.modelMapper).getConfiguration();
    }

    @Test
    void testAddReport2() {
        Report report = mock(Report.class);
        doNothing().when(report).setIssueDate((LocalDate) any());
        doNothing().when(report).setRegNo(anyLong());
        doNothing().when(report).setUserId(anyInt());
        report.setIssueDate(LocalDate.ofEpochDay(1L));
        report.setRegNo(1L);
        report.setUserId(123);
        when(this.reportRepository.save((Report) any())).thenReturn(report);
        when(this.modelMapper.map((Object) any(), (Class<Object>) any())).thenReturn(null);
        when(this.modelMapper.getConfiguration()).thenReturn(new InheritingConfiguration());

        ReportDto reportDto = new ReportDto();
        reportDto.setIssueDate(LocalDate.ofEpochDay(1L));
        reportDto.setRegNo(1L);
        reportDto.setUserId(123);
        assertNull(this.reportServiceImpl.addReport(reportDto));
        verify(this.reportRepository).save((Report) any());
        verify(report).setIssueDate((LocalDate) any());
        verify(report).setRegNo(anyLong());
        verify(report).setUserId(anyInt());
        verify(this.modelMapper, atLeast(1)).map((Object) any(), (Class<Report>) any());
        verify(this.modelMapper, atLeast(1)).getConfiguration();
    }


    @Test
    void testGetReport() {
        Report report = new Report();
        report.setIssueDate(LocalDate.ofEpochDay(1L));
        report.setRegNo(1L);
        report.setUserId(123);
        Optional<Report> ofResult = Optional.of(report);
        when(this.reportRepository.findById((Long) any())).thenReturn(ofResult);

        ReportDto reportDto = new ReportDto();
        reportDto.setIssueDate(LocalDate.ofEpochDay(1L));
        reportDto.setRegNo(1L);
        reportDto.setUserId(123);
        when(this.modelMapper.map((Object) any(), (Class<ReportDto>) any())).thenReturn(reportDto);
        when(this.modelMapper.getConfiguration()).thenReturn(new InheritingConfiguration());
        assertSame(reportDto, this.reportServiceImpl.getReport(123L));
        verify(this.reportRepository).findById((Long) any());
        verify(this.modelMapper).map((Object) any(), (Class<ReportDto>) any());
        verify(this.modelMapper).getConfiguration();
    }


    @Test
    void testGetReport2() {
        Report report = new Report();
        report.setIssueDate(LocalDate.ofEpochDay(1L));
        report.setRegNo(1L);
        report.setUserId(123);
        Optional<Report> ofResult = Optional.of(report);
        when(this.reportRepository.findById((Long) any())).thenReturn(ofResult);
        when(this.modelMapper.map((Object) any(), (Class<ReportDto>) any()))
                .thenThrow(new ReportNotFoundException("Exception"));
        when(this.modelMapper.getConfiguration()).thenReturn(new InheritingConfiguration());
        assertThrows(ReportNotFoundException.class, () -> this.reportServiceImpl.getReport(123L));
        verify(this.reportRepository).findById((Long) any());
        verify(this.modelMapper).map((Object) any(), (Class<ReportDto>) any());
        verify(this.modelMapper).getConfiguration();
    }

    @Test
    void testDeleteReport() {
        doNothing().when(this.reportRepository).deleteById((Long) any());
        this.reportServiceImpl.deleteReport(123L);
        verify(this.reportRepository).deleteById((Long) any());
        assertTrue(this.reportServiceImpl.getAllReports().isEmpty());
    }


    @Test
    void testDeleteReport2() {
        doThrow(new ReportNotFoundException("Exception")).when(this.reportRepository).deleteById((Long) any());
        assertThrows(ReportNotFoundException.class, () -> this.reportServiceImpl.deleteReport(123L));
        verify(this.reportRepository).deleteById((Long) any());
    }

    @Test
    void testBuildReportDto() {
        ReportDto reportDto = new ReportDto();
        reportDto.setIssueDate(LocalDate.ofEpochDay(1L));
        reportDto.setRegNo(1L);
        reportDto.setUserId(123);
        when(this.modelMapper.map((Object) any(), (Class<ReportDto>) any())).thenReturn(reportDto);
        when(this.modelMapper.getConfiguration()).thenReturn(new InheritingConfiguration());

        Report report = new Report();
        report.setIssueDate(LocalDate.ofEpochDay(1L));
        report.setRegNo(1L);
        report.setUserId(123);
        assertSame(reportDto, this.reportServiceImpl.buildReportDto(report));
        verify(this.modelMapper).map((Object) any(), (Class<ReportDto>) any());
        verify(this.modelMapper).getConfiguration();
    }


    @Test
    void testBuildReportDto2() {
        when(this.modelMapper.map((Object) any(), (Class<ReportDto>) any()))
                .thenThrow(new ReportNotFoundException("Exception"));
        when(this.modelMapper.getConfiguration()).thenReturn(new InheritingConfiguration());

        Report report = new Report();
        report.setIssueDate(LocalDate.ofEpochDay(1L));
        report.setRegNo(1L);
        report.setUserId(123);
        assertThrows(ReportNotFoundException.class, () -> this.reportServiceImpl.buildReportDto(report));
        verify(this.modelMapper).map((Object) any(), (Class<ReportDto>) any());
        verify(this.modelMapper).getConfiguration();
    }

    @Test
    void testBuildReport() {
        Report report = new Report();
        report.setIssueDate(LocalDate.ofEpochDay(1L));
        report.setRegNo(1L);
        report.setUserId(123);
        when(this.modelMapper.map((Object) any(), (Class<Report>) any())).thenReturn(report);
        when(this.modelMapper.getConfiguration()).thenReturn(new InheritingConfiguration());

        ReportDto reportDto = new ReportDto();
        reportDto.setIssueDate(LocalDate.ofEpochDay(1L));
        reportDto.setRegNo(1L);
        reportDto.setUserId(123);
        assertSame(report, this.reportServiceImpl.buildReport(reportDto));
        verify(this.modelMapper).map((Object) any(), (Class<Report>) any());
        verify(this.modelMapper).getConfiguration();
    }


    @Test
    void testBuildReport2() {
        when(this.modelMapper.map((Object) any(), (Class<Report>) any()))
                .thenThrow(new ReportNotFoundException("Exception"));
        when(this.modelMapper.getConfiguration()).thenReturn(new InheritingConfiguration());

        ReportDto reportDto = new ReportDto();
        reportDto.setIssueDate(LocalDate.ofEpochDay(1L));
        reportDto.setRegNo(1L);
        reportDto.setUserId(123);
        assertThrows(ReportNotFoundException.class, () -> this.reportServiceImpl.buildReport(reportDto));
        verify(this.modelMapper).map((Object) any(), (Class<Report>) any());
        verify(this.modelMapper).getConfiguration();
    }

}

