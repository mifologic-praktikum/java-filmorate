package ru.yandex.practicum.filmorate.service.film;

import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.List;

public interface FilmService {

    public Film findFilmById(Long filmId);

    public List<Film> findAllFilms();

    public Film addFilm(Film film);

    public Film updateFilm(Film film);

    public void addLike(Long filmId, Long userId);

    public void deleteLike(Long filmId, Long userId);

    public List<Film> findTopTenFilmsByLikes(int count);
}
