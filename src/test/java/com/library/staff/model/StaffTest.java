package com.library.staff.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.library.reports.model.Report;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

class StaffTest {

    @Test
    void testConstructor() {
        Staff actualStaff = new Staff();
        ArrayList<Report> reportList = new ArrayList<>();
        actualStaff.setReport(reportList);
        actualStaff.setStaffId(123L);
        actualStaff.setStaffName(" banu");
        assertSame(reportList, actualStaff.getReport());
        assertEquals(123L, actualStaff.getStaffId());
        assertEquals(" banu", actualStaff.getStaffName());
    }


    @Test
    void testConstructor2() {
        Staff actualStaff = new Staff(123L, " banu", new ArrayList<>());

        assertTrue(actualStaff.getReport().isEmpty());
        assertEquals(" banu", actualStaff.getStaffName());
        assertEquals(123L, actualStaff.getStaffId());
    }
}

