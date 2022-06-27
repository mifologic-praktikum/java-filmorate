package ru.yandex.practicum.filmorate.controller;


import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.validation.FilmValidation;

import javax.validation.Valid;
import java.util.*;

@RestController
@Slf4j
@RequestMapping(value = "/films")
public class FilmController {

    private static int filmId = 0;
    private final Map<Integer, Film> films = new TreeMap<>();
    private final FilmValidation validation = new FilmValidation();

    private static int generateId() {
        return ++filmId;
    }

    @GetMapping
    public Collection<Film> getAllFilms() {
        log.info("Текущее количество фильмов: {}", films.size());
        return films.values();
    }

    @PostMapping
    public Film addFilm(@Valid @RequestBody Film film) {
        film.setId(generateId());
        validation.validateFilm(film);
        films.put(film.getId(), film);
        log.info("Добавлен фильм: {}", film);
        return film;
    }

    @PutMapping
    public Film updateFilm(@Valid @RequestBody Film film) {
        validation.validateFilm(film);
        films.put(film.getId(), film);
        log.info("Обновлён фильм {}", film);
        return film;
    }
}
