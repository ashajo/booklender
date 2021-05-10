package se.lexicon.booklender.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import se.lexicon.booklender.dto.LibraryUserDto;
import se.lexicon.booklender.exception.DataNotFoundException;
import se.lexicon.booklender.exception.RecordNotFoundException;
import se.lexicon.booklender.service.LibraryUserService;

import javax.transaction.Transactional;
import java.util.List;

@RestController
@RequestMapping("/api/v1/LibraryUser")
public class LibraryUserController {

    LibraryUserService libraryUserService;

    @Autowired
    public void setLibraryUserService(LibraryUserService libraryUserService) {
        this.libraryUserService = libraryUserService;
    }

    @GetMapping("/")
    public ResponseEntity<List<LibraryUserDto>> findAll(){
            return ResponseEntity.status(HttpStatus.OK).body(libraryUserService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<LibraryUserDto> findById(@PathVariable("id")Integer userId) throws RecordNotFoundException {
        if(userId == 0) return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();

            return ResponseEntity.status(HttpStatus.OK).body(libraryUserService.findById(userId));
        }


    @GetMapping("/email")
    public ResponseEntity<LibraryUserDto> findByEmail(@RequestParam(value = "email") String email){
        return ResponseEntity.status(HttpStatus.OK).body(libraryUserService.findByEmail(email));
    }


    @PostMapping
    public ResponseEntity<LibraryUserDto> save(@RequestBody LibraryUserDto dto){
        if(dto == null) {
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(libraryUserService.create(dto));
    }


    @PutMapping
    public ResponseEntity<LibraryUserDto> update(@RequestBody LibraryUserDto dto) throws RecordNotFoundException {
        if (dto == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.status(HttpStatus.OK).body(libraryUserService.update(dto));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<LibraryUserDto> delete(@PathVariable("id")Integer id) throws RecordNotFoundException {
        if (id < 1) return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        libraryUserService.delete(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

}



