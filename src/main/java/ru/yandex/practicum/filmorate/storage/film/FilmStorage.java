package ru.yandex.practicum.filmorate.storage.film;

import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;

import java.util.List;
import java.util.Optional;

public interface FilmStorage {

    public Optional<Film> findFilmById(long filmId);

    public List<Film> findAllFilms();

    public Film addFilm(Film film);

    public Film updateFilm(Film film);

    void setFilmGenre(Film film);

    void loadFilmGenre(Film film);

    public List<Film> findTopFilmsCountByLikes(int count);

}
