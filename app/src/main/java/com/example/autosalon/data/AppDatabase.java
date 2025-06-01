package com.example.autosalon.data;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.autosalon.models.Car;
import com.example.autosalon.models.User;
/*
Основной класс Room, в котором мы указываем, какие таблицы будут использоваоться и какие DAO (Data Access Object) подключены
 DAO - некая прослойка между БД и системой. DAO абстрагирует сущности системы и делает их отображение на БД,
 определяет общие методы использования соединения, его получение, закрытие и (или) возвращение в Connection Pool.

Вершиной иерархии DAO является абстрактный класс или интерфейс с описанием общих методов,
которые будут использоваться при взаимодействии с базой данных. Как правило, это методы поиска, удаление по ключу, обновление
 */
@Database(entities = {User.class, Car.class}, version = 2)     //Database - аннотация, указывающая список таблиц (entities) и версию БД (version)
public abstract class AppDatabase extends RoomDatabase {    //RoomDatabase - абстрактный базовый класс, предоставляющий доступ к DAO
    public abstract UserDao userDao();
    public abstract CarDao carDao();
}
