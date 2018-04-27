package com.me.travelapp.Utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.me.travelapp.POJO.BucketList;

import java.util.ArrayList;
import java.util.List;
/**
 * Created by HP-USER on 27-04-2018.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "bucketlist_db";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {

        // create notes table
        db.execSQL(BucketList.CREATE_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + BucketList.TABLE_NAME);

        // Create tables again
        onCreate(db);
    }

    public long insertBucketlist(String place,String value) {
        // get writable database as we want to write data
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        // `id` will be inserted automatically.
        // no need to add them
        values.put(BucketList.COLUMN_PLACE, place);
        values.put(BucketList.COLUMN_VALUE,value);
        // insert row
        long id = db.insert(BucketList.TABLE_NAME, null, values);

        // close db connection
        db.close();

        // return newly inserted row id
        return id;
    }

    public BucketList getBucketList(long id) {
        // get readable database as we are not inserting anything
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(BucketList.TABLE_NAME,
                new String[]{BucketList.COLUMN_ID, BucketList.COLUMN_PLACE, BucketList.COLUMN_VALUE},
                BucketList.COLUMN_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();

        // prepare note object
        BucketList note = new BucketList(
                cursor.getInt(cursor.getColumnIndex(BucketList.COLUMN_ID)),
                cursor.getString(cursor.getColumnIndex(BucketList.COLUMN_PLACE)),
                cursor.getString(cursor.getColumnIndex(BucketList.COLUMN_VALUE)));

        // close the db connection
        cursor.close();

        return note;
    }

    public List<BucketList> getAllBucketList() {
        List<BucketList> mybucketlist = new ArrayList<>();

        // Select All Query
        String selectQuery = "SELECT  * FROM " + BucketList.TABLE_NAME + " ORDER BY " +
                BucketList.COLUMN_VALUE+ " DESC";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                BucketList note = new BucketList();
                note.setId(cursor.getInt(cursor.getColumnIndex(BucketList.COLUMN_ID)));
                note.setPlace(cursor.getString(cursor.getColumnIndex(BucketList.COLUMN_PLACE)));
                note.setValue(cursor.getString(cursor.getColumnIndex(BucketList.COLUMN_VALUE)));

                mybucketlist.add(note);
            } while (cursor.moveToNext());
        }

        // close db connection
        db.close();

        // return notes list
        return mybucketlist;
    }

    public int getBucketListCount() {
        String countQuery = "SELECT  * FROM " + BucketList.TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        int count = cursor.getCount();
        cursor.close();


        // return count
        return count;
    }

    public int updateBucketList(BucketList bucketList) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(BucketList.COLUMN_PLACE, bucketList.getPlace());
        values.put(BucketList.COLUMN_VALUE,bucketList.getValue());
        // updating row
        return db.update(BucketList.TABLE_NAME, values, BucketList.COLUMN_ID + " = ?",
                new String[]{String.valueOf(bucketList.getId())});
    }

    public void deleteBucketList(BucketList bucketList) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(BucketList.TABLE_NAME, BucketList.COLUMN_ID + " = ?",
                new String[]{String.valueOf(bucketList.getId())});
        db.close();
    }

}