package com.library.book.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.library.book.dto.BookDto;
import com.library.book.model.Book;
import com.library.book.repository.BookRepository;
import com.library.exceptions.CustomNotFoundException.BookNotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.modelmapper.config.Configuration;
import org.modelmapper.internal.InheritingConfiguration;
import org.springframework.dao.EmptyResultDataAccessException;

class BookServiceImplTest {

    @Test
    void testGetAllBooks2() {

        BookRepository bookRepository = mock(BookRepository.class);
        when(bookRepository.findAll()).thenReturn(new ArrayList<>());
        assertTrue((new BookServiceImpl(bookRepository, new ModelMapper())).getAllBooks().isEmpty());
        verify(bookRepository).findAll();
    }


    @Test
    void testGetAllBooks3() {

        Book book = new Book();
        book.setAuthor("balu");
        book.setBookId(123L);
        book.setBookTitle("krishna");
        book.setEdition(1);
        book.setPrice(100);

        ArrayList<Book> bookList = new ArrayList<>();
        bookList.add(book);
        BookRepository bookRepository = mock(BookRepository.class);
        when(bookRepository.findAll()).thenReturn(bookList);
        List<BookDto> actualAllBooks = (new BookServiceImpl(bookRepository, new ModelMapper())).getAllBooks();
        assertEquals(1, actualAllBooks.size());
        BookDto getResult = actualAllBooks.get(0);
        assertEquals("balu", getResult.getAuthor());
        assertEquals(100, getResult.getPrice());
        assertEquals(1, getResult.getEdition());
        assertEquals("krishna", getResult.getBookTitle());
        assertEquals(123L, getResult.getBookId());
        verify(bookRepository).findAll();
    }


    @Test
    void testAddBook2() {

        Book book = new Book();
        book.setAuthor("balu");
        book.setBookId(123L);
        book.setBookTitle("oops");
        book.setEdition(1);
        book.setPrice(111);
        BookRepository bookRepository = mock(BookRepository.class);
        when(bookRepository.save((Book) any())).thenReturn(book);
        BookServiceImpl bookServiceImpl = new BookServiceImpl(bookRepository, new ModelMapper());

        BookDto bookDto = new BookDto();
        bookDto.setAuthor("balu");
        bookDto.setBookId(123L);
        bookDto.setBookTitle("oops");
        bookDto.setEdition(1);
        bookDto.setPrice(111);
        BookDto actualAddBookResult = bookServiceImpl.addBook(bookDto);
        assertEquals("balu", actualAddBookResult.getAuthor());
        assertEquals(111, actualAddBookResult.getPrice());
        assertEquals(1, actualAddBookResult.getEdition());
        assertEquals("oops", actualAddBookResult.getBookTitle());
        assertEquals(123L, actualAddBookResult.getBookId());
        verify(bookRepository).save((Book) any());
    }


    @Test
    void testAddBook3() {
        Book book = mock(Book.class);
        when(book.getEdition()).thenReturn(1);
        when(book.getPrice()).thenReturn(111);
        when(book.getAuthor()).thenReturn("balu");
        when(book.getBookTitle()).thenReturn("oops");
        when(book.getBookId()).thenReturn(123L);
        doNothing().when(book).setAuthor((String) any());
        doNothing().when(book).setBookId(anyLong());
        doNothing().when(book).setBookTitle((String) any());
        doNothing().when(book).setEdition(anyInt());
        doNothing().when(book).setPrice(anyInt());
        book.setAuthor("balu");
        book.setBookId(123L);
        book.setBookTitle("oops");
        book.setEdition(1);
        book.setPrice(111);
        BookRepository bookRepository = mock(BookRepository.class);
        when(bookRepository.save((Book) any())).thenReturn(book);
        BookServiceImpl bookServiceImpl = new BookServiceImpl(bookRepository, new ModelMapper());

        BookDto bookDto = new BookDto();
        bookDto.setAuthor("balu");
        bookDto.setBookId(123L);
        bookDto.setBookTitle("oops");
        bookDto.setEdition(1);
        bookDto.setPrice(111);
        BookDto actualAddBookResult = bookServiceImpl.addBook(bookDto);
        assertEquals("balu", actualAddBookResult.getAuthor());
        assertEquals(111, actualAddBookResult.getPrice());
        assertEquals(1, actualAddBookResult.getEdition());
        assertEquals("oops", actualAddBookResult.getBookTitle());
        assertEquals(123L, actualAddBookResult.getBookId());
        verify(bookRepository).save((Book) any());
        verify(book).getEdition();
        verify(book).getPrice();
        verify(book).getAuthor();
        verify(book).getBookTitle();
        verify(book).getBookId();
        verify(book).setAuthor((String) any());
        verify(book).setBookId(anyLong());
        verify(book).setBookTitle((String) any());
        verify(book).setEdition(anyInt());
        verify(book).setPrice(anyInt());
    }


