package se.lexicon.booklender.data;

import org.springframework.data.repository.CrudRepository;
import se.lexicon.booklender.model.LibraryUser;

import java.util.Optional;

public interface LibraryUserRepository extends CrudRepository<LibraryUser,Integer> {

    Optional<LibraryUser> findByEmailIgnoreCase(String email);
}
