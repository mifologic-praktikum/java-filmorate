package ru.yandex.practicum.filmorate.controller;

import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;
import java.time.Month;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class UserControllerTest {

    @Test
    public void userIdCantBeLessThan1() {
        User user = new User();
        user.setId(0L);
        user.setEmail("mail@yandex.ru");
        user.setName("BethG");
        user.setLogin("Beth");
        user.setBirthday(LocalDate.of(1964, Month.MARCH, 15));
        UserController uc = new UserController();
        assertThrows(ValidationException.class, () -> uc.validate(user));
    }

    @Test
    public void userNameChangeToLoginIfUserNameIsEmptySting() {
        User user = new User();
        user.setId(0L);
        user.setEmail("mail@yandex.ru");
        user.setName("BethG");
        user.setLogin("");
        user.setBirthday(LocalDate.of(1964, Month.MARCH, 15));
        UserController uc = new UserController();
        uc.validate(user);
        assertEquals(user.getName(), user.getLogin());
    }

    @Test
    public void userBirthDayDateCantBeInFuture() {
        User user = new User();
        user.setId(0L);
        user.setEmail("mail@yandex.ru");
        user.setName("BethG");
        user.setLogin("");
        user.setBirthday(LocalDate.of(2964, Month.MARCH, 15));
        UserController uc = new UserController();
        assertThrows(ValidationException.class, () -> uc.validate(user));
    }
}
