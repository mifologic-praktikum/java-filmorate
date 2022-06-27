package ru.yandex.practicum.filmorate.validation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;

public class FilmValidation {

    private final static Logger log = LoggerFactory.getLogger(User.class);

    public void validateFilm(Film film) {
        if(film.getId() < 1) {
            log.error("Id меньше 1");
            throw new ValidationException("id не может быть меньше 1");
        }
        if (film.getName().equals("")) {
            log.error("film.name не может быть null");
            throw new ValidationException("Название фильма не может быть null");
        }
        if(film.getReleaseDate().isBefore(LocalDate.of(1895, 12, 28))) {
            log.error("Дата релиза раньше, чем 28 декабря 1895");
            throw new ValidationException("Дата не релиза не может быть раньше, чем 28 декабря 1895 года");
        }
    }
}
