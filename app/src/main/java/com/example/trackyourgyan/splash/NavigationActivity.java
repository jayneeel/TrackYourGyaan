package com.example.trackyourgyan.splash;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.trackyourgyan.R;
import com.example.trackyourgyan.auth.SignInActivity;
import com.example.trackyourgyan.auth.SignUpActivity;

public class NavigationActivity extends AppCompatActivity {

    private Button signinBtn, signupBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);

        signinBtn = findViewById(R.id.signin_btn);
        signupBtn = findViewById(R.id.signup_btn);

        signinBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(NavigationActivity.this, SignInActivity.class));
            }
        });

        signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(NavigationActivity.this, SignUpActivity.class));
            }
        });
    }
}