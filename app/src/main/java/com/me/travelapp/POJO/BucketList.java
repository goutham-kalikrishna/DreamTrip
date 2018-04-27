package com.me.travelapp.POJO;

/**
 * Created by HP-USER on 27-04-2018.
 */

public class BucketList {
    public static final String TABLE_NAME = "bucketlist";

    public static final String COLUMN_ID = "id";
    public static final String COLUMN_PLACE = "place";
    public static final String COLUMN_VALUE = "VALUE";
    public static final String DEFAULT_VALUE="false";

    private int id;
    private String place;
    private String value;


    // Create table SQL query
    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + "("
                    + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + COLUMN_PLACE + " TEXT,"
                    + COLUMN_VALUE + " TEXT DEFAULT "+DEFAULT_VALUE
                    + ")";

    public BucketList() {
    }

    public BucketList(int id, String place, String value) {
        this.id = id;
        this.place = place;
        this.value = value;
    }

    public int getId() {
        return id;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getValue() {
        return value;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setValue(String value) {
        this.value = value;
    }
}