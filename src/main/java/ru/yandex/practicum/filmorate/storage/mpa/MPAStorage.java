package ru.yandex.practicum.filmorate.storage.mpa;

import ru.yandex.practicum.filmorate.model.MPA;

import java.util.List;
import java.util.Optional;

public interface MPAStorage {

    public Optional<MPA> findRatingById(Long id);

    public List<MPA> findAllRatings();
}
