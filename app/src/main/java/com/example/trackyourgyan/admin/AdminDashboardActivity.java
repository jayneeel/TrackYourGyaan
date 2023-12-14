package com.example.trackyourgyan.admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.trackyourgyan.R;
import com.example.trackyourgyan.student.StudentListFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomnavigation.BottomNavigationView.OnNavigationItemSelectedListener;
import com.google.firebase.firestore.FirebaseFirestore;

public class AdminDashboardActivity extends AppCompatActivity implements OnNavigationItemSelectedListener {
    private BottomNavigationView bottomNavigationView;
    private StudentListFragment studentListFragment;
    private AdminAnnouncementsFragment adminAnnouncementsFragment;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_dashboard);
        bottomNavigationView = findViewById(R.id.admin_bnav);
        studentListFragment = new StudentListFragment();
        adminAnnouncementsFragment = new AdminAnnouncementsFragment();
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        bottomNavigationView.setSelectedItemId(R.id.students);
        db = FirebaseFirestore.getInstance();

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.students){
            getSupportFragmentManager().beginTransaction().replace(R.id.admin_frame_container, studentListFragment).commit();
            return true;

        }else if(item.getItemId() == R.id.announcements){
            getSupportFragmentManager().beginTransaction().replace(R.id.admin_frame_container, adminAnnouncementsFragment).commit();
            return true;
        }
        return false;
    }
}