    @Test
    void testAddBook4() {

        Book book = mock(Book.class);
        when(book.getEdition()).thenReturn(1);
        when(book.getPrice()).thenReturn(111);
        when(book.getAuthor()).thenReturn("balu");
        when(book.getBookTitle()).thenReturn("oops");
        when(book.getBookId()).thenReturn(123L);
        doNothing().when(book).setAuthor((String) any());
        doNothing().when(book).setBookId(anyLong());
        doNothing().when(book).setBookTitle((String) any());
        doNothing().when(book).setEdition(anyInt());
        doNothing().when(book).setPrice(anyInt());
        book.setAuthor("balu");
        book.setBookId(123L);
        book.setBookTitle("oops");
        book.setEdition(1);
        book.setPrice(111);
        BookRepository bookRepository = mock(BookRepository.class);
        when(bookRepository.save((Book) any())).thenReturn(book);
        ModelMapper modelMapper = mock(ModelMapper.class);
        when(modelMapper.map((Object) any(), (Class<Book>) any())).thenThrow(new BookNotFoundException("Exception"));
        when(modelMapper.getConfiguration()).thenReturn(new InheritingConfiguration());
        BookServiceImpl bookServiceImpl = new BookServiceImpl(bookRepository, modelMapper);

        BookDto bookDto = new BookDto();
        bookDto.setAuthor("balu");
        bookDto.setBookId(123L);
        bookDto.setBookTitle("oops");
        bookDto.setEdition(1);
        bookDto.setPrice(111);
        assertThrows(BookNotFoundException.class, () -> bookServiceImpl.addBook(bookDto));
        verify(book).setAuthor((String) any());
        verify(book).setBookId(anyLong());
        verify(book).setBookTitle((String) any());
        verify(book).setEdition(anyInt());
        verify(book).setPrice(anyInt());
        verify(modelMapper).map((Object) any(), (Class<Book>) any());
        verify(modelMapper).getConfiguration();
    }

    @Test
    void testDeleteBook2() {

        BookRepository bookRepository = mock(BookRepository.class);
        doNothing().when(bookRepository).deleteById((Long) any());
        (new BookServiceImpl(bookRepository, new ModelMapper())).deleteBook(123L);
        verify(bookRepository).deleteById((Long) any());
    }

    @Test
    void testDeleteBook3() {
        BookRepository bookRepository = mock(BookRepository.class);
        doThrow(new EmptyResultDataAccessException(3)).when(bookRepository).deleteById((Long) any());
        assertThrows(BookNotFoundException.class,
                () -> (new BookServiceImpl(bookRepository, new ModelMapper())).deleteBook(123L));
        verify(bookRepository).deleteById((Long) any());
    }

    @Test
    void testBuildBookDto3() {

        BookDto bookDto = new BookDto();
        bookDto.setAuthor("balu");
        bookDto.setBookId(123L);
        bookDto.setBookTitle("oops");
        bookDto.setEdition(1);
        bookDto.setPrice(111);
        ModelMapper modelMapper = mock(ModelMapper.class);
        when(modelMapper.map((Object) any(), (Class<BookDto>) any())).thenReturn(bookDto);
        when(modelMapper.getConfiguration()).thenReturn(new InheritingConfiguration());
        BookServiceImpl bookServiceImpl = new BookServiceImpl(mock(BookRepository.class), modelMapper);

        Book book = new Book();
        book.setAuthor("balu");
        book.setBookId(123L);
        book.setBookTitle("oops");
        book.setEdition(1);
        book.setPrice(111);
        assertSame(bookDto, bookServiceImpl.buildBookDto(book));
        verify(modelMapper).map((Object) any(), (Class<BookDto>) any());
        verify(modelMapper).getConfiguration();
    }

    @Test
    void testBuildBookDto4() {
        ModelMapper modelMapper = mock(ModelMapper.class);
        when(modelMapper.map((Object) any(), (Class<BookDto>) any())).thenThrow(new BookNotFoundException("Exception"));
        when(modelMapper.getConfiguration()).thenReturn(new InheritingConfiguration());
        BookServiceImpl bookServiceImpl = new BookServiceImpl(mock(BookRepository.class), modelMapper);

        Book book = new Book();
        book.setAuthor("balu");
        book.setBookId(123L);
        book.setBookTitle("oops");
        book.setEdition(1);
        book.setPrice(111);
        assertThrows(BookNotFoundException.class, () -> bookServiceImpl.buildBookDto(book));
        verify(modelMapper).map((Object) any(), (Class<BookDto>) any());
        verify(modelMapper).getConfiguration();
    }

    @Test
    void testBuildBook2() {
        BookRepository bookRepository = mock(BookRepository.class);
        BookServiceImpl bookServiceImpl = new BookServiceImpl(bookRepository, new ModelMapper());

        BookDto bookDto = new BookDto();
        bookDto.setAuthor("balu");
        bookDto.setBookId(123L);
        bookDto.setBookTitle("oops");
        bookDto.setEdition(1);
        bookDto.setPrice(111);
        Book actualBuildBookResult = bookServiceImpl.buildBook(bookDto);
        assertEquals("balu", actualBuildBookResult.getAuthor());
        assertEquals(111, actualBuildBookResult.getPrice());
        assertEquals(1, actualBuildBookResult.getEdition());
        assertEquals("oops", actualBuildBookResult.getBookTitle());
        assertEquals(123L, actualBuildBookResult.getBookId());
    }

    @Test
    void testBuildBook3() {

        Book book = new Book();
        book.setAuthor("balu");
        book.setBookId(123L);
        book.setBookTitle("oops");
        book.setEdition(1);
        book.setPrice(111);
        ModelMapper modelMapper = mock(ModelMapper.class);
        when(modelMapper.map((Object) any(), (Class<Book>) any())).thenReturn(book);
        when(modelMapper.getConfiguration()).thenReturn(new InheritingConfiguration());
        BookServiceImpl bookServiceImpl = new BookServiceImpl(mock(BookRepository.class), modelMapper);

        BookDto bookDto = new BookDto();
        bookDto.setAuthor("balu");
        bookDto.setBookId(123L);
        bookDto.setBookTitle("oops");
        bookDto.setEdition(1);
        bookDto.setPrice(111);
        assertSame(book, bookServiceImpl.buildBook(bookDto));
        verify(modelMapper).map((Object) any(), (Class<Book>) any());
        verify(modelMapper).getConfiguration();
    }

}

