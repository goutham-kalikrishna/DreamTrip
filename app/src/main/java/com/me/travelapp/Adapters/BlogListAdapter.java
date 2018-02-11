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
import android.widget.ImageView;
import android.widget.TextView;

import com.me.travelapp.POJO.Post;
import com.me.travelapp.R;

import java.util.ArrayList;

/**
 * Created by Half_BlooD PrincE on 2/11/2018.
 */

public class BlogListAdapter extends BaseAdapter {

    Context mContext;
    ArrayList<Post> posts;

    public BlogListAdapter(Context context, ArrayList<Post> posts)
    {
        mContext=context;
        this.posts=posts;

    }

    @Override
    public int getCount() {
        return posts.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View myView, ViewGroup viewGroup) {
        View view= LayoutInflater.from(mContext).inflate(R.layout.viewpager_card_blog,null);
        ImageView imageView=view.findViewById(R.id.blogImage);
        TextView textView=view.findViewById(R.id.blogText);
        TextView titleView=view.findViewById(R.id.blogTitle);

        Log.d("blog", "instantiateItem: "+position);

        String data = posts.get(position).getBlog();
        String[] result = data.split("\n", 2);

        imageView.setImageBitmap(StringToBitMap(posts.get(position).getPhoto()));
        titleView.setText(result[0]);
        if(result.length>1)
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
