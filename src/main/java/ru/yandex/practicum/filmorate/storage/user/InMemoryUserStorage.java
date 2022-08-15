package ru.yandex.practicum.filmorate.storage.user;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.User;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class InMemoryUserStorage implements UserStorage {

    private Long userId = 0L;
    private final Map<Long, User> users = new HashMap<>();

    @Override
    public List<User> findUsers() {
        return new ArrayList<>(users.values());
    }

    @Override
    public Optional<User> findUserById(Long userId) {
        return Optional.ofNullable(users.get(userId));
    }

    @Override
    public User addUser(User user) {
        user.setId(++userId);
        users.put(user.getId(), user);
        return user;
    }

    @Override
    public User updateUser(User user) {
        users.put(user.getId(), user);
        return user;
    }

    @Override
    public void addFriend(Long userId, Long friendId) {
        users.get(userId).getFriends().add(friendId);
        users.get(userId).getFriends().add(userId);
    }

    @Override
    public void removeFriend(Long userId, Long friendId) {
        users.get(userId).getFriends().remove(friendId);
        users.get(friendId).getFriends().remove(userId);
    }

    @Override
    public Collection<User> findUserFriends(Long userId) {
        Set<Long> friendsIds = users.get(userId).getFriends();
        Collection<User> friends = new HashSet<>();
        for (Long id : friendsIds) {
            friends.add(users.get(id));
        }
        return friends;
    }

    @Override
    public List<User> findCommonFriends(Long userId, Long otherUserId) {
        List<User> userFriends = new ArrayList<>();
        for (Long id : users.get(userId).getFriends()) {
            userFriends.add(users.get(id));
        }
        List<User> otherUserFriends = new ArrayList<>();
        for (Long id : users.get(otherUserId).getFriends()) {
            otherUserFriends.add(users.get(id));
        }
        return userFriends.stream().filter(otherUserFriends::contains)
                .collect(Collectors.toList());
    }
}
