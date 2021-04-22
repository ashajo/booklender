package se.lexicon.booklender.data;

import org.springframework.data.repository.CrudRepository;
import se.lexicon.booklender.model.Loan;

import java.util.List;
import java.util.Optional;

public interface LoanRepository extends CrudRepository<Loan,Long> {

    Optional<Loan> findByLoanIdIgnoreCase(Long loanId);
    List<Loan>findByBookIdIgnoreCase(int bookId);
    List<Loan>findByTerminatedIgnoreCase(boolean terminated);
}
