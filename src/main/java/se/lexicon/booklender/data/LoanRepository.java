package se.lexicon.booklender.data;

import org.springframework.data.repository.CrudRepository;
import se.lexicon.booklender.model.Loan;

import java.util.List;

public interface LoanRepository extends CrudRepository<Loan,Long> {
    List<Loan>findByLoanId(int userId);
    List<Loan>findByBookId(int bookId);
    List<Loan>findByTerminated(boolean status);
}
