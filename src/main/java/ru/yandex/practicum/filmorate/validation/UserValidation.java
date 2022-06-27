package ru.yandex.practicum.filmorate.validation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;

public class UserValidation {

    private final static Logger log = LoggerFactory.getLogger(User.class);

    public void validateUser(User user) {
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
