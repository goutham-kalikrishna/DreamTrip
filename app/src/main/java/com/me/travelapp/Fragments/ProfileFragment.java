package com.me.travelapp.Fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.me.travelapp.BucketListActivity;
import com.me.travelapp.LoginActivity.ChangePassword;
import com.me.travelapp.LoginActivity.LoginActivity;
import com.me.travelapp.ProfileActivity.ProfileActivity;
import com.me.travelapp.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {


    public ProfileFragment() {
        // Required empty public constructor
    }

    TextView logoutBtn;
    TextView changePassword;
    ImageView editProfile;
    TextView bucketlist;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        logoutBtn=view.findViewById(R.id.logout);
        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent i = new Intent(getContext(), LoginActivity.class);
                startActivity(i);
            }
        });


        bucketlist=view.findViewById(R.id.bucketlist);
        bucketlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getContext(), BucketListActivity.class);
                startActivity(intent);
            }
        });

        changePassword= view.findViewById(R.id.changePassword);
        changePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getContext(), ChangePassword.class);
                startActivity(intent);
            }
        });

        editProfile=view.findViewById(R.id.edit_profile);
        editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getContext(), ProfileActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }

}
