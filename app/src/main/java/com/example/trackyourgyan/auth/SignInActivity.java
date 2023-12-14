package com.example.trackyourgyan.auth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.trackyourgyan.admin.AdminDashboardActivity;
import com.example.trackyourgyan.student.StudentDashboardActivity;
import com.example.trackyourgyan.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class SignInActivity extends AppCompatActivity {

    private TextInputEditText emailET,passET;
    private MaterialButton loginBtn;
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore db;
    private ImageView backBtn;

    private TextView forgetPassTxt, createAccTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);
        db = FirebaseFirestore.getInstance();
        emailET = findViewById(R.id.email);
        passET = findViewById(R.id.pwd);
        loginBtn = findViewById(R.id.loginBtn);
        firebaseAuth = FirebaseAuth.getInstance();
        backBtn = findViewById(R.id.back_btn);
        forgetPassTxt = findViewById(R.id.forget_pass_txt);
        createAccTxt = findViewById(R.id.create_acc_txt);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        forgetPassTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SignInActivity.this, ForgetPasswordActivity.class));
            }
        });

        createAccTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SignInActivity.this, SignUpActivity.class));
            }
        });

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email_txt = emailET.getText().toString();
                String pass_txt = passET.getText().toString();

                firebaseAuth.signInWithEmailAndPassword(email_txt,pass_txt)
                        .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                            @Override
                            public void onSuccess(AuthResult authResult) {

                                finishAffinity();
                                Toast.makeText(SignInActivity.this, "Login Successful!", Toast.LENGTH_SHORT).show();
                                SharedPreferences sharedPreferences = getSharedPreferences("TRG_PREFS", MODE_PRIVATE);
                                SharedPreferences.Editor prefEditor = sharedPreferences.edit();
                                prefEditor.putBoolean("isLoggedIn", true);
                                if(authResult.getUser().getEmail().toString().equals("admin@gmail.com")){
                                    startActivity(new Intent(SignInActivity.this, AdminDashboardActivity.class));
                                }else{
                                    prefEditor.putString("studentEmail", authResult.getUser().getEmail());
                                    startActivity(new Intent(SignInActivity.this, StudentDashboardActivity.class));
                                }
                                prefEditor.apply();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(SignInActivity.this, "Bad Credentials", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });
    }
}