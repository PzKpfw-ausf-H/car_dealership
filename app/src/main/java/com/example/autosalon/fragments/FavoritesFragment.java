package com.example.autosalon.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.autosalon.R;
import com.example.autosalon.activities.DetailsActivity;
import com.example.autosalon.adapters.CarAdapter;
import com.example.autosalon.models.Car;
import com.example.autosalon.utils.SharedPrefsManager;

import java.util.List;

public class FavoritesFragment extends Fragment {
    private RecyclerView recyclerView;
    private CarAdapter adapter;
    private List<Car> favorites;

    public FavoritesFragment() {
        super(R.layout.fragment_favorites);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstaceState) {
        View view = inflater.inflate(R.layout.fragment_favorites, container, false);

        recyclerView = view.findViewById(R.id.favoritesRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        favorites = SharedPrefsManager.getFavorites(getContext());

        adapter = new CarAdapter(getContext(), favorites, car -> {
            Intent intent = new Intent(getContext(), DetailsActivity.class);
            intent.putExtra("car", car);
            startActivity(intent);
        });

        recyclerView.setAdapter(adapter);

        return view;
    }
}


















