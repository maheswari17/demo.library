package com.library.loan.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.library.book.dto.BookDto;
import com.library.book.model.Book;
import com.library.exceptions.CustomNotFoundException.LoanNotFoundException;
import com.library.loan.dto.LoanDto;
import com.library.loan.model.Loan;
import com.library.loan.repository.LoanRepository;
import com.library.member.dto.MemberDto;
import com.library.member.model.Member;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;
import javax.persistence.EntityManager;

import org.junit.jupiter.api.Disabled;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.modelmapper.ModelMapper;
import org.modelmapper.internal.InheritingConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {LoanServiceImpl.class})
@ExtendWith(SpringExtension.class)
class LoanServiceImplTest {
    @MockBean
    private EntityManager entityManager;

    @MockBean
    private LoanRepository loanRepository;

    @Autowired
    private LoanServiceImpl loanServiceImpl;

    @MockBean
    private ModelMapper modelMapper;


    @Test
    void testGetAllMembersBook() {
        when(this.loanRepository.findAll()).thenReturn(new ArrayList<>());
        assertTrue(this.loanServiceImpl.getAllMembersBook().isEmpty());
        verify(this.loanRepository).findAll();
    }

    @Test
    void testGetAllMembersBook2() {
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
        when(this.modelMapper.map((Object) any(), (Class<LoanDto>) any())).thenReturn(loanDto);
        when(this.modelMapper.getConfiguration()).thenReturn(new InheritingConfiguration());

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

        ArrayList<Loan> loanList = new ArrayList<>();
        loanList.add(loan);
        when(this.loanRepository.findAll()).thenReturn(loanList);
        assertEquals(1, this.loanServiceImpl.getAllMembersBook().size());
        verify(this.modelMapper).map((Object) any(), (Class<LoanDto>) any());
        verify(this.modelMapper).getConfiguration();
        verify(this.loanRepository).findAll();
    }

    @Test
    void testSaveLoan() {
        when(this.modelMapper.map((Object) any(), (Class<Object>) any())).thenThrow(new LoanNotFoundException("Exception"));
        when(this.modelMapper.getConfiguration()).thenReturn(new InheritingConfiguration());

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
        assertThrows(LoanNotFoundException.class, () -> this.loanServiceImpl.saveLoan(loanDto));
        verify(this.modelMapper).map((Object) any(), (Class<Loan>) any());
        verify(this.modelMapper).getConfiguration();
    }

    @Test
    void testGetLoan() {
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
        when(this.modelMapper.map((Object) any(), (Class<LoanDto>) any())).thenReturn(loanDto);
        when(this.modelMapper.getConfiguration()).thenReturn(new InheritingConfiguration());

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
        Optional<Loan> ofResult = Optional.of(loan);
        when(this.loanRepository.findById((Long) any())).thenReturn(ofResult);
        assertSame(loanDto, this.loanServiceImpl.getLoan(123L));
        verify(this.modelMapper).map((Object) any(), (Class<LoanDto>) any());
        verify(this.modelMapper).getConfiguration();
        verify(this.loanRepository).findById((Long) any());
    }

    @Test
    void testGetLoan2() {
        when(this.modelMapper.map((Object) any(), (Class<LoanDto>) any()))
                .thenThrow(new LoanNotFoundException("Exception"));
        when(this.modelMapper.getConfiguration()).thenReturn(new InheritingConfiguration());

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
        Optional<Loan> ofResult = Optional.of(loan);
        when(this.loanRepository.findById((Long) any())).thenReturn(ofResult);
        assertThrows(LoanNotFoundException.class, () -> this.loanServiceImpl.getLoan(123L));
        verify(this.modelMapper).map((Object) any(), (Class<LoanDto>) any());
        verify(this.modelMapper).getConfiguration();
        verify(this.loanRepository).findById((Long) any());
    }


    @Test
    void testDeleteLoan() {
        doNothing().when(this.loanRepository).deleteById((Long) any());
        this.loanServiceImpl.deleteLoan(123L);
        verify(this.loanRepository).deleteById((Long) any());
        assertTrue(this.loanServiceImpl.getAllMembersBook().isEmpty());
    }

    @Test
    void testDeleteLoan2() {
        doThrow(new LoanNotFoundException("Exception")).when(this.loanRepository).deleteById((Long) any());
        assertThrows(LoanNotFoundException.class, () -> this.loanServiceImpl.deleteLoan(123L));
        verify(this.loanRepository).deleteById((Long) any());
    }

    @Test
    void testBuildLoanDto() {
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
        when(this.modelMapper.map((Object) any(), (Class<LoanDto>) any())).thenReturn(loanDto);
        when(this.modelMapper.getConfiguration()).thenReturn(new InheritingConfiguration());

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
        assertSame(loanDto, this.loanServiceImpl.buildLoanDto(loan));
        verify(this.modelMapper).map((Object) any(), (Class<LoanDto>) any());
        verify(this.modelMapper).getConfiguration();
    }

    @Test
    void testBuildLoanDto2() {
        when(this.modelMapper.map((Object) any(), (Class<LoanDto>) any()))
                .thenThrow(new LoanNotFoundException("Exception"));
        when(this.modelMapper.getConfiguration()).thenReturn(new InheritingConfiguration());

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
        assertThrows(LoanNotFoundException.class, () -> this.loanServiceImpl.buildLoanDto(loan));
        verify(this.modelMapper).map((Object) any(), (Class<LoanDto>) any());
        verify(this.modelMapper).getConfiguration();
    }

    @Test
    void testBuildLoan() {
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
        when(this.modelMapper.map((Object) any(), (Class<Loan>) any())).thenReturn(loan);
        when(this.modelMapper.getConfiguration()).thenReturn(new InheritingConfiguration());

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
        assertSame(loan, this.loanServiceImpl.buildLoan(loanDto));
        verify(this.modelMapper).map((Object) any(), (Class<Loan>) any());
        verify(this.modelMapper).getConfiguration();
    }

    @Test
    void testBuildLoan2() {
        when(this.modelMapper.map((Object) any(), (Class<Loan>) any())).thenThrow(new LoanNotFoundException("Exception"));
        when(this.modelMapper.getConfiguration()).thenReturn(new InheritingConfiguration());

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
        assertThrows(LoanNotFoundException.class, () -> this.loanServiceImpl.buildLoan(loanDto));
        verify(this.modelMapper).map((Object) any(), (Class<Loan>) any());
        verify(this.modelMapper).getConfiguration();
    }

}

