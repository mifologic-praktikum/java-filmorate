package ru.yandex.practicum.filmorate.controller;


import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.validation.UserValidation;

import javax.validation.Valid;
import java.util.*;

@RestController
@Slf4j
@RequestMapping(value = "/users")
public class UserController {

    private static int userId = 0;
    private final Map<Integer, User> users = new TreeMap<>();
    private final UserValidation validation = new UserValidation();

    private static int generateId() {
        return ++userId;
    }

    @GetMapping
    public Collection<User> getUsers() {
        log.info("Текущее количество пользователей: {}", users.size());
        return users.values();
    }

    @PostMapping
    public User addUser(@Valid @RequestBody User user) {
        user.setId(generateId());
        validation.validateUser(user);
        users.put(user.getId(), user);
        log.info("Добавлен пользователь: {}", user);
        return user;
    }

    @PutMapping
    public User updateUser(@Valid @RequestBody User user) {
        validation.validateUser(user);
        users.put(user.getId(), user);
        log.info("Обновлён пользователь {}", user);
        return user;
    }
}
