package se.lexicon.booklender.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import se.lexicon.booklender.data.LibraryUserRepository;
import se.lexicon.booklender.dto.LibraryUserDto;
import se.lexicon.booklender.exception.DataNotFoundException;
import se.lexicon.booklender.exception.RecordNotFoundException;
import se.lexicon.booklender.model.LibraryUser;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class LibraryUserServiceImpl implements LibraryUserService {

    LibraryUserRepository libraryUserRepository;
    ModelMapper modelMapper;

    @Autowired
    public void setLibraryUserRepository(LibraryUserRepository libraryUserRepository) {
        this.libraryUserRepository = libraryUserRepository;
    }

    @Autowired
    public void setModelMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public LibraryUserDto findById(int userId) throws RecordNotFoundException {
        if (userId == 0) throw new IllegalArgumentException("Id should not be empty");
        return modelMapper.map(libraryUserRepository.findById(userId)
                .orElseThrow(() -> new RecordNotFoundException("LibraryUserDto not found")), LibraryUserDto.class);
    }

    @Override
    public LibraryUserDto findByEmail(String email) {
        if (email == null) throw new IllegalArgumentException("Email should not be null");
        return modelMapper.map(libraryUserRepository.findByEmailIgnoreCase(email), LibraryUserDto.class);
    }

    @Override
    public List<LibraryUserDto> findAll() {
        List<LibraryUser> libraryUserList = new ArrayList<>();
        libraryUserRepository.findAll().iterator().forEachRemaining(libraryUserList::add);
        return libraryUserList.stream().map(libraryUser -> modelMapper.map(libraryUser, LibraryUserDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public LibraryUserDto create(LibraryUserDto dto) {
        if (dto == null) throw new IllegalArgumentException("LibraryUserDto object not found");
        if (dto.getUserId() != 0) throw new IllegalArgumentException("LibraryUserDto should be empty");
        return modelMapper.map(libraryUserRepository.save(modelMapper.map(dto, LibraryUser.class)), LibraryUserDto.class);
    }

    @Override
    public LibraryUserDto update(LibraryUserDto dto) throws RecordNotFoundException {
        if (dto == null) throw new IllegalArgumentException("The dto object not found");
        if (dto.getUserId() < 1) throw new IllegalArgumentException("LibraryUserDto is not valid");
        Optional<LibraryUser> bookOptional = libraryUserRepository.findById(modelMapper.map(dto, LibraryUser.class).getUserId());
        if (bookOptional.isPresent()) {
            return modelMapper.map(libraryUserRepository.save(modelMapper.map(dto, LibraryUser.class)), LibraryUserDto.class);
        } else throw new RecordNotFoundException("LibraryUserDto");
    }

    @Override
    public void delete(int userId) throws RecordNotFoundException {
        if (userId < 1) throw new IllegalArgumentException("The id is not valid");
        libraryUserRepository.delete(modelMapper.map(libraryUserRepository.findById(userId)
                .orElseThrow(() -> new RecordNotFoundException("Id")), LibraryUser.class));
    }
}



