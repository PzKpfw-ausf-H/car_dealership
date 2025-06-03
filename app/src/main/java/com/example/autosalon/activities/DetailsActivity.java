package com.example.autosalon.activities;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.autosalon.R;
import com.example.autosalon.data.CarDao;
import com.example.autosalon.utils.DatabaseHelper;
import com.example.autosalon.models.Car;

public class DetailsActivity extends AppCompatActivity {
    private ImageView detailImage;
    private TextView model, manufacturer, year, engine, volume, gearbox, drive, price;

    private CarDao carDao;
    private Car car;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        carDao = DatabaseHelper.getDatabase(this).carDao();
        car = (Car) getIntent().getSerializableExtra("car");

        detailImage = findViewById(R.id.detail_image);
        model = findViewById(R.id.detail_model);
        manufacturer = findViewById(R.id.detail_manufacturer);
        year = findViewById(R.id.detail_year);
        engine = findViewById(R.id.detail_engine);
        volume = findViewById(R.id.detail_volume);
        gearbox = findViewById(R.id.detail_gearbox);
        drive = findViewById(R.id.detail_drive);
        price = findViewById(R.id.detail_price);

        if (car != null) {
            // Подгрузка изображения по названию модели
            int imageResId = getLocalImageResource(car.getModelName());
            detailImage.setImageResource(imageResId);


            model.setText(car.getModelName());
            manufacturer.setText("Производитель: " + car.getManufacturer());
            year.setText("Год выпуска: " + car.getYear());
            engine.setText("Двигатель: " + car.getEngineType());
            volume.setText("Объем двигателя: " + car.getEngineVolume());
            gearbox.setText("Коробка передач: " + car.getGearBox());
            drive.setText("Привод: " + car.getDriveGear());
            price.setText("Цена: " + car.getPrice());
        }
    }

    private int getLocalImageResource(String modelName) {
        switch (modelName.toLowerCase()) {
            case "camry":
                return R.drawable.camry;
            case "bmw 3 series":
                return R.drawable.bmw3;
            case "kia rio":
                return R.drawable.rio;
            case "hyundai solaris":
                return R.drawable.solaris;
            case "audi a4":
                return R.drawable.audi_a4;
            case "tesla model 3":
                return R.drawable.tesla3;
            case "lada vesta":
                return R.drawable.lada;
            case "mustang gt":
                return R.drawable.mustang;
            case "model s":
                return R.drawable.teslas;
            default:
                return R.drawable.placeholder;
        }
    }
}
