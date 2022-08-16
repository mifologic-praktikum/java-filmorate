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

    public GenreDbStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Optional<Genre> findGenreById(long id) {
        String sqlQuery = "SELECT GENRE_ID, NAME FROM GENRES WHERE GENRE_ID = ?";
        SqlRowSet rs = jdbcTemplate.queryForRowSet(
                sqlQuery, id);
        if (rs.next()) {
            return Optional.of(new Genre(
                    rs.getlong("GENRE_ID"),
                    rs.getString("NAME")));
        }
        return Optional.empty();
    }

    @Override
    public List<Genre> findAllGenres() {
        String sqlQuery = "SELECT GENRE_ID, NAME FROM GENRES";
        return jdbcTemplate.query(sqlQuery, this::mapRowToGenre);
    }

    private Genre mapRowToGenre(ResultSet resultSet, int rowNumber) throws SQLException {
        return new Genre(
                resultSet.getlong("GENRE_ID"),
                resultSet.getString("NAME"));
    }
}
