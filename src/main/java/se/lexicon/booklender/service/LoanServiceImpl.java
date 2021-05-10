package se.lexicon.booklender.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import se.lexicon.booklender.data.BookRepository;
import se.lexicon.booklender.data.LibraryUserRepository;
import se.lexicon.booklender.data.LoanRepository;
import se.lexicon.booklender.dto.BookDto;
import se.lexicon.booklender.dto.LibraryUserDto;
import se.lexicon.booklender.dto.LoanDto;
import se.lexicon.booklender.exception.ArgumentException;
import se.lexicon.booklender.exception.DataNotFoundException;
import se.lexicon.booklender.exception.RecordNotFoundException;
import se.lexicon.booklender.model.Loan;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class LoanServiceImpl implements LoanService {

    LibraryUserRepository libraryUserRepository;
    LoanRepository loanRepository;
    BookRepository bookRepository;
    ModelMapper modelMapper;

    @Autowired
    public void setLibraryUserRepository(LibraryUserRepository libraryUserRepository) {
        this.libraryUserRepository = libraryUserRepository;
    }

    @Autowired
    public void setLoanRepository(LoanRepository loanRepository) {
        this.loanRepository = loanRepository;
    }

    @Autowired
    public void setBookRepository(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Autowired
    public void setModelMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public LoanDto findById(long loanId) throws RecordNotFoundException {
        if (loanId == 0) throw new ArgumentException("Id should not be empty");
        return modelMapper.map(loanRepository.findById(loanId)
                .orElseThrow(() -> new RecordNotFoundException("LoanDto not found")), LoanDto.class);
    }

@Transactional
    @Override
    public LoanDto create(LoanDto dto) {
        return modelMapper.map(loanRepository.save(modelMapper.map(dto, Loan.class)), LoanDto.class);
    }

@Transactional
    @Override
    public LoanDto update(LoanDto dto) throws RecordNotFoundException {
        if (dto == null) throw new ArgumentException("Bookdto object not null");
        if (dto.getLoanId() < 1) throw new IllegalArgumentException("BookId is not null");
        Optional<Loan>optionalLoan=loanRepository.findById(dto.getLoanId());
        if (optionalLoan.isPresent()) {
            return modelMapper.map(loanRepository.save(modelMapper.map(dto, Loan.class)), LoanDto.class);
        }else {
            throw new RecordNotFoundException("dto not found");
        }
    }

    @Override
    public List<LoanDto> findAll() {
        List<Loan> loanList = new ArrayList<>();
        loanRepository.findAll().iterator().forEachRemaining(loanList::add);
        return loanList.stream().map(loan -> modelMapper.map(loan, LoanDto.class)).collect(Collectors.toList());
    }


    @Override
    public List<LoanDto> findByBookId(int bookId) {
        if (bookId < 1) throw new ArgumentException("bookId not null");

        List<Loan> loanList = new ArrayList<>();
        loanRepository.findByBookId(bookId).iterator().forEachRemaining(loanList::add);

        List<LoanDto> loanDtoList = loanRepository.findByBookId(bookId)
                .stream().map(book -> modelMapper.map(book, LoanDto.class)).collect(Collectors.toList());

        return loanDtoList;

    }

    @Override
    public List<LoanDto> findByUserId(int userId) {
        if (userId < 1) throw new ArgumentException("The field is empty");

        List<Loan> loanList = new ArrayList<>();
        loanRepository.findByBookId(userId).iterator().forEachRemaining(loanList::add);

        List<LoanDto> loanDtoList = loanRepository.findByBookId(userId)
                .stream().map(book -> modelMapper.map(book, LoanDto.class)).collect(Collectors.toList());

        return loanDtoList;

    }

    @Override
    public List<LoanDto> findByTerminated(boolean terminated) {
        return loanRepository.findByTerminated(terminated)
                .stream().map(book -> modelMapper.map(book, LoanDto.class)).collect(Collectors.toList());
    }

    @Override
    public void delete(long loanId) throws RecordNotFoundException {
        if (loanId < 1) throw new ArgumentException("The id is not valid");

        loanRepository.delete(modelMapper.map(loanRepository.findById(loanId)
                .orElseThrow(() -> new RecordNotFoundException("Id ")), Loan.class));
    }
}


