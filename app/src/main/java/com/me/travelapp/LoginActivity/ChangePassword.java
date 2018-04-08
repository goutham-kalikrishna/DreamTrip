package com.me.travelapp.LoginActivity;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.NonNull;
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
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.me.travelapp.R;

public class ChangePassword extends AppCompatActivity {

    private EditText currentPassword;
    private EditText newPassword;
    private EditText repeatPassword;
    private Button changePassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

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


        currentPassword=findViewById(R.id.currentPassword);
        newPassword=findViewById(R.id.newPassword);
        repeatPassword=findViewById(R.id.repeatOldPassword);
        changePassword=findViewById(R.id.change_pwd_btn);

        currentPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                btnStateChange();
            }
        });

        newPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                btnStateChange();
            }
        });

        repeatPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                btnStateChange();
            }
        });

        changePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                AuthCredential credential = EmailAuthProvider
                        .getCredential(user.getEmail(), currentPassword.getText().toString());

                user.reauthenticate(credential)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    user.updatePassword(newPassword.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                Log.d("Tag", "Password updated");
                                                finish();
                                            } else {
                                                Log.d("Tag", "Error password not updated");
                                            }
                                        }
                                    });
                                } else {
                                    currentPassword.setError("Incorrect Password");
                                    Log.d("Tag", "Error auth failed");
                                }
                            }
                        });
                buttonOff();
            }
        });
    }


    void btnStateChange()
    {
        if(!currentPassword.getText().toString().equals("")&&!newPassword.getText().toString().equals("")&&!repeatPassword.getText().toString().equals(""))
        {
            buttonOn();
        }
        else
        {
            buttonOff();
        }
    }

    void buttonOn()
    {
        changePassword.setEnabled(true);
        changePassword.setBackgroundResource(R.drawable.login_button_on_bg);
        changePassword.setAlpha(1);
        changePassword.setTextColor(Color.WHITE);
    }

    void buttonOff()
    {
        changePassword.setEnabled(false);
        changePassword.setBackgroundResource(R.drawable.login_button_bg);
        changePassword.setAlpha(0.7f);
        changePassword.setTextColor(getResources().getColor(R.color.colorPrimary));
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
