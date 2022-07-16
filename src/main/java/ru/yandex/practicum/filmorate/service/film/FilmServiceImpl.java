package ru.yandex.practicum.filmorate.service.film;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.BadRequestException;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.time.LocalDate;
import java.util.List;

@Service
public class FilmServiceImpl implements FilmService {

    private final FilmStorage filmStorage;
    private final UserStorage userStorage;

    @Autowired
    public FilmServiceImpl(FilmStorage filmStorage, UserStorage userStorage) {
        this.filmStorage = filmStorage;
        this.userStorage = userStorage;
    }

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
        if (film == null) {
            throw new NotFoundException("Film with id=" + filmId + " not found");
        }
        User user = userStorage.findUserById(userId);
        if (user == null) {
            throw new NotFoundException("User with id=" + userId + " not found");
        }
        filmStorage.addLike(film, user);
    }

    @Override
    public void deleteLike(Long filmId, Long userId) {
        Film film = filmStorage.findFilmById(filmId);
        User user = userStorage.findUserById(userId);
        if (film == null) {
            throw new NotFoundException("Film with id=" + filmId + " not found");
        }
        if (user == null) {
            throw new NotFoundException("User with id=" + userId + " not found");
        }
        filmStorage.deleteLike(film, user);
    }

    @Override
    public List<Film> findTopFilmsCountByLikes(int count) {
        return filmStorage.findTopFilmsCountByLikes(count);
    }
}
