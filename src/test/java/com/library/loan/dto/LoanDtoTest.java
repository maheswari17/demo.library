package com.library.loan.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

import com.library.book.dto.BookDto;
import com.library.member.dto.MemberDto;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

class LoanDtoTest {

    @Test
    void testConstructor() {
        LoanDto actualLoanDto = new LoanDto();
        BookDto bookDto = new BookDto();
        bookDto.setAuthor("ani");
        bookDto.setBookId(123L);
        bookDto.setBookTitle("java");
        bookDto.setEdition(1);
        bookDto.setPrice(111);
        actualLoanDto.setBookDto(bookDto);
        actualLoanDto.setBookId(123L);
        LocalDate ofEpochDayResult = LocalDate.ofEpochDay(1L);
        actualLoanDto.setIssueDate(ofEpochDayResult);
        actualLoanDto.setLoanId(123L);
        MemberDto memberDto = new MemberDto();
        memberDto.setContactNo(1L);
        memberDto.setDepartment("cse");
        memberDto.setEmail("jane.doe@example.org");
        memberDto.setFirstName("Jane");
        memberDto.setLastName("Doe");
        memberDto.setMemberId(123L);
        actualLoanDto.setMemberDto(memberDto);
        actualLoanDto.setMemberId(123L);
        LocalDate ofEpochDayResult1 = LocalDate.ofEpochDay(1L);
        actualLoanDto.setReturnDate(ofEpochDayResult1);
        assertSame(bookDto, actualLoanDto.getBookDto());
        assertEquals(123L, actualLoanDto.getBookId());
        assertSame(ofEpochDayResult, actualLoanDto.getIssueDate());
        assertEquals(123L, actualLoanDto.getLoanId());
        assertSame(memberDto, actualLoanDto.getMemberDto());
        assertEquals(123L, actualLoanDto.getMemberId());
        assertSame(ofEpochDayResult1, actualLoanDto.getReturnDate());
    }
}

