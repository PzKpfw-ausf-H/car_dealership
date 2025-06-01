package com.example.autosalon.utils;

import android.content.Context;

import androidx.room.Room;

import com.example.autosalon.data.AppDatabase;

/*
* Создает один экземпляр БД на все приложение и возвращает его при необходимости.
* Улучшает производительность и предотвращает утечки памяти
* Используется паттерн Singleton - один экземпляр БД
* */
public class DatabaseHelper {
    private static AppDatabase instance;

    public static AppDatabase getDatabase(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, "user_db")
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .build();
        }
        return instance;
    }
}
