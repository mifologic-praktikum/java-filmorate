package ru.yandex.practicum.filmorate.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Friend {

    private Long userId;
    private  Long friendId;
    private boolean friendShipStatus;
}
