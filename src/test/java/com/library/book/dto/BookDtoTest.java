package com.library.book.dto;
import com.library.book.dto.BookDto;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class BookDtoTest {
    @Test
    void testConstructor() {
        BookDto actualBookDto = new BookDto();
        actualBookDto.setAuthor("Anu");
        actualBookDto.setBookId(123L);
        actualBookDto.setBookTitle("oops");
        actualBookDto.setEdition(1);
        actualBookDto.setPrice(100);
        assertEquals("Anu", actualBookDto.getAuthor());
        assertEquals(123L, actualBookDto.getBookId());
        assertEquals("oops", actualBookDto.getBookTitle());
        assertEquals(1, actualBookDto.getEdition());
        assertEquals(100, actualBookDto.getPrice());
    }
}
