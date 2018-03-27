package com.me.travelapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.Volley;
import com.me.travelapp.Adapters.Hotel_Adapter;
import com.me.travelapp.POJO.Hotel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class HotelSelection extends AppCompatActivity {
    ArrayList <Hotel> Hotel_list;
    int count=0;
    ListView listView;
    Hotel_Adapter adapter;
    RequestQueue queue;
 public   static RelativeLayout relativeLayout;
 public   static TextView fname,fprice;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotel_selection);
       // getActionBar().setTitle("Hotel Booking");
     //   getSupportActionBar().setTitle("Hotel Booking");
        Intent in=getIntent();
        relativeLayout=(RelativeLayout)findViewById(R.id.hotelbill);
        fname=(TextView)findViewById(R.id.fname) ;
        fprice=(TextView)findViewById(R.id.fprice) ;
       Button select=(Button)findViewById(R.id.Hproceed);
       select.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent intent=new Intent(HotelSelection.this,ListViewDraggingAnimation.class);
               Bundle bundle = new Bundle();

               bundle.putString("Hotel name",fname.getText().toString());
               bundle.putString("Hotel price",fprice.getText().toString());
               intent.putExtras(bundle);
               startActivity(intent);
           }
       });
        queue = Volley.newRequestQueue(getApplicationContext());
        Bundle bundle = getIntent().getExtras();
       Hotel_list=(ArrayList<Hotel>) bundle.getSerializable("Hotel_List");
         listView=(ListView)findViewById(R.id.HotelList);
         adapter=new Hotel_Adapter(getApplicationContext(),Hotel_list);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Take appropriate action for each action item click
        switch (item.getItemId()) {
            case R.id.priceSort:
                Collections.sort(Hotel_list,new Comparator<Hotel>() {
                    @Override
                    public int compare(Hotel o1, Hotel o2) {
                        if(o1.price>o2.price)
                            return 1;
                        else if(o1.price<o2.price)
                            return -1;
                        else
                            return 0;
                    }
                });
                Log.e("Hotel_list",Hotel_list.toString());
                for(int i=0;i<10;i++)
                {
                    String url = "http://theopentutorials.com/totwp331/wp-content/uploads/totlogo.png";

                    count=i;
                    ImageRequest request = new ImageRequest(url,
                            new Response.Listener<Bitmap>() {
                                @Override
                                public void onResponse(Bitmap bitmap) {
                                    Log.e("pic",bitmap+"");
                                    Hotel_list.get(count).bitmap=bitmap;
                                    if(count==9)
                                    {
                                        while(Hotel_list.get(9).bitmap==null){}
                                        listView.setAdapter(adapter);
                                    }
                                }
                            }, 0, 0, null,
                            new Response.ErrorListener() {
                                public void onErrorResponse(VolleyError error) {

                                }
                            });
                    queue.add(request);
                }
                count=0;
       //         adapter=new Hotel_Adapter(getApplicationContext(),Hotel_list);
               listView.setAdapter(adapter);
               return true;
            case R.id.ratingSort:
                Collections.sort(Hotel_list,new Comparator<Hotel>() {
                    @Override
                    public int compare(Hotel o1, Hotel o2) {
                        if(o1.rating>o2.rating)
                            return -1;
                        else if(o1.rating<o2.rating)
                            return 1;
                        else
                            return 0;
                    }
                });
                Log.e("Hotel_list",Hotel_list.toString());
                for(int i=0;i<10;i++)
                {
                    String url = Hotel_list.get(i).url;

                    count=i;
                    ImageRequest request = new ImageRequest(url,
                            new Response.Listener<Bitmap>() {
                                @Override
                                public void onResponse(Bitmap bitmap) {
                                    Log.e("pic",bitmap+"");
                                    Hotel_list.get(count).bitmap=bitmap;
                                  if(count==9)
                                  {
                                      while(Hotel_list.get(9).bitmap==null){}
                                      listView.setAdapter(adapter);
                                  }
                                }
                            }, 0, 0, null,
                            new Response.ErrorListener() {
                                public void onErrorResponse(VolleyError error) {

                                }
                            });
                    queue.add(request);
                }
                count=0;
            //    adapter=new Hotel_Adapter(getApplicationContext(),Hotel_list);
                listView.setAdapter(adapter);
                return true;
            default:
                return true;
        }
    }
}

