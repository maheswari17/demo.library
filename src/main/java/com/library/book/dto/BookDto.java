package com.library.book.dto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class BookDto {
    private long bookId;
    private String bookTitle;
    private String author;
    private int edition;
    private int price;

}
