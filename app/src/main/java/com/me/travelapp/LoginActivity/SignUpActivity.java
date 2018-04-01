package com.me.travelapp.LoginActivity;

import android.app.ActionBar;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextWatcher;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.me.travelapp.R;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener{

    private static final String TAG = "SignUpActivity";

    private EditText username;
    private EditText password;
    private EditText email;
    private EditText phone;
    private RelativeLayout loginBtn;
    private Button signUp;
    private ProgressBar progressBar;
    private Spinner spinner;
    private RelativeLayout homeLayout;
    private TextView loginText;

    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        mAuth=FirebaseAuth.getInstance();

        Window window= getWindow();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(Color.BLACK);
        }

        android.support.v7.app.ActionBar bar = getSupportActionBar();
        bar.setTitle(Html.fromHtml("<font color=\"black\">" + "SignUp" + "</font>"));
        bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#ffffff")));
        Drawable backArrow = getResources().getDrawable(R.drawable.back_icon);
        bar.setHomeAsUpIndicator(backArrow);
        bar.setDisplayHomeAsUpEnabled(true);


        progressBar=findViewById(R.id.login_progress);
        progressBar.getIndeterminateDrawable().setColorFilter(getResources().getColor(R.color.colorPrimary), PorterDuff.Mode.MULTIPLY);
        progressBar.setVisibility(View.INVISIBLE);

        signUp=findViewById(R.id.signup_btn);
        signUp.setEnabled(false);
        signUp.setOnClickListener(this);
        loginBtn=findViewById(R.id.redirectToLogin);

        username=findViewById(R.id.signup_username);
        password=findViewById(R.id.signup_password);
        email=findViewById(R.id.signup_email);
        phone=findViewById(R.id.signup_phone);
        homeLayout=findViewById(R.id.homeLayout);

        spinner =  findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.planets_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        username.addTextChangedListener(new TextWatcher() {
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

        password.addTextChangedListener(new TextWatcher() {
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

        email.addTextChangedListener(new TextWatcher() {
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

        phone.addTextChangedListener(new TextWatcher() {
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

        password.addTextChangedListener(new TextWatcher() {
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

        String bold="Log In.";
        String normal="Already have an account? ";
        SpannableString str=new SpannableString(normal+bold);
        str.setSpan(new StyleSpan(Typeface.BOLD),normal.length(),normal.length()+bold.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        loginText=findViewById(R.id.loginText);
        loginText.setText(str);
        loginText.setTextColor(Color.DKGRAY);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(),LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
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

    void btnStateChange()
    {
        if(!password.getText().toString().equals("")&&!username.getText().toString().equals("")&&!email.getText().toString().equals("")&&!phone.getText().toString().equals(""))
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
        signUp.setEnabled(true);
        signUp.setBackgroundResource(R.drawable.login_button_on_bg);
        signUp.setAlpha(1);
        signUp.setTextColor(Color.WHITE);
    }

    void buttonOff()
    {
        signUp.setEnabled(false);
        signUp.setBackgroundResource(R.drawable.login_button_bg);
        signUp.setAlpha(0.7f);
        signUp.setTextColor(getResources().getColor(R.color.colorPrimary));
    }

    @Override
    public void onClick(View view) {
        int i = view.getId();
        if (i == R.id.signup_btn) {
            signUp.setText("");
            signUp.setBackgroundResource(R.drawable.login_button_bg);
            signUp.setAlpha(0.7f);
            progressBar.setVisibility(View.VISIBLE);

            if(phone.getText().toString().length()==10)
            {
                mAuth.createUserWithEmailAndPassword(email.getText().toString().trim(), password.getText().toString())
                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    Log.d(TAG, "createUserWithEmail:success");
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    Toast.makeText(SignUpActivity.this, "Authentication Sucessful.", Toast.LENGTH_SHORT).show();
                                    Intent intent=new Intent(SignUpActivity.this,LoginActivity.class);

                                    UserProfileChangeRequest profileUpdates= new UserProfileChangeRequest.Builder()
                                            .setDisplayName(username.getText().toString())
                                            .build();

                                    user.updateProfile(profileUpdates)
                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if (task.isSuccessful()) {
                                                        Log.d(TAG, "User profile updated.");
                                                    }
                                                }
                                            });

                                    user.sendEmailVerification()
                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if (task.isSuccessful()) {
                                                        Log.d(TAG, "Email sent.");
                                                    }
                                                }
                                            });

                                    startActivity(intent);
                                    finish();
                                    Snackbar.make(homeLayout,"Verify email to activate account!!!",Snackbar.LENGTH_LONG).show();
                                } else {
                                    // If sign in fails, display a message to the user.
                                    Log.w(TAG, "createUserWithEmail:failure", task.getException());
                                    try {
                                        throw task.getException();
                                    } catch (FirebaseAuthWeakPasswordException e) {
                                        password.setError("Invalid Password");
                                        password.requestFocus();
                                    } catch (FirebaseAuthInvalidCredentialsException e) {
                                        username.setError("Invalid");
                                        username.requestFocus();
                                    } catch (FirebaseAuthUserCollisionException e) {
//                                    Toast.makeText(LoginActivity.this, "Authentication failed: " + e.getMessage(), Toast.LENGTH_LONG).show();
                                        Snackbar.make(homeLayout,"Account already exists!!!",Snackbar.LENGTH_SHORT).show();
                                    } catch (Exception e) {
                                        Log.e(TAG, e.getMessage());

                                    }
                                }
                                signUp.setText("Log In");
                                progressBar.setVisibility(View.INVISIBLE);
                                buttonOn();
                            }
                        });
            }
            else{
                signUp.setText("Log In");
                progressBar.setVisibility(View.INVISIBLE);
                buttonOn();
            }

        }
    }
}
