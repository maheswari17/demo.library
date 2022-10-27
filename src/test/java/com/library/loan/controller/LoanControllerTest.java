package com.library.loan.controller;

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

import com.library.book.dto.BookDto;
import com.library.book.model.Book;
import com.library.loan.dto.LoanDto;
import com.library.loan.model.Loan;
import com.library.loan.repository.LoanRepository;
import com.library.loan.service.LoanServiceImpl;
import com.library.member.dto.MemberDto;
import com.library.member.model.Member;

import java.time.LocalDate;

import java.util.ArrayList;

import org.hibernate.BaseSessionEventListener;
import org.hibernate.SessionEventListener;

import org.hibernate.engine.spi.SessionDelegatorBaseImpl;
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

@ContextConfiguration(classes = {LoanController.class})
@ExtendWith(SpringExtension.class)
class LoanControllerTest {
    @Autowired
    private LoanController loanController;

    @MockBean
    private LoanServiceImpl loanServiceImpl;


    @Test
    void testGetLoan() throws Exception {
        BookDto bookDto = new BookDto();
        bookDto.setAuthor("ani");
        bookDto.setBookId(123L);
        bookDto.setBookTitle("java");
        bookDto.setEdition(1);
        bookDto.setPrice(111);

        MemberDto memberDto = new MemberDto();
        memberDto.setContactNo(1L);
        memberDto.setDepartment("cse");
        memberDto.setEmail("ram@gmail.org");
        memberDto.setFirstName("ram");
        memberDto.setLastName("charan");
        memberDto.setMemberId(123L);

        LoanDto loanDto = new LoanDto();
        loanDto.setBookDto(bookDto);
        loanDto.setBookId(123L);
        loanDto.setIssueDate(LocalDate.ofEpochDay(1L));
        loanDto.setLoanId(123L);
        loanDto.setMemberDto(memberDto);
        loanDto.setMemberId(123L);
        loanDto.setReturnDate(LocalDate.ofEpochDay(1L));
        when(this.loanServiceImpl.getLoan((Long) any())).thenReturn(loanDto);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/loans/{id}", 123L);
        MockMvcBuilders.standaloneSetup(this.loanController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "{\"memberId\":123,\"bookId\":123,\"loanId\":123,\"returnDate\":[1970,1,2],\"issueDate\":[1970,1,2],\"memberDto"
                                        + "\":{\"memberId\":123,\"firstName\":\"ram\",\"lastName\":\"charan\",\"department\":\"cse\",\"email\":\"ram"
                                        + "@gmail.org\",\"contactNo\":1},\"bookDto\":{\"bookId\":123,\"bookTitle\":\"java\",\"author\":\"ani\",\"edition\":1"
                                        + ",\"price\":111}}"));
    }



    @Test
    void testSaveLoan() {

        Book book = new Book();
        book.setAuthor("JaneDoe");
        book.setBookId(123L);
        book.setBookTitle("Dr");
        book.setEdition(1);
        book.setPrice(1);

        Member member = new Member();
        member.setContactNo(1L);
        member.setDepartment("Department");
        member.setEmail("jane.doe@example.org");
        member.setFirstName("Jane");
        member.setLastName("Doe");
        member.setMemberId(123L);

        Loan loan = new Loan();
        loan.setBook(book);
        loan.setBookId(123L);
        loan.setIssueDate(LocalDate.ofEpochDay(1L));
        loan.setLoanId(123L);
        loan.setMember(member);
        loan.setMemberId(123L);
        loan.setReturnDate(LocalDate.ofEpochDay(1L));
        LoanRepository loanRepository = mock(LoanRepository.class);
        when(loanRepository.saveAndFlush((Loan) any())).thenReturn(loan);
        SessionDelegatorBaseImpl sessionDelegatorBaseImpl = mock(SessionDelegatorBaseImpl.class);
        doNothing().when(sessionDelegatorBaseImpl).refresh((Object) any());
        SessionDelegatorBaseImpl entityManager = new SessionDelegatorBaseImpl(
                new SessionDelegatorBaseImpl(new SessionDelegatorBaseImpl(sessionDelegatorBaseImpl)));
        LoanController loanController = new LoanController(
                new LoanServiceImpl(loanRepository, entityManager, new ModelMapper()));

        BookDto bookDto = new BookDto();
        bookDto.setAuthor("JaneDoe");
        bookDto.setBookId(123L);
        bookDto.setBookTitle("Dr");
        bookDto.setEdition(1);
        bookDto.setPrice(1);

        MemberDto memberDto = new MemberDto();
        memberDto.setContactNo(1L);
        memberDto.setDepartment("Department");
        memberDto.setEmail("jane.doe@example.org");
        memberDto.setFirstName("Jane");
        memberDto.setLastName("Doe");
        memberDto.setMemberId(123L);

        LoanDto loanDto = new LoanDto();
        loanDto.setBookDto(bookDto);
        loanDto.setBookId(123L);
        loanDto.setIssueDate(LocalDate.ofEpochDay(1L));
        loanDto.setLoanId(123L);
        loanDto.setMemberDto(memberDto);
        loanDto.setMemberId(123L);
        loanDto.setReturnDate(LocalDate.ofEpochDay(1L));
        LoanDto actualSaveLoanResult = loanController.saveLoan(loanDto);
        assertEquals("1970-01-02", actualSaveLoanResult.getReturnDate().toString());
        assertEquals(123L, actualSaveLoanResult.getBookId());
        assertEquals(123L, actualSaveLoanResult.getLoanId());
        assertSame(member, actualSaveLoanResult.getMemberDto());
        assertEquals("1970-01-02", actualSaveLoanResult.getIssueDate().toString());
        assertEquals(123L, actualSaveLoanResult.getMemberId());
        BookDto bookDto1 = actualSaveLoanResult.getBookDto();
        assertEquals(123L, bookDto1.getBookId());
        assertEquals(1, bookDto1.getEdition());
        assertEquals("Dr", bookDto1.getBookTitle());
        assertEquals("JaneDoe", bookDto1.getAuthor());
        assertEquals(1, bookDto1.getPrice());
        verify(loanRepository).saveAndFlush((Loan) any());
        verify(sessionDelegatorBaseImpl).refresh((Object) any());
    }

    @Test
    void testDeleteLoan() throws Exception {
        doNothing().when(this.loanServiceImpl).deleteLoan((Long) any());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/loans/{id}", 123L);
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(this.loanController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    void testGetAllMemberBooks() throws Exception {
        when(this.loanServiceImpl.getAllMembersBook()).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/loans");
        MockMvcBuilders.standaloneSetup(this.loanController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }


}

