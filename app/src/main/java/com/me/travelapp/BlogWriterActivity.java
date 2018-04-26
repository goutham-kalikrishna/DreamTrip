package com.me.travelapp;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ImageSpan;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.me.travelapp.POJO.Post;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

public class BlogWriterActivity extends AppCompatActivity {

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef;
    int value,value1;
    static String data="";
    Post post;
    int check;
    EditText text,text1;
    ArrayList <Bitmap> addedImages;
    ImageView imageView1,imageView2,imageView3,imageView4;

    int Pos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addedImages=new ArrayList<>();
        setContentView(R.layout.activity_blog_writer);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
        Window window= getWindow();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(Color.BLACK);
        }
        text1=findViewById(R.id.TitleBlog);
        text=findViewById(R.id.blogEditView);
        imageView1=findViewById(R.id.img1);
        imageView2=findViewById(R.id.img2);
        imageView3=findViewById(R.id.img3);
        imageView4=findViewById(R.id.img4);

        imageView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (imageView1.getDrawable().getConstantState() == getResources().getDrawable(R.drawable.aimage).getConstantState()) {
                    check=1;
                    pickImage();
                }
            }
        });
        imageView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (imageView2.getDrawable().getConstantState() == getResources().getDrawable(R.drawable.aimage).getConstantState()) {
                    check=2;
                    pickImage();
                }
            }
        });
        imageView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (imageView3.getDrawable().getConstantState() == getResources().getDrawable(R.drawable.aimage).getConstantState()) {
                    check=3;
                    pickImage();
                }
            }
        });
        imageView4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (imageView4.getDrawable().getConstantState() == getResources().getDrawable(R.drawable.aimage).getConstantState()) {
                    check=4;
                    pickImage();
                }
            }
        });
//        imageView.setImageResource(R.drawable.google_logo);
//        readFromFile();
        text.setText(data);

        post=new Post();

        findViewById(R.id.backBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                writeToFile();
                finish();
            }
        });

        findViewById(R.id.publish).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateFirebase();
            }
        });

        findViewById(R.id.save).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                writeToFile();
            }
        });

//        findViewById(R.id.addImage).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                pickImage();
//            }
//        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        myRef = database.getReference("Blog").child("publicKey");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Integer val = dataSnapshot.getValue(Integer.class);
                value=val;
                Log.d("Tag", "Value is: " + value);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        myRef = database.getReference("Blog").child("count");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Integer val = dataSnapshot.getValue(Integer.class);
                value1=val;
                Log.d("Tag", "Value is: " + value);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void writeToFile() {
        data = text.getText().toString();
        String[] result = data.split("\n", 2);
        String name="travel.txt";
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(this.openFileOutput(name, Context.MODE_PRIVATE));
            outputStreamWriter.write(data);
            outputStreamWriter.close();
        }
        catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }

        Toast.makeText(this,"Saved Sucessfully",Toast.LENGTH_SHORT).show();
    }

    private String readFromFile() {

        String ret = "";
        String name="travel.txt";

        try {
            InputStream inputStream = this.openFileInput("name");

            if ( inputStream != null ) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString = "";
                StringBuilder stringBuilder = new StringBuilder();

                while ( (receiveString = bufferedReader.readLine()) != null ) {
                    stringBuilder.append(receiveString);
                }

                inputStream.close();
                ret = stringBuilder.toString();
            }
        }
        catch (FileNotFoundException e) {
            Log.e("login activity", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("login activity", "Can not read file: " + e.toString());
        }
        text.setText(ret);
        return ret;
    }

    public  void pickImage() {
        if (ActivityCompat.checkSelfPermission(BlogWriterActivity.this, android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(BlogWriterActivity.this, new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE, android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        }
        Intent intent = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("scale", true);
//        intent.putExtra("outputX", 50);
//        intent.putExtra("outputY", 50);
//        intent.putExtra("aspectX", 1);
//        intent.putExtra("aspectY", 1);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d("TAG", "onActivityResult: outside");
        if (requestCode == 1 && resultCode == RESULT_OK) {
            final Bundle extras = data.getExtras();
            if (data != null) {
                Uri selectedImage = data.getData();
                String[] filePathColumn = { MediaStore.Images.Media.DATA };

                Cursor cursor = getContentResolver().query(selectedImage,
                        filePathColumn, null, null, null);
                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                String picturePath = cursor.getString(columnIndex);
                cursor.close();

                Bitmap bitmap = BitmapFactory.decodeFile(picturePath);
                addedImages.add(0,bitmap);
                //post.savePhoto(bitmap);
                if(check==1) {
                    imageView1.setImageBitmap(bitmap);
                    imageView2.setImageResource(R.drawable.aimage);
                }
                else if(check==2){imageView2.setImageBitmap(bitmap);
                    imageView3.setImageResource(R.drawable.aimage);}
                else if(check==3){imageView3.setImageBitmap(bitmap);
                    imageView4.setImageResource(R.drawable.aimage);}
                else imageView4.setImageBitmap(bitmap);
                //gridView.setAdapter(horizontal_scroll_images_adapter);
            }
            else
                Log.d("TAG", "onActivityResult: ");

        }
        else
            Log.d("TAG", "onActivityResult: outside");

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults)
    {
        switch (requestCode) {
            case 1:
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Intent galleryIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(galleryIntent, 1);
                } else {
                    //do something like displaying a message that he didn`t allow the app to access gallery and you wont be able to let him select from gallery
                }
                break;
        }
    }


    void updateFirebase(){
        myRef = database.getReference("Blog").child("publicKey");
        post.setTitle(text1.getText().toString());
        post.setBlog(text.getText().toString());
        value++;
        ArrayList<String> arrayList=new ArrayList<>();
        for(int i=0;i<addedImages.size();i++)
        {
            arrayList.add(BitMapToString(addedImages.get(i)));
        }
        post.setPhoto(arrayList);
        post.setValue(value);
        String id="post"+value;
        Toast.makeText(this,id,Toast.LENGTH_SHORT).show();
        myRef = database.getReference("Blog").child("posts").child(id);
        myRef.setValue(post);

        myRef = database.getReference("Blog").child("publicKey");
        myRef.setValue(value);
        myRef = database.getReference("Blog").child("count");
        value1++;
        myRef.setValue(value1);
        finish();
    }


    @Override
    protected void onPause() {
        writeToFile();
        super.onPause();
    }


    @Override
    protected void onStop() {
        writeToFile();
        super.onStop();
    }


    public String BitMapToString(Bitmap bitmap){
        ByteArrayOutputStream baos=new  ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,100, baos);
        byte [] b=baos.toByteArray();
        String temp= Base64.encodeToString(b, Base64.DEFAULT);
        return temp;
    }


}
