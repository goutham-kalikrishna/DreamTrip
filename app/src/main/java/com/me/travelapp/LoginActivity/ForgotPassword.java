package com.me.travelapp.LoginActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.me.travelapp.R;

public class ForgotPassword extends AppCompatActivity {

    private static final String TAG = "forgotPasswordActivity";
    private EditText email;
    private Button resend;
    private RelativeLayout homeLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        Window window= getWindow();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(Color.BLACK);
        }

        android.support.v7.app.ActionBar bar = getSupportActionBar();
        bar.setTitle(Html.fromHtml("<font color=\"black\">" + "Forgot Password" + "</font>"));
        bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#ffffff")));
        Drawable backArrow = getResources().getDrawable(R.drawable.back_icon);
        bar.setHomeAsUpIndicator(backArrow);
        bar.setDisplayHomeAsUpEnabled(true);


        homeLayout=findViewById(R.id.homeLayout);
        email=findViewById(R.id.forgot_email);
        resend=findViewById(R.id.reset_btn);
        resend.setEnabled(false);

        email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(email.getText().toString().length()>0)
                {
                    buttonOn();
                }
                else
                {
                    buttonOff();
                }
            }
        });

        resend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth auth = FirebaseAuth.getInstance();
                String emailAddress = email.getText().toString();
                Log.d(TAG, "Email sent.");

                auth.sendPasswordResetEmail(emailAddress)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Log.d(TAG, "Email sent.");
                                    Intent intent=new Intent(getApplicationContext(),LoginActivity.class);
                                    startActivity(intent);
                                    finish();
                                }
                                else{
                                    Snackbar.make(homeLayout,"Error Occured!!!",Snackbar.LENGTH_LONG).show();
                                }
                            }
                        });
            }
        });
    }


    void buttonOn()
    {
        resend.setEnabled(true);
        resend.setBackgroundResource(R.drawable.login_button_on_bg);
        resend.setAlpha(1);
        resend.setTextColor(Color.WHITE);
    }

    void buttonOff()
    {
        resend.setEnabled(false);
        resend.setBackgroundResource(R.drawable.login_button_bg);
        resend.setAlpha(0.7f);
        resend.setTextColor(getResources().getColor(R.color.colorPrimary));
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
