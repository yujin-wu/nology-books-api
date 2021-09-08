package com.example.appengine.springboot;

import ch.qos.logback.classic.jmx.MBeanUtil;
import com.example.appengine.springboot.models.Book;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import com.example.appengine.springboot.BookDto;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    public BookDto getById(Long id) {
        Book book = bookRepository.findById(id).orElseThrow(() ->
                new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Book with id " + id + " not found"
                )
        );
        BookDto dto = new BookDto();
        BeanUtils.copyProperties(book, dto);
        return dto;
    }

    public BookDto create(BookDto data) {
        Book book = new Book();
        BeanUtils.copyProperties(data, book);
        book.setId(null);
        Book result = bookRepository.save(book);
        BookDto resultDto = new BookDto();
        BeanUtils.copyProperties(result, resultDto);
        return resultDto;
    }


    public BookDto save(BookDto data, Long id) {
        if (id == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        Book book = bookRepository.findById(data.getId())
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND)
                );

        if (data.getRating() != null) {
            book.setRating(data.getRating());
        }
        if (data.getFinished() != null) {
            book.setFinished(data.getFinished());
        }
        book = bookRepository.save(book);
        BookDto resultDto = new BookDto();
        BeanUtils.copyProperties(book, resultDto);
        return resultDto;
    }

    public List<BookDto> getAll(Optional<Boolean> isFinished) {
        List<Book> books;
        if (isFinished.isEmpty()) {
            books = bookRepository.findAll();
        } else {
            books = bookRepository.findAllByFinished(isFinished.get());
        }
        return books.stream().map(b -> {
            BookDto dto = new BookDto();
            BeanUtils.copyProperties(b, dto);
            return dto;
        }).collect(Collectors.toList());
    }
}