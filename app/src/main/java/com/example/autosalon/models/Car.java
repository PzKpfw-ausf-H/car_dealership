package com.example.autosalon.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.firebase.database.PropertyName;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

@Entity(tableName = "car")
public class Car implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "is_favorite")
    private boolean isFavorite;

    private String modelName;
    private String manufacturer;
    private int year;
    private String imageUrl;
    private String engineType;
    private String engineVolume;
    private String gearBox;
    private String driveGear;
    private String price;
    private int mileage;
    private int fuelLevel;
    private int approxKmLeft;
    @ColumnInfo(name = "bought")
    @PropertyName("bought")
    private boolean isBought;

    public boolean needsOilChange() {
        return mileage > 10000;
    }

    // конструкторы
    public Car() {}

    public Car(String modelName, String manufacturer, int year, String imageUrl, String engineType,
               String engineVolume, String gearBox, String driveGear, String price) {
        this.modelName = modelName;
        this.manufacturer = manufacturer;
        this.year = year;
        this.imageUrl = imageUrl;
        this.engineType = engineType;
        this.engineVolume = engineVolume;
        this.gearBox = gearBox;
        this.driveGear = driveGear;
        this.price = price;
    }

    //геттеры
    public String getModelName() {
        return this.modelName;
    }
    public String getManufacturer() {
        return this.manufacturer;
    }
    public int getYear() {
        return this.year;
    }
    public String getImageUrl() {
        return this.imageUrl;
    }
    public String getEngineType() {
        return this.engineType;
    }
    public String getEngineVolume() {
        return this.engineVolume;
    }
    public String getGearBox() {
        return this.gearBox;
    }
    public String getDriveGear() {
        return this.driveGear;
    }
    public String getPrice() {
        return this.price;
    }
    public boolean isFavorite() {return isFavorite;}
    public int getId() {return this.id;}
    public int getMileage() {return this.mileage;}
    public int getFuelLevel() {return this.fuelLevel;}
    public int getApproxKmLeft() {return this.approxKmLeft;}
    @PropertyName("bought")
    public boolean isBought() {return this.isBought;}

    // сеттеры

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setEngineType(String engineType) {
        this.engineType = engineType;
    }

    public void setEngineVolume(String engineVolume) {
        this.engineVolume = engineVolume;
    }

    public void setGearBox(String gearBox) {
        this.gearBox = gearBox;
    }

    public void setDriveGear(String driveGear) {
        this.driveGear = driveGear;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public void setFavorite(boolean favorite) {isFavorite = favorite;}

    public void setId(int id) {this.id = id;}
    public void setMileage(int mileage) {this.mileage = mileage;}
    public void setFuelLevel(int fuelLevel) {this.fuelLevel = fuelLevel;}
    public void setApproxKmLeft(int approxKmLeft) {this.approxKmLeft = approxKmLeft;}
    @PropertyName("bought")
    public void setBought(boolean bought) {this.isBought = bought;}
}
