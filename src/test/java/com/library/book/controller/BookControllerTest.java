package com.library.book.controller;
import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.library.book.controller.BookController;
import com.library.book.dto.BookDto;
import com.library.book.service.BookServiceImpl;
import java.util.ArrayList;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ContextConfiguration(classes = {BookController.class})
@ExtendWith(SpringExtension.class)
public class BookControllerTest {
    @Autowired
    private BookController bookController;

    @MockBean
    private BookServiceImpl bookServiceImpl;

    @Test
    void testGetAllBooks() throws Exception {
        when(this.bookServiceImpl.getAllBooks()).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/books");
        MockMvcBuilders.standaloneSetup(this.bookController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }
    @Test
    void testGet() throws Exception {
        BookDto bookDto = new BookDto();
        bookDto.setAuthor("avin");
        bookDto.setBookId(111L);
        bookDto.setBookTitle("java");
        bookDto.setEdition(1);
        bookDto.setPrice(110);
        when(this.bookServiceImpl.getBook(anyLong())).thenReturn(bookDto);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/books/{id}", 111L);
        MockMvcBuilders.standaloneSetup(this.bookController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string("{\"bookId\":111,\"bookTitle\":\"java\",\"author\":\"avin\",\"edition\":1,\"price\":110}"));
    }
    @Test
    void testAdd() throws Exception {
        when(this.bookServiceImpl.getAllBooks()).thenReturn(new ArrayList<>());

        BookDto bookDto = new BookDto();
        bookDto.setAuthor("mithra");
        bookDto.setBookId(115L);
        bookDto.setBookTitle("Dr");
        bookDto.setEdition(1);
        bookDto.setPrice(123);
        String content = (new ObjectMapper()).writeValueAsString(bookDto);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/books")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        MockMvcBuilders.standaloneSetup(this.bookController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }
    @Test
    void testDelete() throws Exception {
        doNothing().when(this.bookServiceImpl).deleteBook(anyLong());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/books/{id}", 1);
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(this.bookController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isNoContent());
    }

}
