package com.example.autosalon.data;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.autosalon.models.User;

//Room автоматически генерирует реализацию при сборке проекта
@Dao
public interface UserDao {

    @Insert
    void insert(User user);     // Метод для добавления нового пользователя

    @Query("SELECT * FROM User WHERE username = :username AND password = :password")
    User login(String username, String password);   //Метод для проверки данных пользователя (авторизация)

    @Query("SELECT * FROM User WHERE username = :username")
    User getUserByUsername(String username);    //Метод для проверки уникальности логина при регистрации

    @Query("SELECT * FROM user WHERE username = :username LIMIT 1")
    User findByLogin(String username);
}
