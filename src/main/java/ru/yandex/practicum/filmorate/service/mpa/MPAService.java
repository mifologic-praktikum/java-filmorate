package ru.yandex.practicum.filmorate.service.mpa;

import ru.yandex.practicum.filmorate.model.MPA;

import java.util.List;
import java.util.Optional;

public interface MPAService {

    public Optional<MPA> findRatingById(Long id);

    public List<MPA> findAllRatings();
}
