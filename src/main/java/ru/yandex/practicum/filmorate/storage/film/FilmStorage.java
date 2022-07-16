package ru.yandex.practicum.filmorate.storage.film;

import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;

import java.util.List;

public interface FilmStorage {

    public Film findFilmById(Long filmId);

    public List<Film> findAllFilms();

    public Film addFilm(Film film);

    public Film updateFilm(Film film);

    public void addLike(Film film, User user);

    public void deleteLike(Film film, User user);

    public List<Film> findTopFilmsCountByLikes(int count);

}
