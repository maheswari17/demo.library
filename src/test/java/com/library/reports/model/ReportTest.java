package com.library.reports.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

class ReportTest {

    @Test
    void testConstructor() {
        Report actualReport = new Report();
        LocalDate ofEpochDayResult = LocalDate.ofEpochDay(1L);
        actualReport.setIssueDate(ofEpochDayResult);
        actualReport.setRegNo(1L);
        actualReport.setUserId(123);
        assertSame(ofEpochDayResult, actualReport.getIssueDate());
        assertEquals(1L, actualReport.getRegNo());
        assertEquals(123, actualReport.getUserId());
    }


    @Test
    void testConstructor2() {
        Report actualReport = new Report(1L, 123, LocalDate.ofEpochDay(1L));

        assertEquals("1970-01-02", actualReport.getIssueDate().toString());
        assertEquals(123, actualReport.getUserId());
        assertEquals(1L, actualReport.getRegNo());
    }
}

