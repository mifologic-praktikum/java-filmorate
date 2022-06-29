package ru.yandex.practicum.filmorate.controller;


import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.validation.DataValidation;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.*;

@RestController
@Slf4j
@RequestMapping(value = "/users")
public class UserController extends DataValidation<User> {

    private static Integer userId = 0;
    private final Map<Integer, User> users = new TreeMap<>();

    private static Integer generateId() {
        return ++userId;
    }

    @GetMapping
    public List<User> getUsers() {
        log.info("Текущее количество пользователей: {}", users.size());
        log.info("{}", users.values());
        return new ArrayList<>(users.values());
    }

    @PostMapping
    public User addUser(@Valid @RequestBody User user) {
        user.setId(generateId());
        validate(user);
        users.put(user.getId(), user);
        log.info("Добавлен пользователь: {}", user);
        return user;
    }

    @PutMapping
    public User updateUser(@Valid @RequestBody User user) {
        validate(user);
        users.put(user.getId(), user);
        log.info("Обновлён пользователь {}", user);
        return user;
    }

    @Override
    public void validate(User user) {
        if(user.getId() < 1) {
            log.error("Id меньше, чем 1");
            throw new ValidationException("id не может быть меньше 1");
        }
        if (user.getName().equals("")) {
            log.error("Name не может быть \"\"");
            user.setName(user.getLogin());
        }
        if(user.getBirthday().isAfter(LocalDate.now())) {
            log.error("Дата рождения позже, чем текущая дата");
            throw new ValidationException("Дата рождения не может быть позже текущей даты");
        }
    }
}
