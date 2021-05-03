package se.lexicon.booklender.service;

import se.lexicon.booklender.dto.LibraryUserDto;
import se.lexicon.booklender.exception.DataNotFoundException;

import java.util.List;
import java.util.Optional;

public interface LibraryUserService {
    LibraryUserDto findById(int userId) throws DataNotFoundException;
    LibraryUserDto findByEmail(String email);
    List<LibraryUserDto>findAll();
    LibraryUserDto create(LibraryUserDto dto);
    LibraryUserDto update(LibraryUserDto dto) throws DataNotFoundException;
    void delete(int userId)throws DataNotFoundException;
}
