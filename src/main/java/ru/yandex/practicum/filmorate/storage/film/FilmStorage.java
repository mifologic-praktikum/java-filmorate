package ru.yandex.practicum.filmorate.storage.film;

import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;

import java.util.List;
import java.util.Optional;

public interface FilmStorage {

    public Optional<Film> findFilmById(Long filmId);

    public List<Film> findAllFilms();

    public Film addFilm(Film film);

    public Film updateFilm(Film film);

    public void addLike(Long filmId, Long userId);

    public void deleteLike(Long filmId, Long userId);

    public List<Film> findTopFilmsCountByLikes(int count);

}
