package ru.yandex.practicum.filmorate.service.user;

import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface UserService {

    public List<User> findUsers();

    public Optional<User> findUserById(Long userId);

    public User addUser(User user);

    public User updateUser(User user);

    public void addFriend(Long userId, Long friendId);

    public void removeFriend(Long userId, Long friendId);

    public Collection<User> findUserFriends(Long userId);

    public List<User> findCommonFriends(Long userId, Long otherUserId);

}
