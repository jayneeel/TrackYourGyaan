package com.example.trackyourgyan.student;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.trackyourgyan.progress.McqTestActivity;
import com.example.trackyourgyan.R;
import com.example.trackyourgyan.about_us.AboutUsActivity;
import com.example.trackyourgyan.objects.Student;
import com.example.trackyourgyan.progress.ProgressFragment;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class StudentDashboardActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private BottomNavigationView bottomNavigationView;
    private StudentDashboardFragment studentDashboardFragment;
    private StudentProfileFragment studentProfileFragment;
    private ProgressFragment progressFragment;
    private NavigationView navigationView;
    private Bundle bundle;
    private FirebaseFirestore db;
    private String email;
    private Student student;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_dashboard);

        bottomNavigationView = findViewById(R.id.student_bnav);
        drawerLayout = findViewById(R.id.drawerLayout);
        navigationView = findViewById(R.id.navigationView);
        studentDashboardFragment = new StudentDashboardFragment();
        studentProfileFragment = new StudentProfileFragment();
        progressFragment = new ProgressFragment();
        bundle = new Bundle();
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open_drawer, R.string.close_drawer);
        actionBarDrawerToggle.setDrawerSlideAnimationEnabled(true);
        actionBarDrawerToggle.setDrawerIndicatorEnabled(true);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
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

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if(item.getItemId() == R.id.drawer_home){
                    studentDashboardFragment.setArguments(bundle);
                    getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, studentDashboardFragment).commit();
                    return true;
                }
                else if(item.getItemId() == R.id.drawer_progress){
                    startActivity(new Intent(getApplicationContext(), McqTestActivity.class));
                    return true;
                }
                else if(item.getItemId() == R.id.drawer_about_us){
                    startActivity(new Intent(getApplicationContext(), AboutUsActivity.class));
                    return true;
                }

                return false;
            }
        });
    }



    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        if(item.getItemId() == R.id.timeline){
            studentDashboardFragment.setArguments(bundle);
            getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, studentDashboardFragment).commit();
            return true;
        }else if(item.getItemId() == R.id.progress){
            progressFragment.setArguments(bundle);
            getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, progressFragment).commit();
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