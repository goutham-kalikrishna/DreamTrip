package com.me.travelapp.POJO;

import android.graphics.Bitmap;
import android.widget.BaseAdapter;

import java.io.Serializable;

/**
 * Created by j.girish on 10-02-2018.
 */

public class Hotel implements Serializable {
    public String HotelName;
    public double price;
    public String[] facilities;
    public double rating;
    public  String url;
    public Bitmap bitmap;

    public Hotel(String HotelName, double price, String[] facilities, Double rating,String url)
    {
        this.url=url;
        this.HotelName=HotelName;
        this.price=price;
        this.facilities=facilities;
        this.rating=rating;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }
}
