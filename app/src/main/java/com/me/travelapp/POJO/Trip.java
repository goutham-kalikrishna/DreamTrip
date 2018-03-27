package com.me.travelapp.POJO;

/**
 * Created by j.girish on 11-02-2018.
 */

public class Trip {
    String HotelName;
    String HotelPrice;
    String Transport;
    String TransportPrice;

    public void Trip(String HotelName,String HotelPrice,String Transport,String TransportPrice){
        this.HotelName=HotelName;
        this.HotelPrice=HotelPrice;
        this.Transport=Transport;
        this.TransportPrice=TransportPrice;
    }
}
