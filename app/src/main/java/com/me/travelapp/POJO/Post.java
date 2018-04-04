package com.me.travelapp.POJO;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.util.Base64;
import android.widget.ArrayAdapter;

import com.google.firebase.auth.FirebaseAuth;
import com.me.travelapp.R;

import java.io.ByteArrayOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;

/**
 * Created by Half_BlooD PrincE on 2/11/2018.
 */

public class Post implements Serializable {
    public int value;
    public String title;
    public String myUserId;
    public ArrayList<String> photo;
    public String blog="kgfhjloiuygfbvnb";

    public Post(){
        myUserId = FirebaseAuth.getInstance().getCurrentUser().getUid();
    }

    public Post(Context context)
    {
//        Bitmap bitmap= BitmapFactory.decodeResource(context.getResources(),
//                R.drawable.google_logo);
        //  photo=BitMapToString(bitmap);
    }

    public Post(ArrayList<String> post,String blog)
    {
        this.blog=blog;
        this.photo=photo;
        myUserId = FirebaseAuth.getInstance().getCurrentUser().getUid();
    }
    public Post(ArrayList<String> photo,String blog,String title)
    {
        this.title=title;
        this.blog=blog;
        this.photo=photo;
        myUserId = FirebaseAuth.getInstance().getCurrentUser().getUid();
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
    public static Comparator<Post> Comp = new Comparator<Post>() {

        public int compare(Post p1, Post p2) {
            int value1 = p1.getValue();
            int value2 = p2.getValue();

            return value1-value2;
        }};

    public void setTitle(String title){this.title=title;}
    public String getTitle(){return this.title;}
    public int getValue(){return value;}
    public void setValue(int value){this.value=value;}
    public String getBlog() {
        return blog;
    }

    public ArrayList<String> getPhoto() {
        return photo;
    }

    public void setBlog(String blog) {
        this.blog = blog;
    }

    public void setPhoto(ArrayList<String> photo) {
        this.photo = photo;
    }


    public ArrayList<Bitmap> retrivePhoto() {
        ArrayList<Bitmap> bPics=new ArrayList<>();
        if(photo!=null) {
            for (int i = 0; i < photo.size(); i++) {
                bPics.add(StringToBitMap(photo.get(i)));

            }
        }
        return  bPics;
    }


    public String getMyUserId() {
        return myUserId;
    }

    public void setMyUserId(String myUserId) {
        this.myUserId = myUserId;
    }
}
