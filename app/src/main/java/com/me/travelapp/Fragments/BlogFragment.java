package com.me.travelapp.Fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.me.travelapp.Adapters.BlogAdapter;
import com.me.travelapp.Adapters.BlogListAdapter;
import com.me.travelapp.BlogWriterActivity;
import com.me.travelapp.POJO.Post;
import com.me.travelapp.R;
import com.me.travelapp.Utils.VerticalViewPager;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class BlogFragment extends Fragment {

    VerticalViewPager viewPager;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef;
    ArrayList<Post> postArrayList=new ArrayList<>();
    ListView listView;

    FloatingActionButton floatingActionButton;
    public static final String TAG="BlogFragment";


    public BlogFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_blog, container, false);
        viewPager=view.findViewById(R.id.mVerticalViewPager);
        listView=view.findViewById(R.id.listBlog);

        getBlogs();

        floatingActionButton=view.findViewById(R.id.fab);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity().getApplication(), BlogWriterActivity.class);
                startActivity(i);
            }
        });

        return view;
    }

    void getBlogs()
    {
        myRef = database.getReference("Blog").child("posts");
        Post post=new Post(getContext());
        Log.d(TAG, "Value is: ");

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Post user = snapshot.getValue(Post.class);
                    postArrayList.add(user);
                    Log.d(TAG, "onDataChange: "+user.getMyUserId());
                }
                updateUI();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    void updateUI()
    {
        Log.d(TAG, "updateUI: "+postArrayList.size());
        if(postArrayList.size()==0)
        {
            Log.d(TAG, "updateUI: if"+postArrayList.size());
            viewPager.setVisibility(View.GONE);
        }
        else
        {
            Log.d(TAG, "updateUI: else"+postArrayList.size());
//            viewPager.setVisibility(View.VISIBLE);
//            viewPager.setAdapter(new BlogAdapter(getContext(),postArrayList));
            listView.setAdapter(new BlogListAdapter(getContext(),postArrayList));
        }
    }

}
