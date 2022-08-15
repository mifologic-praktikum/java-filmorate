package ru.yandex.practicum.filmorate.storage.like;

public interface LikeStorage {

    public void addLike(Long filmId, Long userId);

    public void deleteLike(Long filmId, Long userId);
}
