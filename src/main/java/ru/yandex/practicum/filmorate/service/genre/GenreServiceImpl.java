package ru.yandex.practicum.filmorate.service.genre;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.storage.genre.GenreDbStorage;

import java.util.List;
import java.util.Optional;

@Service
public class GenreServiceImpl implements GenreService {

    private final GenreDbStorage genreStorage;

    @Autowired
    public GenreServiceImpl(GenreDbStorage genreStorage) {
        this.genreStorage = genreStorage;
    }

    @Override
    public Optional<Genre> findGenreById(Long genreId) {
        Optional<Genre> genre = genreStorage.findGenreById(genreId);
        if (genre.isEmpty()) {
            throw new NotFoundException("Genre with genreId=" + genreId + " not found");
        }
        return genre;
    }

    @Override
    public List<Genre> findAllGenres() {
        return genreStorage.findAllGenres();
    }
}
