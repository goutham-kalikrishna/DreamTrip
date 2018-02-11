package com.me.travelapp.POJO;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.util.Base64;

import com.google.firebase.auth.FirebaseAuth;
import com.me.travelapp.R;

import java.io.ByteArrayOutputStream;
import java.io.Serializable;

/**
 * Created by Half_BlooD PrincE on 2/11/2018.
 */

public class Post implements Serializable {

    String myUserId;
    String photo;
    String blog="kgfhjloiuygfbvnb";

    public Post(){
        myUserId = FirebaseAuth.getInstance().getCurrentUser().getUid();
    }

    public Post(Context context)
    {
        Bitmap bitmap= BitmapFactory.decodeResource(context.getResources(),
                R.drawable.google_logo);
        photo=BitMapToString(bitmap);
    }

    public Post(Bitmap bitmap,String blog)
    {
        this.blog=blog;
        photo = BitMapToString(bitmap);
        myUserId = FirebaseAuth.getInstance().getCurrentUser().getUid();
    }

    public String BitMapToString(Bitmap bitmap){
        ByteArrayOutputStream baos=new  ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,100, baos);
        byte [] b=baos.toByteArray();
        String temp= Base64.encodeToString(b, Base64.DEFAULT);
        return temp;
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

    public String getBlog() {
        return blog;
    }

    public String getPhoto() {
        return photo;
    }

    public void setBlog(String blog) {
        this.blog = blog;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public void savePhoto(Bitmap bitmap)
    {
        photo = BitMapToString(bitmap);
    }

    public Bitmap retrivePhoto() {

        return StringToBitMap(photo);
    }


    public String getMyUserId() {
        return myUserId;
    }

    public void setMyUserId(String myUserId) {
        this.myUserId = myUserId;
    }
}
