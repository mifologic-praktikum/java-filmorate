package ru.yandex.practicum.filmorate.controller;

import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;
import java.time.Month;

import static org.junit.jupiter.api.Assertions.assertThrows;


public class FilmControllerTest {

    @Test
    public void filmIdCantBeLessThan1() {
        Film film = new Film();
        film.setId(0L);
        film.setName("Nosferatu");
        film.setDescription("Film about vampires");
        film.setReleaseDate(LocalDate.of(1922, Month.MARCH, 15));
        film.setDuration(94);
        FilmController fc = new FilmController();
        assertThrows(ValidationException.class, () -> fc.validate(film));
    }


    @Test
    public void filmDateCantBeEarly1895() {
        Film film = new Film();
        film.setId(0L);
        film.setName("Nosferatu");
        film.setDescription("Film about vampires");
        film.setReleaseDate(LocalDate.of(1822, Month.MARCH, 15));
        film.setDuration(94);
        FilmController fc = new FilmController();
        assertThrows(ValidationException.class, () -> fc.validate(film));
    }
}
