package com.example.iTube;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;


import com.example.iTube.data.DatabaseHelper;
import com.example.iTube.model.User;


public class SignupActivity extends AppCompatActivity {
    DatabaseHelper db;
    ImageView image;
    public static final String[] permissionArray = {Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE};

    Context mContext = this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        EditText sUsernameEditText = findViewById(R.id.sUsernameEditText);
        EditText sPasswordEditText = findViewById(R.id.sPasswordEditText);
        EditText confirmPasswordEditText = findViewById(R.id.confirmPasswordEditText);
        EditText sFullName = findViewById(R.id.sFullName);

        Button saveButton = findViewById(R.id.saveButton);
        image = findViewById(R.id.image);
        db = new DatabaseHelper(mContext);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String username = sUsernameEditText.getText().toString();
                String password = sPasswordEditText.getText().toString();
                String confirmPassword = confirmPasswordEditText.getText().toString();
                String fullName = sFullName.getText().toString();

                try {
                    if (password.equals(confirmPassword)&&!(username.isEmpty()))
                    {
                        long result = db.insertUser(new User(fullName,username, password));
                        Log.d("reached",Long.toString(result));
                        if (result > 0)
                        {
                            Toast.makeText(SignupActivity.this, "Registered successfully!", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(SignupActivity.this,MainActivity.class);
                            startActivity(intent);
                        }
                        else
                        {
                            Toast.makeText(SignupActivity.this, "Registration error!", Toast.LENGTH_SHORT).show();
                        }

                    }
                    else
                    {
                        if(password.equals(confirmPassword)&&!password.isEmpty())
                            Toast.makeText(SignupActivity.this, "Please enter values in all relevant fields", Toast.LENGTH_SHORT).show();
                        else
                            Toast.makeText(SignupActivity.this, "Passwords doesn't match", Toast.LENGTH_SHORT).show();
                    }

                }catch (Exception e){
                    Toast.makeText(SignupActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });

        sUsernameEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });

        sPasswordEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });

        sFullName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });


        confirmPasswordEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });

    }
    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager =(InputMethodManager)getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

}