package ru.yandex.practicum.filmorate.model;

import lombok.*;

@Getter
@Setter
@RequiredArgsConstructor
public class Genre {

    private final Long id;
    private final String name;
}
