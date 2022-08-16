package ru.yandex.practicum.filmorate.service.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserStorage userStorage;

    @Autowired
    public UserServiceImpl(@Qualifier("UserDbStorage") UserStorage userStorage) {
        this.userStorage = userStorage;
    }

    @Override
    public List<User> findUsers() {
        return userStorage.findUsers();
    }

    @Override
    public Optional<User> findUserById(long userId) {
        final Optional<User> user = userStorage.findUserById(userId);
        if (user.isEmpty()) {
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
        if (userStorage.findUserById(user.getId()).isEmpty()) {
            throw new NotFoundException("User with id=" + user.getId() + " not found");
        }
        return userStorage.updateUser(user);
    }

    @Override
    public void addFriend(long userId, long friendId) {
        if (userId < 1 || friendId < 1) {
            throw new NotFoundException("User not found");
        }
        userStorage.addFriend(userId, friendId);
    }

    @Override
    public void removeFriend(long userId, long friendId) {
        Optional<User> user = findUserById(userId);
        Optional<User> friend = findUserById(friendId);
        if (user.isPresent() & friend.isPresent()) {
            userStorage.removeFriend(userId, friendId);
        }
    }

    @Override
    public Collection<User> findUserFriends(long userId) {
        if (userId < 1) {
            throw new NotFoundException("User not found");
        }
        return userStorage.findUserFriends(userId);
    }

    @Override
    public List<User> findCommonFriends(long userId, long otherUserId) {
        if (userStorage.findUserById(userId).isEmpty() || userStorage.findUserById(otherUserId).isEmpty()) {
            throw new NotFoundException("User not found");
        }
        return userStorage.findCommonFriends(userId, otherUserId);
    }
}
