package com.me.travelapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.me.travelapp.POJO.Post;

public class FullBlogAdapter extends AppCompatActivity {

    TextView textView;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_blog_adapter);

        Bundle bundle = getIntent().getExtras();
//        Post post = bundle.getParcelable("data");
        Post post  = (Post) getIntent().getSerializableExtra("data");
        textView=findViewById(R.id.blogText1);
        textView.setText(post.getBlog());

        imageView=findViewById(R.id.blogImage1);
        imageView.setImageBitmap(post.retrivePhoto());
    }
}
