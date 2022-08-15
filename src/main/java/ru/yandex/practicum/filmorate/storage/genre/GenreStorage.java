package ru.yandex.practicum.filmorate.storage.genre;

import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;

import java.util.List;
import java.util.Optional;

public interface GenreStorage {

    public Optional<Genre> findGenreById(Long id);

    public List<Genre> findAllGenres();

    void setFilmGenre(Film film);

    void loadFilmGenre(Film film);
}

