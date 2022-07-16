package ru.yandex.practicum.filmorate.controller;

import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.user.UserService;
import ru.yandex.practicum.filmorate.service.user.UserServiceImpl;
import ru.yandex.practicum.filmorate.storage.user.InMemoryUserStorage;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.time.LocalDate;
import java.time.Month;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class UserControllerTest {

    private final UserStorage userStorage = new InMemoryUserStorage();
    private final UserService userService = new UserServiceImpl(userStorage);

    @Test
    public void userIdCantBeLessThan1() {
        User user = new User();
        user.setId(0L);
        user.setEmail("mail@yandex.ru");
        user.setName("BethG");
        user.setLogin("Beth");
        user.setBirthday(LocalDate.of(1964, Month.MARCH, 15));
        UserController uc = new UserController(userService);
        assertThrows(ValidationException.class, () -> uc.validate(user));
    }

    @Test
    public void userNameChangeToLoginIfUserNameIsEmptySting() {
        User user = new User();
        user.setId(2L);
        user.setEmail("mail@yandex.ru");
        user.setName("");
        user.setLogin("Beth");
        user.setBirthday(LocalDate.of(1964, Month.MARCH, 15));
        UserController uc = new UserController(userService);
        uc.validate(user);
        assertEquals(user.getName(), user.getLogin());
    }

    @Test
    public void userBirthDayDateCantBeInFuture() {
        User user = new User();
        user.setId(3L);
        user.setEmail("mail@yandex.ru");
        user.setName("BethG");
        user.setLogin("");
        user.setBirthday(LocalDate.of(2964, Month.MARCH, 15));
        UserController uc = new UserController(userService);
        assertThrows(ValidationException.class, () -> uc.validate(user));
    }
}
