package ru.yandex.practicum.filmorate.service.film;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.BadRequestException;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.film.InMemoryFilmStorage;
import ru.yandex.practicum.filmorate.storage.user.InMemoryUserStorage;

import java.time.LocalDate;
import java.util.List;

@Service
public class FilmServiceImpl implements FilmService {

    @Autowired
    InMemoryFilmStorage filmStorage;
    @Autowired
    InMemoryUserStorage userStorage;

    @Override
    public Film findFilmById(Long filmId) {
        final Film film = filmStorage.findFilmById(filmId);
        if (film == null) {
            throw new NotFoundException("Film with id=" + filmId + " not found");
        }
        return film;
    }

    @Override
    public List<Film> findAllFilms() {
        return filmStorage.findAllFilms();
    }

    @Override
    public Film addFilm(Film film) {
        if (film.getReleaseDate().isBefore(LocalDate.of(1895, 12, 28))) {
            throw new BadRequestException("Дата не релиза не может быть раньше, чем 28 декабря 1895 года");
        }
        return filmStorage.addFilm(film);
    }

    @Override
    public Film updateFilm(Film film) {
        if (filmStorage.findFilmById(film.getId()) == null) {
            throw new NotFoundException("Film with id=" + film.getId() + " not found");
        }
        return filmStorage.updateFilm(film);
    }

    @Override
    public void addLike(Long filmId, Long userId) {
        Film film = filmStorage.findFilmById(filmId);
        User user = userStorage.findUserById(userId);
        filmStorage.addLike(film, user);
    }

    @Override
    public void deleteLike(Long filmId, Long userId) {
        Film film = filmStorage.findFilmById(filmId);
        User user = userStorage.findUserById(userId);
        if (user == null) {
            throw new NotFoundException("User with id=" + userId + " not found");
        }
        filmStorage.deleteLike(film, user);
    }

    @Override
    public List<Film> findTopTenFilmsByLikes(int count) {
        return filmStorage.findTopTenFilmsByLikes(count);
    }
}
