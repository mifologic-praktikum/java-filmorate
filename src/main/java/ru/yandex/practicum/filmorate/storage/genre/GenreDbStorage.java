package ru.yandex.practicum.filmorate.storage.genre;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Repository
public class GenreDbStorage implements GenreStorage {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public GenreDbStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    @Override
    public Optional<Genre> findGenreById(Long id) {
        final String sqlQuery = "SELECT GENRE_ID, NAME FROM GENRES WHERE GENRE_ID = ?";
        SqlRowSet rs = jdbcTemplate.queryForRowSet(sqlQuery, id);
        if (rs.next()) {
            return Optional.of(new Genre(
                    rs.getLong("GENRE_ID"),
                    rs.getString("NAME")));
        }
        return Optional.empty();
    }

    @Override
    public List<Genre> findAllGenres() {
        final String sqlQuery = "SELECT GENRE_ID, NAME FROM GENRES";
        return jdbcTemplate.query(sqlQuery, this::mapRowToGenre);
    }

    @Override
    public void setFilmGenre(Film film) {
        Long filmId = film.getId();
        jdbcTemplate.update("DELETE FROM FILM_GENRES WHERE FILM_ID = ?", filmId);
        if (film.getGenres() == null || film.getGenres().isEmpty()) {
            return;
        }
        Set<Genre> genres = film.getGenres();
        for (Genre genre : genres) {
            String sqlInsertQuery = "MERGE INTO FILM_GENRES (GENRE_ID, FILM_ID) VALUES (?, ?)";
            jdbcTemplate.update(sqlInsertQuery, genre.getId(), filmId);
        }

    }

    @Override
    public void loadFilmGenre(Film film) {
        Long filmId = film.getId();
        Set<Genre> genres = new LinkedHashSet<>(jdbcTemplate.query("SELECT GENRE_ID, NAME FROM GENRES " +
                "WHERE GENRE_ID IN (SELECT GENRE_ID FROM FILM_GENRES WHERE FILM_ID = ?) ORDER BY GENRE_ID", this::mapRowToGenre, filmId));
        if (!genres.isEmpty()) {
            film.setGenres(genres);
        }
    }


    private Genre mapRowToGenre(ResultSet resultSet, int rowNumber) throws SQLException {
        return new Genre(
                resultSet.getLong("GENRE_ID"),
                resultSet.getString("NAME"));
    }
}
