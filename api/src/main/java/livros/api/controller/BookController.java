package livros.api.controller;

import jakarta.validation.Valid;
import livros.api.dto.BookDto;
import livros.api.model.Book;
import livros.api.model.Genre;
import livros.api.repository.BookRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/livros")
public class BookController {

    private final BookRepository bookRepository;

    public BookController(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @GetMapping
    public ResponseEntity<List<Book>> getAllLivros(){
        return ResponseEntity.status(HttpStatus.OK).body(bookRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getBookById(@PathVariable(value="id") UUID id ){
        Optional<Book> book = bookRepository.findById(id);
        if(book.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Livro nao encontrado");
        }else {
            return ResponseEntity.status(HttpStatus.OK).body(book.get());
        }
    }

    @PostMapping
    public ResponseEntity<Book> addBook(@RequestBody @Valid BookDto bookDto){
        var book =  new Book();
        BeanUtils.copyProperties(bookDto, book);
        return ResponseEntity.status(HttpStatus.CREATED).body(bookRepository.save(book));
    }

    @DeleteMapping("/{id}")
    public  ResponseEntity<String> deleteBook(@PathVariable(value="id") UUID id){
        Optional<Book> book = bookRepository.findById(id);
        if(book.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Livro nao encontrado");
        }else {
            bookRepository.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK).body("Livro excluido com sucesso!");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateBook(@PathVariable(value = "id") UUID id, @RequestBody @Valid BookDto bookDto){
        Optional<Book> book = bookRepository.findById(id);
        if(book.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Livro nao encontrado");
        }else {
            var bookModel = book.get();
            BeanUtils.copyProperties(bookDto, bookModel);
            return ResponseEntity.status(HttpStatus.OK).body(bookRepository.save(bookModel));
        }
    }

    @GetMapping("/genero/{genre}")
    public ResponseEntity<Optional<Book>> getBooksByGenre(@PathVariable String genre) {
        Optional<Book> books = bookRepository.findByGenreIgnoreCase(genre);

        if (books.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(books);
    }
}
