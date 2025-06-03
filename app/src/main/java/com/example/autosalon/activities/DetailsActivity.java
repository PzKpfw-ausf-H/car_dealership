package com.example.autosalon.activities;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.autosalon.R;
import com.example.autosalon.data.CarDao;
import com.example.autosalon.utils.DatabaseHelper;
import com.example.autosalon.models.Car;

import java.util.List;

public class DetailsActivity extends AppCompatActivity {
    private ImageView detailImage;
    private TextView model, manufacturer, year, engine, volume, gearbox, drive, price;

    private CarDao carDao;
    private Car car;

    private LinearLayout similarCarsContainer;
    private LayoutInflater inflater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        detailImage = findViewById(R.id.detail_image);
        model = findViewById(R.id.detail_model);
        manufacturer = findViewById(R.id.detail_manufacturer);
        year = findViewById(R.id.detail_year);
        engine = findViewById(R.id.detail_engine);
        volume = findViewById(R.id.detail_volume);
        gearbox = findViewById(R.id.detail_gearbox);
        drive = findViewById(R.id.detail_drive);
        price = findViewById(R.id.detail_price);
        similarCarsContainer = findViewById(R.id.container_similar_cars);

        carDao = DatabaseHelper.getDatabase(this).carDao();
        inflater = LayoutInflater.from(this);

        car = (Car) getIntent().getSerializableExtra("car");

        if (car != null) {
            //Картинка
            int imageRes = getLocalImageResource(car.getModelName());
            detailImage.setImageResource(imageRes);

            model.setText(car.getModelName());
            manufacturer.setText("Производитель: " + car.getManufacturer());
            year.setText("Год выпуска: " + car.getYear());
            engine.setText("Двигатель: " + car.getEngineType());
            volume.setText("Объем двигателя: " + car.getEngineVolume());
            gearbox.setText("Коробка передач: " + car.getGearBox());
            drive.setText("Привод: " + car.getDriveGear());
            price.setText("Цена: " + car.getPrice());

            loadSimilarCars();
        }
    }

    private void loadSimilarCars() {
        List<Car> allCars = carDao.getAllCars();
        for (Car otherCar : allCars) {
            if (otherCar.getId() == car.getId()) continue; // не показываем текущую машину

            View card = inflater.inflate(R.layout.item_car, similarCarsContainer, false);

            ImageView image = card.findViewById(R.id.car_image);
            TextView modelText = card.findViewById(R.id.car_model);
            TextView priceText = card.findViewById(R.id.car_price);
            ImageView favIcon = card.findViewById(R.id.fav_icon);
            Button callBtn = card.findViewById(R.id.button_call);
            ImageButton msgBtn = card.findViewById(R.id.button_message);

            image.setImageResource(getLocalImageResource(otherCar.getModelName()));
            modelText.setText(otherCar.getModelName());
            priceText.setText(otherCar.getPrice());
            favIcon.setImageResource(otherCar.isFavorite() ? R.drawable.ic_favorite : R.drawable.ic_favorite_border);

            favIcon.setOnClickListener(v -> {
                boolean fav = !otherCar.isFavorite();
                otherCar.setFavorite(fav);
                carDao.updateCar(otherCar);
                favIcon.setImageResource(fav ? R.drawable.ic_favorite : R.drawable.ic_favorite_border);
            });

            similarCarsContainer.addView(card);
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
