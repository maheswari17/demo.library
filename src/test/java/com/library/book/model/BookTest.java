package com.library.book.model;

import com.library.authentication.model.Authentication;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BookTest {
    @Test
    void testConstructor() {
        Book book = new Book();
        book.setBookId(123L);
        book.setBookTitle("aaa");
        book.setAuthor("ani");
        book.setEdition(1);
        book.setPrice(111);
        assertEquals(123L, book.getBookId());
        assertEquals("aaa", book.getBookTitle());
        assertEquals("ani", book.getAuthor());
        assertEquals(1, book.getEdition());
        assertEquals(111, book.getPrice());
    }
    @Test
    void testConstructor2() {
        Book book = new Book(123L, "aaa","ani",1,111);
        assertEquals(123L, book.getBookId());
        assertEquals("aaa", book.getBookTitle());
        assertEquals("ani",book.getAuthor());
        assertEquals(1,book.getEdition());
        assertEquals(111,book.getPrice());
    }
}

