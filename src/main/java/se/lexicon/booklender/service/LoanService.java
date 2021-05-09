package se.lexicon.booklender.service;

import se.lexicon.booklender.dto.LoanDto;
import se.lexicon.booklender.exception.DataNotFoundException;
import se.lexicon.booklender.exception.RecordNotFoundException;

import java.util.List;

public interface LoanService {
    LoanDto findById(long loanId) throws RecordNotFoundException;
    LoanDto create(LoanDto dto);
    LoanDto update(LoanDto dto) throws RecordNotFoundException;
    List<LoanDto>findAll();
    List<LoanDto>findByBookId(int bookId);
    List<LoanDto>findByUserId(int userId);
    List<LoanDto>findByTerminated(boolean terminated);
    void delete(long loanId) throws RecordNotFoundException;
}
