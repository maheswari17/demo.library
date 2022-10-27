package com.library.member.model;

import com.library.book.model.Book;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MemberTest {
    @Test
    void testConstructor() {
        Member member = new Member();
        member.setMemberId(123L);
        member.setFirstName("aaa");
        member.setLastName("ani");
        member.setContactNo(98734829329L);
        member.setDepartment("cse");
        member.setEmail("ani@gmail.com");
        assertEquals(123L, member.getMemberId());
        assertEquals("aaa", member.getFirstName());
        assertEquals("ani", member.getLastName());
        assertEquals(98734829329L, member.getContactNo());
        assertEquals("cse", member.getDepartment());
        assertEquals("ani@gmail.com",member.getEmail());
    }

    @Test
    void testConstructor2() {
        Member member = new Member(123L, "aaa","ani","cse","ani@gmail.com",98734829329L);
        assertEquals(123L, member.getMemberId());
        assertEquals("aaa", member.getFirstName());
        assertEquals("ani",member.getLastName());
        assertEquals(98734829329L,member.getContactNo());
        assertEquals("cse",member.getDepartment());
        assertEquals("ani@gmail.com",member.getEmail());
    }
}
