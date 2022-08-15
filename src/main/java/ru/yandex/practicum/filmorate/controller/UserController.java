package ru.yandex.practicum.filmorate.controller;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.user.UserService;
import ru.yandex.practicum.filmorate.validation.DataValidation;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.*;

@RestController
@Slf4j
@RequestMapping(value = "/users")
public class UserController implements DataValidation<User> {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<User> findUsers() {
        List<User> users = userService.findUsers();
        log.info("Current users count: {}", users.size());
        log.info("{}", users);
        return users;
    }

    @GetMapping("/{userId}")
    public Optional<User> findUserById(@PathVariable Long userId) {
        log.info("Get user by id={}", userId);
        return userService.findUserById(userId);
    }

    @PostMapping
    public User addUser(@Valid @RequestBody User user) {
        log.info("Add user with data: {}", user);
        return userService.addUser(user);
    }

    @PutMapping
    public User updateUser(@Valid @RequestBody User user) {
        log.info("Update user with data: {}", user);
        return userService.updateUser(user);
    }

    @PutMapping("/{userId}/friends/{friendId}")
    public void addFriend(@PathVariable Long userId, @PathVariable Long friendId) {
        log.info("Add friend with id=" + friendId + " to user with id=" + userId);
        userService.addFriend(userId, friendId);
    }

    @DeleteMapping("/{userId}/friends/{friendId}")
    public void deleteFriend(@PathVariable Long userId, @PathVariable Long friendId) {
        log.info("Delete friend with id=" + friendId + " from user with id=" + userId);
        userService.removeFriend(userId, friendId);
    }

    @GetMapping("/{userId}/friends")
    public Collection<User> findUserFriends(@PathVariable Long userId) {
        log.info("Find friends for user with id=" + userId);
        return userService.findUserFriends(userId);
    }

    @GetMapping("/{userId}/friends/common/{otherUserId}")
    public List<User> findCommonFriends(@PathVariable Long userId, @PathVariable Long otherUserId) {
        log.info("Find common friends for users with ids=" + userId + ", " + otherUserId);
        return userService.findCommonFriends(userId, otherUserId);
    }

    @Override
    public void validate(User user) {
        if (user.getId() < 1) {
            log.error("Id меньше, чем 1");
            throw new ValidationException("id не может быть меньше 1");
        }
        if (user.getName().equals("")) {
            log.error("Name не может быть \"\"");
            user.setName(user.getLogin());
        }
        if (user.getBirthday().isAfter(LocalDate.now())) {
            log.error("Дата рождения позже, чем текущая дата");
            throw new ValidationException("Дата рождения не может быть позже текущей даты");
        }
    }
}
