package se.lexicon.booklender.data;

import org.springframework.data.repository.CrudRepository;
import se.lexicon.booklender.model.Book;

import java.util.List;

public interface BookRepository extends CrudRepository<Book,Integer> {

    List<Book>findByReservedIgnoreCase (boolean reserved);
    List<Book>findByAvailableIgnoreCase(boolean available);
    List<Book>findByTitleIgnoreCase(String title);

}
