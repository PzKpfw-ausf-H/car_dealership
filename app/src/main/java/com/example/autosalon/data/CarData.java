package com.example.autosalon.data;

import com.example.autosalon.models.Car;

import java.util.ArrayList;
import java.util.List;

public class CarData {
    public static List<Car> getCarList() {
        List<Car> cars = new ArrayList<>();

        cars.add(new Car("Mustang GT", "Ford", 2022, "https://link.to/mustang.jpg", "V8",
                "5.0L","Manual", "Rear-wheel drive", "$55,000"));

        cars.add(new Car("Model S", "Tesla", 2023, "https://link.to/models.jpg", "Electric",
                "-", "Automatic", "All-wheel drive", "$85,000"));

        cars.add(new Car("Camry", "Toyota", 2021, "https://link.to/camry.jpg", "V4",
                "2.5L", "Automatic", "Front-wheel drive", "$30,000"));

        return cars;
    }
}
