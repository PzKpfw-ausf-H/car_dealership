package com.example.autosalon.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.autosalon.R;
import com.example.autosalon.models.Car;
import com.example.autosalon.models.User;
import com.example.autosalon.utils.DatabaseHelper;

import java.util.List;

public class ProfileFragment extends Fragment {
    private TextView usernameText;

    public ProfileFragment() {
        super(R.layout.fragment_profile);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TextView usernameText = view.findViewById(R.id.text_username);
        LinearLayout myCarsContainer = view.findViewById(R.id.container_my_cars);
        LinearLayout statusContainer = view.findViewById(R.id.container_car_status);

        // Получаем имя пользователя через Room (по текущей сессии)
        SharedPreferences prefs = requireContext().getSharedPreferences("UserSession", Context.MODE_PRIVATE);
        String login = prefs.getString("loggedInUser", "Гость");
        User currentUser = DatabaseHelper.getDatabase(requireContext()).userDao().findByLogin(login);
        usernameText.setText("Пользователь: " + (currentUser != null ? currentUser.getUsername() : "Гость"));

        // Получаем купленные авто через Room
        List<Car> boughtCars = DatabaseHelper.getDatabase(getContext()).carDao().getBoughtCars();
        LayoutInflater inflater = LayoutInflater.from(getContext());

        for (Car car : boughtCars) {
            View carCard = inflater.inflate(R.layout.item_car_mini, myCarsContainer, false);

            ImageView image = carCard.findViewById(R.id.img_car);
            TextView title = carCard.findViewById(R.id.txt_model);
            TextView specs = carCard.findViewById(R.id.txt_specs);

            image.setImageResource(getLocalImageResource(car.getModelName()));
            title.setText(car.getModelName());
            specs.setText(car.getEngineType() + " / " + car.getGearBox() + " / " + car.getDriveGear());

            myCarsContainer.addView(carCard);

            // Состояние авто
            View statusCard = inflater.inflate(R.layout.item_car_status, statusContainer, false);
            ((TextView) statusCard.findViewById(R.id.txt_model)).setText(car.getModelName());
            ((TextView) statusCard.findViewById(R.id.txt_mileage)).setText("Пробег: " + car.getMileage() + " км");
            ((TextView) statusCard.findViewById(R.id.txt_fuel)).setText("Топливо: " + car.getFuelLevel() + "% (" + car.getApproxKmLeft() + " км)");
            ((TextView) statusCard.findViewById(R.id.txt_oil)).setText("Масло: " + (car.needsOilChange() ? "нужна замена" : "в норме"));

            statusContainer.addView(statusCard);

            Log.d("PROFILE", "Купленное авто: " + car.getModelName());
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