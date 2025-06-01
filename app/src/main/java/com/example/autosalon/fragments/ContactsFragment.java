package com.example.autosalon.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.autosalon.R;
import com.yandex.mapkit.MapKitFactory;
import com.yandex.mapkit.geometry.Point;
import com.yandex.mapkit.mapview.MapView;


public class ContactsFragment extends Fragment{

    private MapView mapView;
    private static final String MAPVIEW_BUNDLE_KEY="MapViewBundleKey";

    public ContactsFragment() {
        super(R.layout.fragment_contacts);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MapKitFactory.initialize(requireContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_contacts, container, false);

        Button callButton = view.findViewById(R.id.button_call);
        Button emailButton = view.findViewById(R.id.button_email);

        callButton.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_DIAL);
            intent.setData(Uri.parse("tel:+79778718057"));
            startActivity(intent);
        });

        emailButton.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_SENDTO);
            intent.setData(Uri.parse("mailto:info@autosalon.ru"));
            intent.putExtra(Intent.EXTRA_SUBJECT, "Вопрос по автомобилям");
            startActivity(intent);
        });

        mapView = view.findViewById(R.id.mapView);

        mapView.getMapWindow().getMap().getMapObjects().addPlacemark(
                new Point(55.7558, 37.6173)
        );

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        if (mapView != null) mapView.onStart();
        MapKitFactory.getInstance().onStart();
    }

    @Override
    public void onStop() {
        if (mapView != null) mapView.onStop();
        MapKitFactory.getInstance().onStop();
        super.onStop();
    }
}