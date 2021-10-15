package com.example.foodfortheweek;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONArray;

import java.util.ArrayList;

public class OneDayFood implements Parcelable {
    private String food;
    private String weekDay;
    private Integer id;
    private String pictureUrl;
    private Bitmap pictureBitmap;
    private ArrayList<String> ingredients;

    public OneDayFood(String food, String weekDay, Integer id, String pictureUrl, Bitmap pictureBitmap, ArrayList<String> ingredients) {
        this.food = food;
        this.weekDay = weekDay;
        this.id = id;
        this.pictureUrl = pictureUrl;
        this.pictureBitmap = pictureBitmap;
        this.ingredients = ingredients;
    }

    protected OneDayFood(Parcel in) {
        food = in.readString();
        weekDay = in.readString();
        if (in.readByte() == 0) {
            id = null;
        } else {
            id = in.readInt();
        }
        pictureUrl = in.readString();
        ingredients = in.createStringArrayList();
//        pictureBitmap = in.readParcelable(Bitmap.class.getClassLoader());
    }

    public static final Creator<OneDayFood> CREATOR = new Creator<OneDayFood>() {
        @Override
        public OneDayFood createFromParcel(Parcel in) {
            return new OneDayFood(in);
        }

        @Override
        public OneDayFood[] newArray(int size) {
            return new OneDayFood[size];
        }
    };

    public void setFood(String food) {
        this.food = food;
    }
    public void setWeekDay(String weekDay) {
        this.weekDay = weekDay;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public void setPictureBitmap(Bitmap pictureBitmap) {
        this.pictureBitmap = pictureBitmap;
    }
    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }
    public void setIngredients(ArrayList<String> ingredients) { this.ingredients = ingredients; }


    public String getFood() {
        return food;
    }
    public String getWeekDay() {
        return weekDay;
    }
    public Integer getId() {
        return id;
    }
    public Bitmap getPictureBitmap() {
        return pictureBitmap;
    }
    public String getPictureUrl() { return pictureUrl; }
    public ArrayList<String> getIngredients() { return ingredients; }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeString(food);
        out.writeString(weekDay);
        out.writeInt(id);
        out.writeString(pictureUrl);
        out.writeStringList(ingredients);
//        out.writeValue(pictureBitmap);
    }
}
