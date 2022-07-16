package ru.yandex.practicum.filmorate.service.user;

import ru.yandex.practicum.filmorate.model.User;

import java.util.List;

public interface UserService {

    public List<User> findUsers();

    public User findUserById(Long userId);

    public User addUser(User user);

    public User updateUser(User user);

    public void addFriend(Long userId, Long friendId);

    public void removeFriend(Long userId, Long friendId);

    public List<User> findUserFriends(Long userId);

    public List<User> findCommonFriends(Long userId, Long otherUserId);

}
