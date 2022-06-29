package ru.yandex.practicum.filmorate.controller;


import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.validation.DataValidation;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.*;

@RestController
@Slf4j
@RequestMapping(value = "/films")
public class FilmController extends DataValidation<Film> {

    private static Integer filmId = 0;
    private final Map<Integer, Film> films = new TreeMap<>();

    private static Integer generateId() {
        return ++filmId;
    }

    @GetMapping
    public List<Film> getAllFilms() {
        log.info("Текущее количество фильмов: {}", films.size());
        return new ArrayList<>(films.values());
    }

    @PostMapping
    public Film addFilm(@Valid @RequestBody Film film) {
        film.setId(generateId());
        validate(film);
        films.put(film.getId(), film);
        log.info("Добавлен фильм: {}", film);
        return film;
    }

    @PutMapping
    public Film updateFilm(@Valid @RequestBody Film film) {
        validate(film);
        films.put(film.getId(), film);
        log.info("Обновлён фильм {}", film);
        return film;
    }

    @Override
    public void validate(Film film) {
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
