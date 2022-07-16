package ru.yandex.practicum.filmorate.storage.user;

import org.springframework.web.bind.annotation.PathVariable;
import ru.yandex.practicum.filmorate.model.User;

import java.util.List;

public interface UserStorage {

    public List<User> findUsers();

    public User findUserById(Long userId);

    public User addUser(User user);

    public User updateUser(User user);

    public void addFriend(User user, User friend);

    public void removeFriend(User user, User friend);

    public List<User> findUserFriends(Long userId);

    public List<User> findCommonFriends(Long userId, Long otherUserId);
}
