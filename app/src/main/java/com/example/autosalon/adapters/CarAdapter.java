package com.example.autosalon.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.autosalon.R;
import com.example.autosalon.models.Car;
import com.example.autosalon.utils.SharedPrefsManager;

import java.util.List;

public class CarAdapter extends RecyclerView.Adapter<CarAdapter.CarViewHolder> {

    private Context context;
    private List<Car> carList;
    private OnItemClickListener listener;


    public void updateList(List<Car> newList) {
        carList.clear();
        carList.addAll(newList);
        notifyDataSetChanged();
    }
    public interface OnItemClickListener {
        void onItemClick(Car car);
    }

    public CarAdapter(Context context, List<Car> carList, OnItemClickListener listener) {
        this.context = context;
        this.carList = carList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public CarViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_car, parent, false);
        return new CarViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CarViewHolder holder, int position) {
        Car car = carList.get(position);
        holder.modelText.setText(car.getModelName());
        holder.priceText.setText(car.getPrice());

        Glide.with(context).load(car.getImageUrl()).into(holder.imageView);

        holder.itemView.setOnClickListener(v -> listener.onItemClick(car));

        boolean isFavorite = SharedPrefsManager.isAlreadyFavorite(context, car);
        holder.favIcon.setImageResource(
                isFavorite ? R.drawable.ic_favorite : R.drawable.ic_favorite_border
        );

        holder.favIcon.setOnClickListener(v -> {
            boolean nowFavorite = SharedPrefsManager.isAlreadyFavorite(context, car);

            if (nowFavorite) {
                SharedPrefsManager.removeFromFavorites(context, car);
                holder.favIcon.setImageResource(R.drawable.ic_favorite_border);
                Toast.makeText(context, "Удалено из избранного", Toast.LENGTH_SHORT).show();
            } else {
                SharedPrefsManager.addToFavorites(context, car);
                holder.favIcon.setImageResource(R.drawable.ic_favorite);
                Toast.makeText(context, "Добавлено в избранное", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return carList.size();
    }

    public static class CarViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView modelText, priceText;
        ImageView favIcon;

        public CarViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.car_image);
            modelText = itemView.findViewById(R.id.car_model);
            priceText = itemView.findViewById(R.id.car_price);
            favIcon = itemView.findViewById(R.id.fav_icon);
        }
    }






}
