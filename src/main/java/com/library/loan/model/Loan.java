package com.library.loan.model;
import com.library.book.model.Book;
import com.library.member.model.Member;
import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "loan")
public class Loan  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
   @NotNull
    private long loanId;
    @NotNull
    private long memberId;
    @NotNull
    private long bookId;
    @NotNull
    private LocalDate returnDate;
    @NotNull
    private LocalDate issueDate;

    @ManyToOne
    @JoinColumn(name = "bookId", insertable = false, updatable = false)
    private Book book;

    @ManyToOne
    @JoinColumn(name = "memberId", insertable = false, updatable = false)
    private Member member;

    public Book getBook() {
        return book;
    }


    public Member getMember() {
        return member;
    }

}
