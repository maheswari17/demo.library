package com.library.staff.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class StaffDtoTest {

    @Test
    void testConstructor() {
        StaffDto actualStaffDto = new StaffDto();
        actualStaffDto.setStaffId(123L);
        actualStaffDto.setStaffName(" banu");
        assertEquals(123L, actualStaffDto.getStaffId());
        assertEquals(" banu", actualStaffDto.getStaffName());
    }
}

