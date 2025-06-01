package com.example.autosalon.data;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Delete;
import androidx.room.Update;

import com.example.autosalon.models.Car;

import java.util.List;

@Dao
public interface CarDao {

    @Insert
    void insert(Car car);

    @Update
    void updateCar(Car car);

    @Query("SELECT * FROM car")
    List<Car> getAllCars();

    @Query("SELECT * FROM car WHERE is_favorite = 1")
    List<Car> getFavoriteCars();

    @Query("SELECT * FROM car WHERE id = :id LIMIT 1")
    Car getCarById(int id);
}
