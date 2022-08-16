package ru.yandex.practicum.filmorate.storage.like;

public interface LikeStorage {

    public void addLike(long filmId, long userId);

    public void deleteLike(long filmId, long userId);
}
