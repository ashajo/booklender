package se.lexicon.booklender.data;

import org.springframework.data.repository.CrudRepository;
import se.lexicon.booklender.model.Book;

import java.util.List;

public interface BookRepository extends CrudRepository<Book,Integer> {

    List<Book>findByReserved (boolean reserved);
    List<Book>findByAvailable(boolean available);
    List<Book>findByTitle(String title);

}
