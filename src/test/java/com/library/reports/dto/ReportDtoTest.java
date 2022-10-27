package com.library.reports.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

class ReportDtoTest {

    @Test
    void testConstructor() {
        ReportDto actualReportDto = new ReportDto();
        LocalDate ofEpochDayResult = LocalDate.ofEpochDay(1L);
        actualReportDto.setIssueDate(ofEpochDayResult);
        actualReportDto.setRegNo(1L);
        actualReportDto.setUserId(123);
        assertSame(ofEpochDayResult, actualReportDto.getIssueDate());
        assertEquals(1L, actualReportDto.getRegNo());
        assertEquals(123, actualReportDto.getUserId());
    }
}

