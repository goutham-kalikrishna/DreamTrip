package com.me.travelapp.Adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.me.travelapp.BlogWriterActivity;
import com.me.travelapp.FullBlogAdapter;
import com.me.travelapp.MainActivity;
import com.me.travelapp.POJO.Post;
import com.me.travelapp.PlacesSelectionActivity;
import com.me.travelapp.R;

import java.util.ArrayList;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

/**
 * Created by Half_BlooD PrincE on 2/11/2018.
 */

public class BlogListAdapter extends BaseAdapter {

    Context mContext;
    static public ArrayList<Post> posts;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef;
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
    public View getView(final int position, View myView, ViewGroup viewGroup) {
        View view;
        if(posts.get(position).getPhoto()!=null) {
            view = LayoutInflater.from(mContext).inflate(R.layout.viewpager_card_blog, null);
            ImageView imageView = view.findViewById(R.id.blogImage);
            TextView textView = view.findViewById(R.id.blogText);
            TextView titleView = view.findViewById(R.id.blogTitle);
            Button Like = view.findViewById(R.id.likeR);
            Button Share = view.findViewById(R.id.ShareR);
            Button ReadMore = view.findViewById(R.id.ReadmoreR);
            Log.d("blog", "instantiateItem: " + position);

            imageView.setImageBitmap(StringToBitMap(posts.get(position).getPhoto().get(0)));
            titleView.setText(posts.get(position).getTitle());
            textView.setText(posts.get(position).getBlog());
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent in = new Intent(mContext.getApplicationContext(), FullBlogAdapter.class);
                    in.putExtra("position",position);
                    mContext.startActivity(in);
                }
            });
            Like.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    myRef = database.getReference("Blog").child("posts").child("post" + posts.get(position).getValue()).child("Likes");

                }
            });
            ReadMore.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent in = new Intent(mContext.getApplicationContext(), FullBlogAdapter.class);
                    in.putExtra("position",position);
                    mContext.startActivity(in);
                }
            });
            Share.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent sendIntent = new Intent();
                    sendIntent.setAction(Intent.ACTION_SEND);
                    sendIntent.putExtra(Intent.EXTRA_TEXT, posts.get(position).getBlog());
                    //sendIntent.putExtra(Intent.EXTRA_SUBJECT, "");
                    //  sendIntent.putExtra(Intent.EXTRA_STREAM, StringToBitMap(posts.get(position).getPhoto()));
                    sendIntent.setType("text/plain");
                    mContext.startActivity(sendIntent);
                }
            });
        }
        else
        {
            view = LayoutInflater.from(mContext).inflate(R.layout.viewpager_card_blog_text, null);
            TextView textView = view.findViewById(R.id.blogText1);
            TextView titleView = view.findViewById(R.id.blogTitle1);
            Button Like = view.findViewById(R.id.likeR1);
            Button Share = view.findViewById(R.id.ShareR1);
            Button ReadMore = view.findViewById(R.id.ReadmoreR1);
            Log.d("blog", "instantiateItem: " + position);
            titleView.setText(posts.get(position).getTitle());
            textView.setText(posts.get(position).getBlog());
            Like.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    myRef = database.getReference("Blog").child("posts").child("post" + posts.get(position).getValue()).child("Likes");

                }
            });
            ReadMore.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent in = new Intent(mContext.getApplicationContext(), FullBlogAdapter.class);
                    in.putExtra("position",position);
                    mContext.startActivity(in);
                }
            });
            Share.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent sendIntent = new Intent();
                    sendIntent.setAction(Intent.ACTION_SEND);
                    sendIntent.putExtra(Intent.EXTRA_TEXT, posts.get(position).getBlog());
                    //sendIntent.putExtra(Intent.EXTRA_SUBJECT, "");
                    //  sendIntent.putExtra(Intent.EXTRA_STREAM, StringToBitMap(posts.get(position).getPhoto()));
                    sendIntent.setType("text/plain");
                    mContext.startActivity(sendIntent);
                }
            });

        }
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
