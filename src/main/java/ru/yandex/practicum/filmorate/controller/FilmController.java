package ru.yandex.practicum.filmorate.controller;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.film.FilmService;
import ru.yandex.practicum.filmorate.validation.DataValidation;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.*;

@RestController
@Slf4j
@RequestMapping(value = "/films")
public class FilmController implements DataValidation<Film> {

    private final FilmService filmService;

    @Autowired
    public FilmController(FilmService filmService) {
        this.filmService = filmService;
    }

    @GetMapping
    public List<Film> findAllFilms() {
        List<Film> films = filmService.findAllFilms();
        log.info("Current films count: {}", films.size());
        return films;
    }

    @GetMapping("/{filmId}")
    public Film findFilmById(@PathVariable Long filmId) {
        return filmService.findFilmById(filmId);
    }

    @PostMapping
    public Film addFilm(@Valid @RequestBody Film film) {
        log.info("Add film: {}", film);
        return filmService.addFilm(film);
    }

    @PutMapping
    public Film updateFilm(@Valid @RequestBody Film film) {
        log.info("Update film {}", film);
        return filmService.updateFilm(film);
    }

    @PutMapping("/{filmId}/like/{userId}")
    public void addLike(@PathVariable Long filmId, @PathVariable Long userId) {
        log.info("Add like for film with id=" + filmId);
        filmService.addLike(filmId, userId);
    }

    @DeleteMapping("/{filmId}/like/{userId}")
    public void deleteLike(@PathVariable Long filmId, @PathVariable Long userId) {
        log.info("Delete like for film with id=" + filmId);
        filmService.deleteLike(filmId, userId);
    }

    @GetMapping("/popular")
    public List<Film> findTopFilmsCountByLikes(@RequestParam(defaultValue = "10", required = false) int count) {
        log.info("Get " + count + " films by likes");
        return filmService.findTopFilmsCountByLikes(count);
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
