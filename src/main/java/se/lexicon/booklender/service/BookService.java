package se.lexicon.booklender.service;

import se.lexicon.booklender.dto.BookDto;
import se.lexicon.booklender.exception.DataNotFoundException;

import java.util.List;

public interface BookService {
    BookDto findById(int bookId) throws DataNotFoundException;
    BookDto create(BookDto dto);
    BookDto update(BookDto dto) throws DataNotFoundException ;

    List<BookDto> findAll();
    List<BookDto> findByTitle(String title);
    List<BookDto> findByReserved(boolean reserved);
    List<BookDto> findByAvailable(boolean available);

    void delete(int bookId) throws DataNotFoundException ;
}

