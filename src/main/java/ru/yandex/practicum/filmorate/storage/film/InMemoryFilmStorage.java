package ru.yandex.practicum.filmorate.storage.film;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class InMemoryFilmStorage implements FilmStorage {

    private static Long filmId = 0L;
    private final Map<Long, Film> films = new TreeMap<>();

    private static Long generateId() {
        return ++filmId;
    }

    @Override
    public Film findFilmById(Long filmId) {
        return films.get(filmId);
    }

    @Override
    public List<Film> findAllFilms() {
        return new ArrayList<>(films.values());
    }

    @Override
    public Film addFilm(Film film) {
        film.setId(generateId());
        films.put(film.getId(), film);
        return film;
    }

    @Override
    public Film updateFilm(Film film) {
        films.put(film.getId(), film);
        return film;
    }

    @Override
    public void addLike(Film film, User user) {
        film.getUsersLikes().add(user.getId());
    }

    @Override
    public void deleteLike(Film film, User user) {
        film.getUsersLikes().remove(user.getId());
    }

    @Override
    public List<Film> findTopTenFilmsByLikes(int count) {
        return films.values().stream()
                .sorted(Comparator.comparing(f -> f.getUsersLikes().size(), Comparator.reverseOrder()))
                .limit(count)
                .collect(Collectors.toList());
    }
}
