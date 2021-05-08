package se.lexicon.booklender.service;

import se.lexicon.booklender.dto.LibraryUserDto;
import se.lexicon.booklender.exception.DataNotFoundException;
import se.lexicon.booklender.exception.RecordNotFoundException;

import java.util.List;
import java.util.Optional;

public interface LibraryUserService {
    LibraryUserDto findById(int userId) throws RecordNotFoundException;
    LibraryUserDto findByEmail(String email);
    List<LibraryUserDto>findAll();
    LibraryUserDto create(LibraryUserDto dto);
    LibraryUserDto update(LibraryUserDto dto) throws RecordNotFoundException;
    void delete(int userId)throws RecordNotFoundException;
}
