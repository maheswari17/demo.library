package com.library.utils;
import com.library.book.dto.BookDto;
import com.library.book.model.Book;
import com.library.loan.dto.LoanDto;
import com.library.loan.model.Loan;
import com.library.member.dto.MemberDto;
import com.library.member.model.Member;
import lombok.experimental.UtilityClass;

@UtilityClass
public class DtoConverter {
    public BookDto buildBookDto(Book book)   {
        BookDto bookDto = new BookDto();
        bookDto.setBookId(book.getBookId());
        bookDto.setBookTitle(book.getBookTitle());
        bookDto.setAuthor(book.getAuthor());
        bookDto.setEdition(book.getEdition());
        bookDto.setPrice(book.getPrice());
        return bookDto;
    }

    public Book buildBook(BookDto bookDto)  {
        Book book = new Book();
        book.setBookId(bookDto.getBookId());
        book.setBookTitle(bookDto.getBookTitle());
        book.setAuthor(bookDto.getAuthor());
        book.setEdition(bookDto.getEdition());
        book.setPrice(bookDto.getPrice());
        return book;
    }

    public MemberDto buildMemberDto(Member member)   {
        MemberDto memberDto = new MemberDto();
        memberDto.setMemberId(member.getMemberId());
        memberDto.setFirstName(member.getFirstName());
        memberDto.setLastName(member.getLastName());
        memberDto.setDepartment(member.getDepartment());
        memberDto.setEmail(member.getEmail());
        memberDto.setContactNo(member.getContactNo());
        return memberDto;
    }

    public Member buildMember(MemberDto memberDto)  {
        Member member = new Member();
        member.setMemberId(memberDto.getMemberId());
        member.setFirstName(memberDto.getFirstName());
        member.setLastName(memberDto.getLastName());
        member.setEmail(memberDto.getEmail());
        member.setDepartment(memberDto.getDepartment());
        member.setContactNo(memberDto.getContactNo());
        return member;
    }

    public LoanDto buildLoanDto(Loan loan)   {
        LoanDto loanDto = new LoanDto();
        loanDto.setBookDto(buildBookDto(loan.getBook()));
        loanDto.setMemberDto(buildMemberDto(loan.getMember()));
        loanDto.setMemberId(loan.getMemberId());
        loanDto.setLoanId(loan.getLoanId());
        loanDto.setBookId(loan.getBookId());
        loanDto.setIssueDate(loan.getIssueDate());
        loanDto.setReturnDate(loan.getReturnDate());
        return loanDto;
    }

    public Loan buildLoan(LoanDto loanDto)  {
        Loan loan = new Loan();
        loan.setMemberId(loanDto.getMemberId());
        loan.setLoanId(loanDto.getLoanId());
        loan.setBookId(loanDto.getBookId());
        loan.setIssueDate(loanDto.getIssueDate());
        loan.setReturnDate(loanDto.getReturnDate());
        return loan;
    }
}
