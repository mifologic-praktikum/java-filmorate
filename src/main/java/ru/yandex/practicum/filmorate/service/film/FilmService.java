package ru.yandex.practicum.filmorate.service.film;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.List;
import java.util.Optional;

public interface FilmService {

    public Optional<Film> findFilmById(long filmId);

    public List<Film> findAllFilms();

    public Film addFilm(Film film);

    public Film updateFilm(Film film);

    public void addLike(long filmId, long userId);

    public void deleteLike(long filmId, long userId);

    public List<Film> findTopFilmsCountByLikes(int count);
}
