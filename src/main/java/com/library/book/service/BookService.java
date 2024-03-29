package com.library.book.service;
import com.library.book.dto.BookDto;
import java.util.List;

public interface BookService {
    List<BookDto> getAllBooks();
    BookDto addBook(BookDto bookDto);
    BookDto getBook(long id);
    void deleteBook(long id);
}
