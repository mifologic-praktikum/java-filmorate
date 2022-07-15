package ru.yandex.practicum.filmorate.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;


@Getter
@Setter
@NoArgsConstructor
public class User {
    private Long id;
    @Email
    @NotNull
    private String email;
    @NotBlank
    private String login;
    @NotNull
    private String name;
    @PastOrPresent
    private LocalDate birthday;
    @JsonIgnore
    private Set<Long> friends = new HashSet<>();


}
