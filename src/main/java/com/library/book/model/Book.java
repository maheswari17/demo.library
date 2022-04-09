package com.library.book.model;

import com.library.book.dto.BookDto;
import com.library.loan.model.Loan;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "book")
public class Book   {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull
    private long bookId;
    @NotNull
    private String bookTitle;
    @NotNull
    private String author;
    @NotNull
    private int edition;
    @NotNull
    private int price;

}
