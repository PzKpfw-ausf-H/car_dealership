package com.example.autosalon.utils;

import android.content.Context;
import android.provider.ContactsContract;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.autosalon.data.CarDao;
import com.example.autosalon.models.Car;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;
import java.util.Objects;

public class FirebaseHelper {

    public static void loadCarsFromFirebase(Context context) {
        FirebaseDatabase.getInstance().getReference().addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot carSnapshot : snapshot.getChildren()) {
                    Car car = carSnapshot.getValue(Car.class);

                    if (car != null) {
                        Log.d("FIREBASE", "Загружено авто: " + car.getModelName() + ", куплено: " + car.isBought());
                        Boolean bought = carSnapshot.child("bought").getValue(Boolean.class);
                        if (bought != null) {
                            car.setBought(bought);
                        }

                        if (DatabaseHelper.getDatabase(context).carDao().getCarById(car.getId()) == null) {
                            DatabaseHelper.getDatabase(context).carDao().insert(car);
                        }
                    }
                }
                FirebaseHelper.normalizeCarData(context);
                Log.d("Firebase", "Данные успешно загружены в Room");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("Firebase", "ошибка загрузки: " + error.getMessage());
            }
        });
    }

    public static void normalizeCarData(Context context) {
        CarDao carDao = DatabaseHelper.getDatabase(context).carDao();
        List<Car> allCars = carDao.getAllCars();

        for (Car car : allCars) {
            // Привод
            switch (car.getDriveGear().toLowerCase()) {
                case "front-wheel drive":
                case "передний привод":
                    car.setDriveGear("Передний");
                    break;
                case "rear-wheel drive":
                    car.setDriveGear("Задний");
                    break;
                case "all-wheel drive":
                case "awd":
                    car.setDriveGear("Полный");
                    break;
            }

            // Коробка передач
            switch (car.getGearBox().toLowerCase()) {
                case "manual":
                    car.setGearBox("Механика");
                    break;
                case "automatic":
                case "автомат":
                    car.setGearBox("Автомат");
                    break;
                case "cvt":
                    car.setGearBox("Вариатор");
                    break;
            }

            // Тип двигателя
            switch (car.getEngineType().toLowerCase()) {
                case "gasoline":
                case "бензиновый":
                    car.setEngineType("Бензиновый");
                    break;
                case "diesel":
                    car.setEngineType("Дизельный");
                    break;
                case "electric":
                    car.setEngineType("Электрический");
                    break;
                case "hybrid":
                    car.setEngineType("Гибрид");
                    break;
            }
            carDao.updateCar(car);
        }
    }
}
