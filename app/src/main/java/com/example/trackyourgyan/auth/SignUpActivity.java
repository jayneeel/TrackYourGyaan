package com.example.trackyourgyan.auth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.trackyourgyan.objects.Student;
import com.example.trackyourgyan.student.StudentDashboardActivity;
import com.example.trackyourgyan.R;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class SignUpActivity extends AppCompatActivity {
    private ImageView backBtn;
    private AutoCompleteTextView yearSpinner, deptSpinner;
    private ArrayAdapter<String> yearAdapter, deptAdapter;
    private TextInputEditText fullnameET,emailET,passET;
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore db;
    private MaterialButton signupBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        db = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        fullnameET = findViewById(R.id.full_name);
        emailET = findViewById(R.id.email);
        passET = findViewById(R.id.password);

        yearSpinner = findViewById(R.id.year);
        deptSpinner = findViewById(R.id.dept);

        signupBtn = findViewById(R.id.registerBtn);
        backBtn = findViewById(R.id.back_btn);

        ArrayList<String> yearList = new ArrayList<>();
        yearList.add("First Year");
        yearList.add("Second Year");
        yearList.add("Third Year");



        ArrayList<String> deptList = new ArrayList<>();
        deptList.add("Computer Engineering");
        deptList.add("Information Technology");
        deptList.add("Civil Engineering");
        deptList.add("Mechanical Engineering");

        yearAdapter = new ArrayAdapter<String>(SignUpActivity.this, R.layout.dropdown_item, yearList);
        deptAdapter = new ArrayAdapter<String>(SignUpActivity.this, R.layout.dropdown_item, deptList);

        yearSpinner.setAdapter(yearAdapter);
        deptSpinner.setAdapter(deptAdapter);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String fullname_txt = fullnameET.getText().toString();
                String year_txt = yearSpinner.getText().toString();
                String dept_txt = deptSpinner.getText().toString();
                String email_txt = emailET.getText().toString();
                String password_txt = passET.getText().toString();

                Student student = new Student(fullname_txt, email_txt, year_txt, dept_txt);


                firebaseAuth.createUserWithEmailAndPassword(email_txt,password_txt)
                        .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                            @Override
                            public void onSuccess(AuthResult authResult) {
                                    db.collection("students").document(student.getEmail()).set(student);
                                    Toast.makeText(SignUpActivity.this, "Account Created Successfully!", Toast.LENGTH_LONG).show();
                                    appendToSharedPrefs(student);
                                    Intent intent = new Intent(SignUpActivity.this, StudentDashboardActivity.class);
                                    finishAffinity();
                                    startActivity(intent);
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(SignUpActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
                            }
                        });

            }
        });


    }

    private void appendToSharedPrefs(Student student) {
        SharedPreferences sharedPreferences = getSharedPreferences("TYG_PREFS", MODE_PRIVATE);
        SharedPreferences.Editor prefEditor = sharedPreferences.edit();
        prefEditor.putBoolean("isLoggedIn", true);
        prefEditor.putString("studentEmail", student.getEmail());
        prefEditor.apply();
    }

}