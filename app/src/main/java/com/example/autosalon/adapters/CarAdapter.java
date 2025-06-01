package com.example.autosalon.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.autosalon.R;
import com.example.autosalon.data.CarDao;
import com.example.autosalon.models.Car;
import com.example.autosalon.utils.DatabaseHelper;

import java.util.List;

public class CarAdapter extends RecyclerView.Adapter<CarAdapter.CarViewHolder> {

    private Context context;
    private List<Car> carList;
    private OnItemClickListener listener;
    private CarDao carDao;

    public interface OnItemClickListener {
        void onItemClick(Car car);
    }

    public CarAdapter(Context context, List<Car> carList, OnItemClickListener listener) {
        this.context = context;
        this.carList = carList;
        this.listener = listener;
        this.carDao = DatabaseHelper.getDatabase(context).carDao();
    }

    public void updateList(List<Car> newList) {
        carList.clear();
        carList.addAll(newList);
        notifyDataSetChanged();
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

        // Подгружаем изображение
        Glide.with(context).load(car.getImageUrl()).into(holder.imageView);

        // Слушатель нажатия на карточку
        holder.itemView.setOnClickListener(v -> listener.onItemClick(car));

        // Установка иконки избранного
        updateFavoriteIcon(holder.favIcon, car.isFavorite());

        // Обработка клика на иконку избранного
        holder.favIcon.setOnClickListener(v -> {
            boolean newState = !car.isFavorite();
            car.setFavorite(newState);
            carDao.updateCar(car);  // Обновляем в базе
            updateFavoriteIcon(holder.favIcon, newState);
            Toast.makeText(context,
                    newState ? "Добавлено в избранное" : "Удалено из избранного",
                    Toast.LENGTH_SHORT).show();
        });
    }

    private void updateFavoriteIcon(ImageView icon, boolean isFavorite) {
        icon.setImageResource(isFavorite ? R.drawable.ic_favorite : R.drawable.ic_favorite_border);
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

