package livros.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import livros.api.model.Genre;

import java.time.LocalDate;

public record BookDto(
        @NotBlank
        String name,
        @NotNull
        String author,
        Genre genre,
        LocalDate releaseDate
) {
}

