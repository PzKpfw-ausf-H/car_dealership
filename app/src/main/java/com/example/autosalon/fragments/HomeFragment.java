package com.example.autosalon.fragments;

import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.autosalon.R;
import com.example.autosalon.activities.DetailsActivity;
import com.example.autosalon.adapters.CarAdapter;
import com.example.autosalon.data.AppDatabase;
import com.example.autosalon.data.CarData;
import com.example.autosalon.models.Car;
import com.example.autosalon.utils.DatabaseHelper;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class HomeFragment extends Fragment {
    private RecyclerView recyclerView;
    private CarAdapter adapter;
    private List<Car> carList;
    private List<Car> filteredList;
    private EditText searchInput;
    private Spinner spinnerManufacturer, spinnerGearbox, spinnerDrive;

    public HomeFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        recyclerView = view.findViewById(R.id.carRecyclerView);
        searchInput = view.findViewById(R.id.search_input);
        spinnerManufacturer = view.findViewById(R.id.spinner_manufacturer);
        spinnerGearbox = view.findViewById(R.id.spinner_gearbox);
        spinnerDrive = view.findViewById(R.id.spinner_drive);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        AppDatabase db = DatabaseHelper.getDatabase(getContext());
        carList = db.carDao().getAllCars();

        if (carList.isEmpty()) {
            carList = CarData.getCarList(); //список по умолчанию
            for (Car car : carList) {
                db.carDao().insert(car); //Добавляем в БД
            }
            carList = db.carDao().getAllCars(); //Перезапрашиваем из БД
        }

        filteredList = new ArrayList<>(carList);

        initSpinners();

        adapter = new CarAdapter(getContext(), filteredList, car -> {
            Intent intent = new Intent(getContext(), DetailsActivity.class);
            intent.putExtra("car", car);
            startActivity(intent);
        });

        recyclerView.setAdapter(adapter);

        searchInput.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {
                filterAll();
            }
            @Override public void afterTextChanged(Editable s) {}
        });

        return view;
    }

    private void initSpinners() {
        Set<String> manufacturers = new HashSet<>();
        Set<String> gearboxes = new HashSet<>();
        Set<String> drives = new HashSet<>();

        manufacturers.add("Все марки");
        gearboxes.add("Все коробки");
        drives.add("Все приводы");

        for (Car car : carList) {
            manufacturers.add(car.getManufacturer());
            gearboxes.add(car.getGearBox());
            drives.add(car.getDriveGear());
        }

        ArrayAdapter<String> adapterMan = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_dropdown_item, new ArrayList<>(manufacturers));
        ArrayAdapter<String> adapterGear = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_dropdown_item, new ArrayList<>(gearboxes));
        ArrayAdapter<String> adapterDrive = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_dropdown_item, new ArrayList<>(drives));

        spinnerManufacturer.setAdapter(adapterMan);
        spinnerGearbox.setAdapter(adapterGear);
        spinnerDrive.setAdapter(adapterDrive);

        AdapterView.OnItemSelectedListener listener = new AdapterView.OnItemSelectedListener() {
            @Override public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                filterAll();
            }

            @Override public void onNothingSelected(AdapterView<?> parent) {}
        };

        spinnerManufacturer.setOnItemSelectedListener(listener);
        spinnerGearbox.setOnItemSelectedListener(listener);
        spinnerDrive.setOnItemSelectedListener(listener);
    }

    private void filterAll() {
        String search = searchInput.getText().toString().toLowerCase();
        String selectedManufacturer = spinnerManufacturer.getSelectedItem().toString();
        String selectedGearbox = spinnerGearbox.getSelectedItem().toString();
        String selectedDrive = spinnerDrive.getSelectedItem().toString();

        List<Car> filtered = new ArrayList<>();

        for (Car car : carList) {
            boolean matchesSearch = car.getModelName().toLowerCase().contains(search);
            boolean matchesManufacturer = selectedManufacturer.equals("Все марки") || car.getManufacturer().equals(selectedManufacturer);
            boolean matchesGearbox = selectedGearbox.equals("Все коробки") || car.getGearBox().equals(selectedGearbox);
            boolean matchesDrive = selectedDrive.equals("Все приводы") || car.getDriveGear().equals(selectedDrive);

            if (matchesSearch && matchesManufacturer && matchesGearbox && matchesDrive) {
                filtered.add(car);
            }
        }

        adapter.updateList(filtered);
    }

    @Override
    public void onResume() {
        super.onResume();
        carList.clear();
        carList.addAll(DatabaseHelper.getDatabase(getContext()).carDao().getAllCars());
        filterAll(); // пересчитываем фильтр при возврате на фрагмент
    }
}
