package se.lexicon.booklender.service;

import se.lexicon.booklender.dto.BookDto;
import se.lexicon.booklender.exception.DataNotFoundException;
import se.lexicon.booklender.exception.RecordNotFoundException;

import java.util.List;

public interface BookService {
    BookDto findById(int bookId) throws RecordNotFoundException;
    BookDto create(BookDto dto);
    BookDto update(BookDto dto) throws RecordNotFoundException;

    List<BookDto> findAll();
    List<BookDto> findByTitle(String title);
    List<BookDto> findByReserved(boolean reserved);
    List<BookDto> findByAvailable(boolean available);

    void delete(int bookId) throws RecordNotFoundException ;
}

