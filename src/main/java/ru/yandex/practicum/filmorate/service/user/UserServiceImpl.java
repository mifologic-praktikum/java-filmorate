package ru.yandex.practicum.filmorate.service.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.InMemoryUserStorage;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    InMemoryUserStorage userStorage;

    @Override
    public List<User> findUsers() {
        return userStorage.findUsers();
    }

    @Override
    public User findUserById(Long userId) {
        final User user = userStorage.findUserById(userId);
        if (user == null) {
            throw new NotFoundException("User with id=" + userId + " not found");
        }
        return user;
    }

    @Override
    public User addUser(User user) {
        if (user.getName().isEmpty()) {
            user.setName(user.getLogin());
        }
        return userStorage.addUser(user);
    }

    @Override
    public User updateUser(User user) {
        if (userStorage.findUserById(user.getId()) == null) {
            throw new NotFoundException("User with id=" + user.getId() + " not found");
        }
        return userStorage.updateUser(user);
    }

    @Override
    public void addFriend(Long userId, Long friendId) {
        User user = findUserById(userId);
        User friend = findUserById(friendId);
        userStorage.addFriend(user, friend);
    }

    @Override
    public void removeFriend(Long friendId, Long userId) {
        User user = findUserById(userId);
        User friend = findUserById(friendId);
        userStorage.removeFriend(user, friend);
    }

    @Override
    public List<User> findUserFriends(Long userId) {
        return userStorage.findUserFriends(userId);
    }

    @Override
    public List<User> findCommonFriends(Long userId, Long otherUserId) {
        if (userStorage.findUserById(userId) == null || userStorage.findUserById(otherUserId) == null) {
            throw new NotFoundException("User not found");
        }
        return userStorage.findCommonFriends(userId, otherUserId);
    }
}
