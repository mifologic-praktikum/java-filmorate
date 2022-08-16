package ru.yandex.practicum.filmorate.storage.user;

import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface UserStorage {

    public List<User> findUsers();

    public Optional<User> findUserById(long userId);

    public User addUser(User user);

    public User updateUser(User user);

    public void addFriend(long userId, long friendId);

    public void removeFriend(long userId, long friendId);

    public Collection<User> findUserFriends(long userId);

    public List<User> findCommonFriends(long userId, long otherUserId);
}
