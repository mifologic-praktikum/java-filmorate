package ru.yandex.practicum.filmorate.storage.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.User;

import java.sql.PreparedStatement;
import java.util.*;

@Component("UserDbStorage")
public class UserDbStorage implements UserStorage {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public UserDbStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<User> findUsers() {
        List<User> users = new ArrayList<>();
        final String sqlQuery = "SELECT * FROM USERS";
        SqlRowSet rs = jdbcTemplate.queryForRowSet(sqlQuery);
        while (rs.next()) {
            users.add(new User(
                    rs.getLong("USER_ID"),
                    rs.getString("EMAIL"),
                    rs.getString("LOGIN"),
                    rs.getString("NAME"),
                    Objects.requireNonNull(rs.getDate("BIRTHDAY")).toLocalDate()));
        }
        return users;
    }

    @Override
    public Optional<User> findUserById(Long userId) {
        final String sqlQuery = "SELECT * FROM USERS WHERE USER_ID = ?";
        SqlRowSet rs = jdbcTemplate.queryForRowSet(sqlQuery, userId);
        if (rs.next()) {
            User user = new User(rs.getLong("USER_ID"),
                    rs.getString("EMAIL"),
                    rs.getString("LOGIN"),
                    rs.getString("NAME"),
                    Objects.requireNonNull(rs.getDate("BIRTHDAY")).toLocalDate());
            return Optional.of(user);
        } else {
            return Optional.empty();
        }
    }

    @Override
    public User addUser(User user) {
        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("USERS")
                .usingGeneratedKeyColumns("USER_ID");
        user.setId(simpleJdbcInsert.executeAndReturnKey(this.userToMap(user)).longValue());
        return user;
    }

    @Override
    public User updateUser(User user) {
        String sqlQuery = "UPDATE USERS SET EMAIL = ?, LOGIN = ?, NAME = ?, BIRTHDAY = ? WHERE USER_ID = ?";
        jdbcTemplate.update(sqlQuery,
                user.getEmail(),
                user.getLogin(),
                user.getName(),
                user.getBirthday(),
                user.getId());
        return user;
    }

    @Override
    public void addFriend(Long userId, Long friendId) {
        String sqlQuery = "INSERT INTO USER_FRIENDS (USER_ID, FRIEND_ID) VALUES (?, ?)";
        jdbcTemplate.update(connection -> {
            PreparedStatement stmt = connection.prepareStatement(sqlQuery);
            stmt.setLong(1, userId);
            stmt.setLong(2, friendId);
            return stmt;
        });
    }

    @Override
    public void removeFriend(Long userId, Long friendId) {
        String sqlQuery = "DELETE FROM USER_FRIENDS WHERE USER_ID = ? AND  FRIEND_ID = ?";
        jdbcTemplate.update(sqlQuery, userId, friendId);
    }

    @Override
    public Collection<User> findUserFriends(Long userId) {
        final String sqlQuery = "SELECT U.USER_ID, U.EMAIL, U.LOGIN, U.NAME, U.BIRTHDAY FROM USERS U " +
                "JOIN USER_FRIENDS UF on U.USER_ID = UF.FRIEND_ID " +
                "WHERE UF.USER_ID = ?";
        SqlRowSet rs = jdbcTemplate.queryForRowSet(sqlQuery, userId);
        Collection<User> friends = new HashSet<>();
        while (rs.next()) {
            friends.add(new User(
                    rs.getLong("USER_ID"),
                    rs.getString("EMAIL"),
                    rs.getString("LOGIN"),
                    rs.getString("NAME"),
                    Objects.requireNonNull(rs.getDate("BIRTHDAY")).toLocalDate()));
        }
        return friends;
    }

    @Override
    public List<User> findCommonFriends(Long userId, Long otherUserId) {
        List<User> users = new ArrayList<>();
        final String sqlQuery = "SELECT U.USER_ID, U.EMAIL, U.LOGIN, U.NAME, U.BIRTHDAY From USERS U " +
                "WHERE USER_ID IN (SELECT FRIEND_ID FROM USER_FRIENDS UF  where UF.USER_ID = ?) " +
                "AND USER_ID IN (SELECT FRIEND_ID FROM USER_FRIENDS UF2  where UF2.USER_ID = ?)";
        SqlRowSet rs = jdbcTemplate.queryForRowSet(sqlQuery, userId, otherUserId);
        while (rs.next()) {
            users.add(new User(
                    rs.getLong("USER_ID"),
                    rs.getString("EMAIL"),
                    rs.getString("LOGIN"),
                    rs.getString("NAME"),
                    Objects.requireNonNull(rs.getDate("BIRTHDAY")).toLocalDate()));
        }
        return users;
    }

    private Map<String, Object> userToMap(User user) {
        Map<String, Object> values = new HashMap<>();
        values.put("EMAIL", user.getEmail());
        values.put("LOGIN", user.getLogin());
        values.put("NAME", user.getName());
        values.put("BIRTHDAY", user.getBirthday());
        return values;
    }
}
