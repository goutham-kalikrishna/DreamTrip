package com.me.travelapp.Adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.me.travelapp.HotelSelection;
import com.me.travelapp.POJO.Hotel;
import com.me.travelapp.R;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

/**
 * Created by j.girish on 11-02-2018.
 */

public class Hotel_Adapter extends BaseAdapter {
    Context context;
    int [] pic={R.drawable.hotel0,R.drawable.hotel1,R.drawable.hotel2,R.drawable.hotel3,R.drawable.hotel4,R.drawable.hotel5,R.drawable.hotel6,R.drawable.hotel7,R.drawable.hotel8,R.drawable.hotel9};
    ArrayList <Hotel> Hotel_list;
     int checked_position=-1;
    RequestQueue queue;
    public Hotel_Adapter(Context context, ArrayList<Hotel> Hotel_list)
    {
        this.context=context;
        this.Hotel_list=Hotel_list;
        queue = Volley.newRequestQueue(context);
    }
    @Override
    public int getCount() {
        return 10;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
       final View myview = LayoutInflater.from(context).inflate(R.layout.domain_list, null);
        TextView domain = (TextView) myview.findViewById(R.id.HotelName);
        final ImageView img = (ImageView) myview.findViewById(R.id.HotelImg);
        domain.setText(Hotel_list.get(position).HotelName);
        String url = Hotel_list.get(position).url;
        Random random=new Random();
        img.setImageBitmap(Hotel_list.get(position).bitmap);
      //  img.setImageResource(pic[random.nextInt(10)]);
      //  Log.e("Bitmanp",Hotel_list.get(position).bitmap+"");
        TextView hotelrating=(TextView)myview.findViewById(R.id.hotelrating);
        hotelrating.setText(" Ratings : "+Hotel_list.get(position).rating);
        TextView hotelprice=(TextView)myview.findViewById(R.id.hotelprice);
        hotelprice.setText(" Best Deal : "+Hotel_list.get(position).price + "  INR");
        final CheckBox check=(CheckBox)myview.findViewById(R.id.selectHotel);
        if(checked_position!=-1 && position==checked_position)
        {
            check.setChecked(true);
        }
        check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(check.isChecked())
                {
                    checked_position=position;
                    HotelSelection.fname.setText(Hotel_list.get(position).HotelName );
                    HotelSelection.fprice.setText("Hotel Bill : "+Hotel_list.get(position).price + "  INR");
                    HotelSelection.relativeLayout.setVisibility(View.VISIBLE);
                    notifyDataSetChanged();
                }
                else
                {
                    Log.e("Removing","Unchecked");
                    HotelSelection.relativeLayout.setVisibility(View.INVISIBLE);
                    checked_position=-1;

                }
            }
        });
        ImageRequest request = new ImageRequest(url,
                new Response.Listener<Bitmap>() {
                    @Override
                    public void onResponse(Bitmap bitmap) {
                        Log.e("pic",bitmap+"");
                        img.setImageBitmap(bitmap);

                    }
                }, 0, 0, null,
                new Response.ErrorListener() {
                    public void onErrorResponse(VolleyError error) {
                        img.setImageResource(R.drawable.image_load_error);
                    }
                });
        queue.add(request);
        return myview;

    }

}
