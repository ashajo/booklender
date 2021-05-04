package se.lexicon.booklender.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import se.lexicon.booklender.dto.BookDto;
import se.lexicon.booklender.exception.DataNotFoundException;
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

    @GetMapping("/find")
    public ResponseEntity<List<BookDto>> find(
            @RequestParam(name = "type", defaultValue = ALL)final String type,
            @RequestParam(name = "value", defaultValue = ALL)final String value){
        switch (type.toUpperCase()){
            case "TITLE":
                return ResponseEntity.ok(bookService.findByTitle(value));
            case "AVAILABLE":
                return ResponseEntity.ok(bookService.findByAvailable(Boolean.parseBoolean(value)));
            case "RESERVED":
                return ResponseEntity.ok(bookService.findByReserved(Boolean.parseBoolean(value)));
            case ALL:
                if (bookService.findAll().isEmpty()) return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
                return ResponseEntity.ok(bookService.findAll());
            default:
                return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookDto> findById(@PathVariable("id")Integer bookId){
        if(bookId <= 0) return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        try {
            return ResponseEntity.status(HttpStatus.OK).body(bookService.findById(bookId));
        } catch (DataNotFoundException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @Transactional
    @PostMapping("/")
    public ResponseEntity<BookDto> save(@RequestBody BookDto dto){
        if(dto == null)
            if (dto.getBookId() <= 0) return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        return ResponseEntity.status(HttpStatus.CREATED).body(bookService.create(dto));
    }

    @Transactional
    @PutMapping("/")
    public ResponseEntity<BookDto> update(@RequestBody BookDto dto){
        if(dto != null)
            if (dto.getBookId() < 1) return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        try {
            return ResponseEntity.status(HttpStatus.OK).body(bookService.update(dto));
        } catch (DataNotFoundException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id")Integer id) throws DataNotFoundException {

        bookService.delete(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

}
