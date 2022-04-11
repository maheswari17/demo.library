package com.library.book.service;

import com.library.book.dto.BookDto;
import com.library.book.model.Book;
import com.library.book.repository.BookRepository;
import com.library.exceptions.CustomNotFoundException.BookNotFoundException;
import com.library.exceptions.CustomNotFoundException.LoanNotFoundException;
import com.library.loan.model.Loan;
import com.library.utils.DtoConverter;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BookServiceImpl implements BookService {
    BookRepository bookRepository;
    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public List<BookDto> getAllBooks() {
        return bookRepository.findAll().stream().map(DtoConverter::buildBookDto).collect(Collectors.toList());
    }

    @Override
    public BookDto addBook(BookDto bookDto) {
        Book book= bookRepository.save(DtoConverter.buildBook(bookDto));
        return DtoConverter.buildBookDto(book);
    }

    @Override
    public BookDto getBook(long id) {
        Optional<Book> book = bookRepository.findById(id);
        if(book.isPresent()) {
            return DtoConverter.buildBookDto(book.get());
        }throw new BookNotFoundException("book details not found");
    }

    @Override
    public void deleteBook(long id) {
        try {
            bookRepository.deleteById(id);
        } catch (DataAccessException e) {
            throw new BookNotFoundException("book with " + id + " doesn't exist");
        }
    }


}
