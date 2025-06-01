package com.example.autosalon.utils;

import android.content.Context;
import android.provider.ContactsContract;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.autosalon.models.Car;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class FirebaseHelper {

    public static void loadCarsFromFirebase(Context context) {
        FirebaseDatabase.getInstance().getReference().addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot carSnapshot : snapshot.getChildren()) {
                    Car car = carSnapshot.getValue(Car.class);
                    if (car != null) {
                        if (DatabaseHelper.getDatabase(context).carDao().getCarById(car.getId()) == null) {
                            DatabaseHelper.getDatabase(context).carDao().insert(car);
                        }
                    }
                }
                Log.d("Firebase", "Данные успешно загружены в Room");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("Firebase", "ошибка загрузки: " + error.getMessage());
            }
        });
    }
}
