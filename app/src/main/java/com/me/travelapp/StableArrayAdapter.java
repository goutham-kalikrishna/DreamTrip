package com.me.travelapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.HashMap;
import java.util.List;
import java.util.zip.Inflater;

public class StableArrayAdapter extends ArrayAdapter<String> {

    final int INVALID_ID = -1;

    HashMap<String, Integer> mIdMap = new HashMap<String, Integer>();
    Context context;

    public StableArrayAdapter(Context context, int textViewResourceId, List<String> objects) {
        super(context, textViewResourceId, objects);
        for (int i = 0; i < objects.size(); ++i) {
            mIdMap.put(objects.get(i), i);
        }
        this.context=context;
    }

    @Override
    public long getItemId(int position) {
        if (position < 0 || position >= mIdMap.size()) {
            return INVALID_ID;
        }
        String item = getItem(position);
        return mIdMap.get(item);
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        Log.d("test", "getView: "+getItem(position));
//        String str=getItem(position);
//        str=str.substring(0,3);
//        Log.d("tesst", "onItemLongClick: "+str);
//        if(str.equalsIgnoreCase("day"))
//        {
//            Log.d("tesst", "onItemLongClick: ");
//            View myview = LayoutInflater.from(context).inflate(R.layout.day_list_card, null);
//
//            return myview;
//        }
//        else
        return super.getView(position, convertView, parent);
    }
}
