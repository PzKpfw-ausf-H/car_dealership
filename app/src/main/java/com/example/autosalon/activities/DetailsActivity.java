package com.example.autosalon.activities;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.autosalon.R;
import com.example.autosalon.data.CarDao;
import com.example.autosalon.utils.DatabaseHelper;
import com.example.autosalon.models.Car;

public class DetailsActivity extends AppCompatActivity {
    private ImageView detailImage;

    private Button favoriteButton;
    private TextView model, manufacturer, year, engine, volume, gearbox, drive, price;

    private CarDao carDao;
    private Car car;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        carDao = DatabaseHelper.getDatabase(this).carDao();

        car = (Car) getIntent().getSerializableExtra("car");

        favoriteButton = findViewById(R.id.button_favorite);

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
            Glide.with(this).load(car.getImageUrl()).into(detailImage);
            model.setText(car.getModelName());
            manufacturer.setText("Производитель: " + car.getManufacturer());
            year.setText("Год выпуска: " + car.getYear());
            engine.setText("Двигатель: " + car.getEngineType());
            volume.setText("Объем двигателя: " + car.getEngineVolume());
            gearbox.setText("Коробка передач: " + car.getGearBox());
            drive.setText("Привод: " + car.getDriveGear());
            price.setText("Цена: " + car.getPrice());
        }

        if (car.isFavorite()) {
            favoriteButton.setText("Уже в избранном");
            favoriteButton.setEnabled(false);
        } else {
            favoriteButton.setOnClickListener(v -> {
                car.setFavorite(true);
                carDao.updateCar(car);
                favoriteButton.setText("Добавлено");
                favoriteButton.setEnabled(false);
            });
        }
    }
}