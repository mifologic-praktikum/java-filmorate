package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.service.genre.GenreService;

import java.util.List;
import java.util.Optional;


@RestController
@Slf4j
@RequestMapping(value = "/genres")
public class GenreController {

    private final GenreService genreService;

    @Autowired
    public GenreController(GenreService genreService) {
        this.genreService = genreService;
    }

    @GetMapping
    public List<Genre> findAllGenres() {
        List<Genre> genres = genreService.findAllGenres();
        log.info("Current genres count: {}", genres.size());
        return genres;
    }

    @GetMapping("/{genreId}")
    public Optional<Genre> findGenreById(@PathVariable Long genreId) {
        return genreService.findGenreById(genreId);
    }
}
