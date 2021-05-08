package se.lexicon.booklender.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import se.lexicon.booklender.data.BookRepository;
import se.lexicon.booklender.dto.BookDto;
import se.lexicon.booklender.exception.DataNotFoundException;
import se.lexicon.booklender.exception.RecordNotFoundException;
import se.lexicon.booklender.model.Book;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BookServiceImpl implements BookService{

    BookRepository bookRepository;
    ModelMapper     modelMapper;

    @Autowired
    public void setModelMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Autowired
    public void setBookRepository(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public BookDto findById(int bookId) throws RecordNotFoundException {

        return modelMapper.map(bookRepository.findById(bookId)
                .orElseThrow(() -> new RecordNotFoundException("BookDto not found")),BookDto.class);
    }

    @Override
    public BookDto create(BookDto dto) {

        return modelMapper.map(bookRepository.save(modelMapper.map(dto, Book.class)),BookDto.class);
    }

    @Override
    public BookDto update(BookDto dto) throws RecordNotFoundException{
        if (dto == null) throw new IllegalArgumentException("The dto object not found");
        if (dto.getBookId() < 1) throw new IllegalArgumentException("The BookDto is not valid");

        Optional<Book> bookOptional = bookRepository.findById(modelMapper.map(dto,Book.class).getBookId());
        if (bookOptional.isPresent()){
            return modelMapper.map(bookRepository.save(modelMapper.map(dto,Book.class)),BookDto.class);
        }
        else throw new RecordNotFoundException("BookDto");
    }

    @Override
    public void delete(int bookId) throws RecordNotFoundException{
        if (bookId <1)throw new IllegalArgumentException("The id is not valid");

        bookRepository.delete(modelMapper.map(bookRepository.findById(bookId)
                .orElseThrow(()->new RecordNotFoundException("Id ")), Book.class));
    }

    @Override
    public List<BookDto> findAll() {
        List<Book> bookList = new ArrayList<>();
        bookRepository.findAll().iterator().forEachRemaining(bookList::add);

        return bookList.stream()
                .map(book -> modelMapper.map(book,BookDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<BookDto> findByTitle(String title) {
        if(title == null)throw new IllegalArgumentException("The field is empty");

        return bookRepository.findByTitleIgnoreCase(title)
                .stream().map(book->modelMapper.map(book,BookDto.class)).collect(Collectors.toList());
    }

    @Override
    public List<BookDto> findByReserved(boolean reserved) {
        return bookRepository.findByReservedIgnoreCase(reserved)
                .stream().map(book -> modelMapper.map(book,BookDto.class)).collect(Collectors.toList());
    }

    @Override
    public List<BookDto> findByAvailable(boolean available) {
        return bookRepository.findByAvailableIgnoreCase(available)
                .stream().map(book -> modelMapper.map(book, BookDto.class)).collect(Collectors.toList());
    }
}
