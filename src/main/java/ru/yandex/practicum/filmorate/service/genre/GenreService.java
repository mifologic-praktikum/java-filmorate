package ru.yandex.practicum.filmorate.service.genre;

import ru.yandex.practicum.filmorate.model.Genre;

import java.util.List;
import java.util.Optional;

public interface GenreService {

    public Optional<Genre> findGenreById(long id);

    public List<Genre> findAllGenres();
}
