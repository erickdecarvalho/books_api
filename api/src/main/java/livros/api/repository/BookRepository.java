package livros.api.repository;

import livros.api.model.Book;
import livros.api.model.Genre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface BookRepository extends JpaRepository<Book, UUID> {

    @Query("SELECT b FROM Book b WHERE UPPER(b.genre) = UPPER(:genre)")
    Optional<Book> findByGenreIgnoreCase(String genre);
}
