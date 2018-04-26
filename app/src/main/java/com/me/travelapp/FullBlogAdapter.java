package com.me.travelapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.me.travelapp.Adapters.BlogListAdapter;
import com.me.travelapp.Adapters.ViewPagerAdapter;
import com.me.travelapp.POJO.Post;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.zip.Inflater;

public class FullBlogAdapter extends AppCompatActivity {

    ViewPager viewPager;
    TextView t1;
    boolean a=true;
    ArrayList< Bitmap> x;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent=getIntent();
        int position=intent.getIntExtra("position",0);
        setContentView(R.layout.activity_full_blog_adapter);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(BlogListAdapter.posts.get(position).getTitle());

        setSupportActionBar(toolbar);
        //Show "back" button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        viewPager=(ViewPager)findViewById(R.id.viewPager);

        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(this,BlogListAdapter.posts.get(position).retrivePhoto());

        viewPager.setAdapter(viewPagerAdapter);


        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new MyTimerTask(),2000,4000);

        t1=(TextView)findViewById(R.id.content);
        t1.setText(BlogListAdapter.posts.get(position).getBlog());
//        t1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                if(a){
//                    t1.setMaxLines(2);
//                    a=false;
//                }
//                else{
//                    t1.setMaxLines(Integer.MAX_VALUE);
//                    a=true;
//                }
//            }
//        });






    }


    public class MyTimerTask extends TimerTask {
        @Override
        public void run() {
            FullBlogAdapter.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if(viewPager.getCurrentItem() == 0)
                    {
                        viewPager.setCurrentItem(1);
                    }else if(viewPager.getCurrentItem() == 1)
                    {
                        viewPager.setCurrentItem(2);
                    }else
                    {
                        viewPager.setCurrentItem(0);
                    }
                }
            });
        }
    }
}
