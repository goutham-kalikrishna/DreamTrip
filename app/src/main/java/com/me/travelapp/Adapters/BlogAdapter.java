package com.me.travelapp.Adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.me.travelapp.POJO.Post;
import com.me.travelapp.R;


import java.util.ArrayList;

/**
 * Created by Half_BlooD PrincE on 2/11/2018.
 */

public class BlogAdapter extends PagerAdapter {

    Context mContext;
    ArrayList<Post> posts;
    public BlogAdapter(Context context, ArrayList<Post> posts)
    {
        mContext=context;
        this.posts=posts;

    }

    @Override
    public int getCount() {
        return posts.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);
    }




    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view= LayoutInflater.from(mContext).inflate(R.layout.viewpager_card_blog,container,false);
        ImageView imageView=view.findViewById(R.id.blogImage);
        TextView textView=view.findViewById(R.id.blogText);
        TextView titleView=view.findViewById(R.id.blogTitle);
        Log.d("blog", "instantiateItem: "+position);

        String data = posts.get(position).getBlog();
        String[] result = data.split("\n", 2);

        imageView.setImageBitmap(StringToBitMap(posts.get(position).getPhoto().get(0)));
        titleView.setText(result[0]);
        textView.setText(result[1]);
        return view;
    }

    public Bitmap StringToBitMap(String encodedString){
        try {
            byte [] encodeByte= Base64.decode(encodedString,Base64.DEFAULT);
            Bitmap bitmap= BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        } catch(Exception e) {
            e.getMessage();
            return null;
        }
    }
}
