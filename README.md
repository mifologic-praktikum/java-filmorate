# java-filmorate
Проекта курса Java-разработчик "Яндекс.Практикума".

## Схема базы данных

![](./src/main/resources/FilmorateDB_schema.png)

### Примеры запросов:

#### Film
* получить все фильмы: `SELECT * FROM Film`
* получить фильм по id: `SELECT * FROM Film WHERE film_id=1`

#### User 
* получить всех пользователей:
  `SELECT * FROM User`
* получить пользователя по id: `SELECT * FROM User WHERE user_id=1`
