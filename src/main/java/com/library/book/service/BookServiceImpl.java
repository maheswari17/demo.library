package com.library.book.service;
import com.library.book.dto.BookDto;
import com.library.book.model.Book;
import com.library.book.repository.BookRepository;
import com.library.exceptions.CustomNotFoundException.BookNotFoundException;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BookServiceImpl implements BookService {
    private BookRepository bookRepository;
    private ModelMapper modelMapper;
    public BookServiceImpl(BookRepository bookRepository,ModelMapper modelMapper) {
        this.bookRepository = bookRepository;
        this.modelMapper=modelMapper;
    }

    public BookServiceImpl() {

    }

    @Override
    public List<BookDto> getAllBooks() {
        return bookRepository.findAll().stream().map(this::buildBookDto).collect(Collectors.toList());
    }

    @Override
    public BookDto addBook(BookDto bookDto) {
        Book book= bookRepository.save(buildBook(bookDto));
        return buildBookDto(book);
    }

    @Override
    public BookDto getBook(long id) {
        Optional<Book> book = bookRepository.findById(id);
        if(book.isPresent()) {
            return buildBookDto(book.get());
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

    public BookDto buildBookDto(Book book)   {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
        BookDto bookDto = modelMapper.map(book,BookDto.class);
        return bookDto;
    }

    public Book buildBook(BookDto bookDto)  {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
        Book book = modelMapper.map(bookDto,Book.class);
        return book;
    }


}
