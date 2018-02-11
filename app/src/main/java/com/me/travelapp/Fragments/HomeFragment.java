package com.me.travelapp.Fragments;


import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.me.travelapp.HotelSelection;
import com.me.travelapp.POJO.Hotel;
import com.me.travelapp.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    int count;
    ArrayList<Hotel> hotel_list=new ArrayList<>();
    RequestQueue queue;
    Dialog dialog;
    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_home, container, false);
        Button submit=(Button)view.findViewById(R.id.submit);
        final EditText place=(EditText)view.findViewById(R.id.place);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                  dialog = new Dialog(getContext());
                dialog.setContentView(R.layout.loading_dialogue);
                dialog.show();
                if (place.getText().length() > 0) {
                   queue = Volley.newRequestQueue(getContext());
                    String url = "https://www.thrillophilia.com/places-to-visit-in-" + place.getText().toString();
                    String url1 = "https://www.goibibo.com/hotels/search-data/?app_id=5eaa10d1&app_key=3492979684358de58b7861ec719e6011&vcid=6771549831164675055&ci=20190219&co=20190220&r=1-2_0";
                    Log.d("lol", "https://www.thrillophilia.com/places-to-visit-in-" + place.getText().toString());

                    StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    // Display the first 500 characters of the response string.
                                    Log.e("Place", response.length() + "");
                                    Document doc = Jsoup.parse(response);
                                    Elements img = doc.select("meta").attr("property", "og:image");
                                    Log.e("Image", img.get(11).attr("content"));
//                                    Iterator itm=img.iterator();
//                                    for(int i=0;i<11;i++)
//                                    {
//                                        itm.next();
//                                    }
//                                    Log.e("Image",itm.next().toString());
                                    Elements link = doc.select("h3");
                                    Iterator itr = link.iterator();
                                    while (itr.hasNext()) {
                                        String lol = itr.next().toString();
                                        Log.e("Mystring", lol);
                                        Document doc1 = Jsoup.parse(lol);
                                        Elements span = doc1.select("span");
                                        if (span.size() > 1) {
                                            String rank = span.get(0).text();
                                            String name = span.get(1).text();
                                            Log.e("Rank", rank);
                                            Log.e("Area", name);

                                        }
                                    }
                                    // Log.d("Rank",link.toString());

                                }
                            }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.e("place", "That didn't work!");
                        }
                    });
                    final JsonObjectRequest jsObjRequest = new JsonObjectRequest
                            (Request.Method.GET, url1, null, new Response.Listener<JSONObject>() {

                                @Override
                                public void onResponse(JSONObject response) {
                                    try {
                                        JSONArray hotelsJ = response.getJSONArray("6771549831164675055");
                                        for (int i = 0; i < hotelsJ.length(); i++) {
                                            JSONObject hotel = hotelsJ.getJSONObject(i);
                                            String HotelName = hotel.getString("hn");
                                            Log.e("HotelName", HotelName);
                                            String url = hotel.getString("tbig");
                                            Log.e("url", url);
                                            double price = hotel.getDouble("tp_alltax");
                                            Log.e("price", price + "");
                                            double rating = hotel.getDouble("gr");
                                            Log.e("rating", rating + "");
                                            JSONArray facilities = hotel.getJSONArray("fm");
                                            String[] faci = new String[facilities.length()];
                                            for (int j = 0; j < facilities.length(); j++) {
                                                faci[j] = facilities.getString(j);
                                                Log.e("facilities", faci[j] + "");
                                            }
                                            //      Log.e("facilities",faci[0]+faci[1]);
                                            hotel_list.add(new Hotel(HotelName, price, faci, rating,url));
                                        }
                                       //      getImg();
                                        Intent intent=new Intent(getContext(),HotelSelection.class);
                                        Bundle bundle = new Bundle();
                                        bundle.putSerializable("Hotel_List",hotel_list);
                                        intent.putExtras(bundle);
                                        dialog.dismiss();
                                        startActivity(intent);
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }, new Response.ErrorListener() {

                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    // TODO Auto-generated method stub
                                    Toast.makeText(getContext(), "Fucked up", Toast.LENGTH_SHORT).show();

                                }
                            });
                    queue.add(jsObjRequest);
                    queue.add(stringRequest);

                }

            }
        });
        return view;
    }
    public void getImg()
    { for(int i=0;i<10;i++)
    {
        String url11 =hotel_list.get(i).url;

        count=i;
        ImageRequest request = new ImageRequest(url11,
                new Response.Listener<Bitmap>() {
                    @Override
                    public void onResponse(Bitmap bitmap) {
                        Log.e("pic",bitmap+"");
                        hotel_list.get(count).setBitmap(bitmap);
                        if(count==9)
                        {
                            Intent intent=new Intent(getContext(),HotelSelection.class);
                            Bundle bundle = new Bundle();
                            bundle.putSerializable("Hotel_List",hotel_list);
                            intent.putExtras(bundle);
                            dialog.dismiss();
                            startActivity(intent);
                        }
                    }
                }, 0, 0, null,
                new Response.ErrorListener() {
                    public void onErrorResponse(VolleyError error) {

                    }
                });
        queue.add(request);
    }
    }
}
