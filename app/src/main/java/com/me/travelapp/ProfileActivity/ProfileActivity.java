package com.me.travelapp.ProfileActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.me.travelapp.BlogWriterActivity;
import com.me.travelapp.R;

import java.io.ByteArrayOutputStream;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileActivity extends AppCompatActivity {


    private FirebaseDatabase database;
    private DatabaseReference myRef;
    private FirebaseAuth mAuth;

    boolean changed=false;
    Spinner gender;
    String[] genderArray = { "Not Specified", "Male", "Female", };
    EditText userName;
    EditText phoneNumber;
    EditText bio;
    EditText email;
    CircleImageView photo;
    TextView changePhoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        Window window= getWindow();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(Color.BLACK);
        }

        android.support.v7.app.ActionBar bar = getSupportActionBar();
        bar.setTitle(Html.fromHtml("<font color=\"black\">" + "Edit Profile" + "</font>"));
        bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#ffffff")));
        Drawable backArrow = getResources().getDrawable(R.drawable.cancel_profile);
        bar.setHomeAsUpIndicator(backArrow);
        bar.setDisplayHomeAsUpEnabled(true);

        mAuth= FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();

        gender=findViewById(R.id.user_sex);
        userName=findViewById(R.id.user_name);
        phoneNumber=findViewById(R.id.user_phone);
        bio=findViewById(R.id.user_bio);
        email=findViewById(R.id.user_email);
        photo=findViewById(R.id.user_profile_photo);
        changePhoto=findViewById(R.id.changePhoto);

        ArrayAdapter aa = new ArrayAdapter(this,android.R.layout.simple_spinner_item,genderArray );
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        gender.setAdapter(aa);

        setUsername();
        setEmail();
        setProfilePic();
        setMobileNumber();
        setBio();
        setGender();

        userName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                changed=true;
            }
        });
        bio.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                changed=true;
            }
        });
        phoneNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                changed=true;
            }
        });

        changePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pickImage();
            }
        });

    }

    void setUsername(){
        myRef = database.getReference("Users").child(mAuth.getUid()).child("Username");
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String value = dataSnapshot.getValue(String.class);
//                Log.d(TAG, "Value is: " + value);
                userName.setText(value);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    void setGender(){
        myRef = database.getReference("Users").child(mAuth.getUid()).child("Gender");
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String value = dataSnapshot.getValue(String.class);
//                Log.d(TAG, "Value is: " + value);
                if(value.equals("Male"))
                    gender.setSelection(1);
                else if(value.equals("Female"))
                    gender.setSelection(2);
                else
                    gender.setSelection(0);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public  void pickImage() {
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE, android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
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
                photo.setImageBitmap(bitmap);
                myRef = database.getReference("Users").child(mAuth.getUid()).child("Photo");
                myRef.setValue(BitMapToString(bitmap));
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

    void setEmail(){
        myRef = database.getReference("Users").child(mAuth.getUid()).child("Email");
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String value = dataSnapshot.getValue(String.class);
//                Log.d(TAG, "Value is: " + value);
                email.setText(value);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    void setMobileNumber(){
        myRef = database.getReference("Users").child(mAuth.getUid()).child("Mobile Number");
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String value = dataSnapshot.getValue(String.class);
//                Log.d(TAG, "Value is: " + value);
                phoneNumber.setText(value);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


    void setProfilePic(){
        myRef = database.getReference("Users").child(mAuth.getUid()).child("Photo");
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String value = dataSnapshot.getValue(String.class);
//                Log.d(TAG, "Value is: " + value);
                if(value!=null){
                    photo.setImageBitmap(StringToBitMap(value));
                    changePhoto.setText("Upload Profile Picture");
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
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

    public String BitMapToString(Bitmap bitmap){
        ByteArrayOutputStream baos=new  ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,100, baos);
        byte [] b=baos.toByteArray();
        String temp= Base64.encodeToString(b, Base64.DEFAULT);
        return temp;
    }

    void setBio(){
        myRef = database.getReference("Users").child(mAuth.getUid()).child("Bio");
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String value = dataSnapshot.getValue(String.class);
                bio.setText(value);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                if(changed)
                {
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
                    alertDialogBuilder.setMessage("You have unsaved changes. Are you sure you want to cancel");
                    alertDialogBuilder.setPositiveButton(Html.fromHtml("<font color=\"black\">" + "Save" + "</font>"),
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface arg0, int arg1) {
                                            updateData();
                                            finish();
                                        }
                                    });

                     alertDialogBuilder.setNegativeButton(Html.fromHtml("<font color=\"black\">" + "Discard" + "</font>"),new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    });

                    AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();
                }
                else
                    finish();

                break;

            case R.id.profile_save_details:
                updateData();
                finish();
        }

        return super.onOptionsItemSelected(item);
    }


    void updateData()
    {
        myRef = database.getReference("Users").child(mAuth.getUid()).child("Username");
        myRef.setValue(userName.getText().toString());

        myRef = database.getReference("Users").child(mAuth.getUid()).child("Bio");
        myRef.setValue(bio.getText().toString());

        myRef = database.getReference("Users").child(mAuth.getUid()).child("Gender");
        if(gender.getSelectedItemPosition()==1)
        {
            Log.d("TAG", "updateData: male");
            myRef.setValue("Male");
        }
        else if(gender.getSelectedItemPosition()==2)
        {
            Log.d("TAG", "updateData: male");
            myRef.setValue("Female");
        }

        myRef = database.getReference("Users").child(mAuth.getUid()).child("Mobile Number");
        myRef.setValue(phoneNumber.getText().toString());

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.edit_profile_menu, menu);
        return true;
    }
}
