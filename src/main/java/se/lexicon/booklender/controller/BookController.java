package se.lexicon.booklender.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import se.lexicon.booklender.dto.BookDto;
import se.lexicon.booklender.exception.DataNotFoundException;
import se.lexicon.booklender.exception.RecordNotFoundException;
import se.lexicon.booklender.service.BookService;

import java.util.List;

import static org.springframework.web.cors.CorsConfiguration.ALL;

@RestController
@RequestMapping("/api/v1/book")
public class BookController {

    BookService bookService;

    @Autowired
    public void setBookService(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping
    public ResponseEntity<List<BookDto>> find(){
        return ResponseEntity.status(HttpStatus.OK).body(bookService.findAll());

    }

    @GetMapping("/{id}")
    public ResponseEntity<BookDto> findById(@PathVariable("id")Integer bookId)throws RecordNotFoundException {

        if(bookId == 0) return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();

            return ResponseEntity.status(HttpStatus.OK).body(bookService.findById(bookId));
        }

    @Transactional
    @PostMapping
    public ResponseEntity<BookDto> save(@RequestBody  BookDto dto){
        if(dto == null)
            if (dto.getBookId() <= 0) return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        return ResponseEntity.status(HttpStatus.CREATED).body(bookService.create(dto));
    }

    @Transactional
    @PutMapping
    public ResponseEntity<BookDto> update(@RequestBody BookDto dto)throws RecordNotFoundException{
        if(dto != null)
             {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
            return ResponseEntity.status(HttpStatus.OK).body(bookService.update(dto));
        }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id")Integer id) throws RecordNotFoundException {

        bookService.delete(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

}
