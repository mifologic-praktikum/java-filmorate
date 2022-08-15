package ru.yandex.practicum.filmorate.service.mpa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.MPA;
import ru.yandex.practicum.filmorate.storage.mpa.MPADbStorage;

import java.util.List;
import java.util.Optional;

@Service
public class MPAServiceImpl implements MPAService {
    MPADbStorage mpaStorage;

    @Autowired
    public MPAServiceImpl(MPADbStorage mpaStorage) {
        this.mpaStorage = mpaStorage;
    }

    @Override
    public Optional<MPA> findRatingById(Long ratingId) {
        Optional<MPA> mpaRating = mpaStorage.findRatingById(ratingId);
        if (mpaRating.isEmpty() || ratingId < 1) {
            throw new NotFoundException("MPA with ratingId=" + ratingId + " not found");
        }
        return mpaRating;
    }

    @Override
    public List<MPA> findAllRatings() {
        return mpaStorage.findAllRatings();
    }
}