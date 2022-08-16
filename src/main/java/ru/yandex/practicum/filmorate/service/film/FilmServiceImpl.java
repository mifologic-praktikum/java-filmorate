package ru.yandex.practicum.filmorate.service.film;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.BadRequestException;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;
import ru.yandex.practicum.filmorate.storage.like.LikeStorage;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class FilmServiceImpl implements FilmService {

    private final FilmStorage filmStorage;
    private final UserStorage userStorage;
    private final LikeStorage likeStorage;

    @Autowired
    public FilmServiceImpl(@Qualifier("FilmDbStorage") FilmStorage filmStorage,
                           @Qualifier("UserDbStorage") UserStorage userStorage,
                           @Qualifier("LikeDbStorage")LikeStorage likeStorage) {
        this.filmStorage = filmStorage;
        this.userStorage = userStorage;
        this.likeStorage = likeStorage;
    }

    @Override
    public Optional<Film> findFilmById(long filmId) {
        if (filmId < 1) {
            throw new NotFoundException("Film with id=" + filmId + " not found");
        }
        return filmStorage.findFilmById(filmId);
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
        if (film.getMpa() == null) {
            throw new BadRequestException("Рейтинг должен быть задан");
        }
        return filmStorage.addFilm(film);
    }

    @Override
    public Film updateFilm(Film film) {
        if (film.getId() < 1) {
            throw new NotFoundException("Film with id=" + film.getId() + " not found");
        }
        return filmStorage.updateFilm(film);
    }

    @Override
    public void addLike(long filmId, long userId) {
        if (filmId < 1) {
            throw new NotFoundException("Film with id=" + filmId + " not found");
        }
        Optional<User> user = userStorage.findUserById(userId);
        if (user.isPresent()) {
            likeStorage.addLike(filmId, userId);
        } else {
            throw new NotFoundException("User with id=" + userId + " not found");
        }
    }

    @Override
    public void deleteLike(long filmId, long userId) {
        if (filmId < 1) {
            throw new NotFoundException("Film with id=" + filmId + " not found");
        }
        Optional<User> user = userStorage.findUserById(userId);
        if (user.isPresent()) {
            likeStorage.addLike(filmId, userId);
        } else {
            throw new NotFoundException("User with id=" + userId + " not found");
        }
    }

    @Override
    public List<Film> findTopFilmsCountByLikes(int count) {
        return filmStorage.findTopFilmsCountByLikes(count);
    }
}
