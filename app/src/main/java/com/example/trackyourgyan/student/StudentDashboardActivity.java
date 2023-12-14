package com.example.trackyourgyan.student;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.trackyourgyan.R;
import com.example.trackyourgyan.objects.Student;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class StudentDashboardActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    private BottomNavigationView bottomNavigationView;
    private StudentDashboardFragment studentDashboardFragment;
    private StudentProfileFragment studentProfileFragment;
    private Bundle bundle;
    private FirebaseFirestore db;
    private String email;
    private Student student;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_dashboard);

        bottomNavigationView = findViewById(R.id.student_bnav);
        studentDashboardFragment = new StudentDashboardFragment();
        studentProfileFragment = new StudentProfileFragment();
        bundle = new Bundle();
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        db = FirebaseFirestore.getInstance();
        SharedPreferences settings = getSharedPreferences("TYG_PREFS", MODE_PRIVATE);
        email = settings.getString("studentEmail","");

        db.collection("students").document(email).get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        student = documentSnapshot.toObject(Student.class);
                        bundle.putSerializable("loggedInStudent",student);
                        bottomNavigationView.setSelectedItemId(R.id.timeline);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        if(item.getItemId() == R.id.timeline){
            studentDashboardFragment.setArguments(bundle);
            getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, studentDashboardFragment).commit();
            return true;
        }
        else if(item.getItemId() == R.id.profile){
            studentProfileFragment.setArguments(bundle);
            getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, studentProfileFragment).commit();
            return true;
        }

        return false;
    }
}