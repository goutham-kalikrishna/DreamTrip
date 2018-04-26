package com.me.travelapp.POJO;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class UserData {



    public static void addUser(String uid,String username,String phoneNum,String email,String provider)
    {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef;
        myRef = database.getReference("Users").child(uid).child("Username");
        myRef.setValue(username);
        myRef = database.getReference("Users").child(uid).child("Mobile Number");
        myRef.setValue(phoneNum);
        myRef = database.getReference("Users").child(uid).child("Email");
        myRef.setValue(email);
        myRef = database.getReference("Users").child(uid).child("Provider");
        myRef.setValue(provider);
    }

    public static String getUserName(){

        return null;
    }

    public static String getProfilePicture(){

        return null;
    }

    public static String getMobileNumber(){

        return null;
    }
}
