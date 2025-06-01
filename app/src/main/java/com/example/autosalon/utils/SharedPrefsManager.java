package com.example.autosalon.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.autosalon.models.Car;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
public class SharedPrefsManager {
    private static final String PREFS_NAME="favorites_prefs";
    private static final String KEY_FAVORITES="favorite_cars";

    public static void addToFavorites(Context context, Car car) {
        List<Car> list = getFavorites(context);
        list.add(car);
        saveList(context, list);
    }

    public static List<Car> getFavorites(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        String json = prefs.getString(KEY_FAVORITES, null);

        if (json != null) {
            Gson gson = new Gson();
            Type type = new TypeToken<List<Car>>() {}.getType();
            return gson.fromJson(json, type);
        }

        return new ArrayList<>();
    }

    public static void saveList(Context context, List<Car> list) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

        Gson gson = new Gson();
        String json = gson.toJson(list);
        editor.putString(KEY_FAVORITES, json);
        editor.apply();
    }

    public static boolean isAlreadyFavorite(Context context, Car car) {
        List<Car> list = getFavorites(context);
        for (Car item : list) {
            if (item.getModelName().equals(car.getModelName())) {
                return true;
            }
        }
        return false;
    }

    public static void removeFromFavorites(Context context, Car car) {
        List<Car> list = getFavorites(context);
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getModelName().equals(car.getModelName())) {
                list.remove(i);
                break;
            }
        }
        saveList(context, list);
    }
}
