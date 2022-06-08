package com.library.book.controller;
import com.library.book.dto.BookDto;
import com.library.book.service.BookServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/books")
public class BookController {
    BookServiceImpl bookServiceable;
    public BookController(BookServiceImpl bookServiceable) {
        this.bookServiceable = bookServiceable;
    }

    @GetMapping
    public List<BookDto> getAllBooks() {
        return bookServiceable.getAllBooks();
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookDto> get(@PathVariable long id) {
        return new ResponseEntity<>(bookServiceable.getBook(id), HttpStatus.OK);
    }

    @PostMapping
    public BookDto add(@RequestBody BookDto bookDto) {
        return bookServiceable.addBook(bookDto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(reason = "book deleted",code = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Integer id) {
        bookServiceable.deleteBook(id);
    }
}
