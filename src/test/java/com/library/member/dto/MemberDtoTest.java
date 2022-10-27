package com.library.member.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.library.member.dto.MemberDto;
import org.junit.jupiter.api.Test;

class MemberDtoTest {

    @Test
    void testConstructor() {
        MemberDto actualMemberDto = new MemberDto();
        actualMemberDto.setContactNo(9988776655L);
        actualMemberDto.setDepartment("Cse");
        actualMemberDto.setEmail("fenni@gmail.com");
        actualMemberDto.setFirstName("fenni");
        actualMemberDto.setLastName("thomas");
        actualMemberDto.setMemberId(123L);
        assertEquals(9988776655L, actualMemberDto.getContactNo());
        assertEquals("Cse", actualMemberDto.getDepartment());
        assertEquals("fenni@gmail.com", actualMemberDto.getEmail());
        assertEquals("fenni", actualMemberDto.getFirstName());
        assertEquals("thomas", actualMemberDto.getLastName());
        assertEquals(123L, actualMemberDto.getMemberId());
    }
}
