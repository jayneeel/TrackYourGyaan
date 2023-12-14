package com.example.trackyourgyan.student;

import static android.content.Context.MODE_PRIVATE;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.trackyourgyan.R;
import com.example.trackyourgyan.adapters.AnnouncementAdapter;
import com.example.trackyourgyan.objects.Announcement;
import com.example.trackyourgyan.objects.Student;
import com.example.trackyourgyan.splash.NavigationActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class StudentProfileFragment extends Fragment {

    private MaterialButton logoutBtn;
    private Student student;
    private TextView fullName, dept, year, email;





    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getArguments();
        if (arguments != null) {
            student = (Student) arguments.getSerializable("loggedInStudent");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_student_profile, container, false);
        logoutBtn = view.findViewById(R.id.logout_btn);
        fullName = view.findViewById(R.id.profile_full_name);
        year = view.findViewById(R.id.profile_year);
        dept = view.findViewById(R.id.profile_dept);
        email = view.findViewById(R.id.profile_email);

        fullName.setText(student.getFirstName());
        email.setText(student.getEmail());
        year.setText(student.getYear());
        dept.setText(student.getDept());




        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences settings = view.getContext().getSharedPreferences("TYG_PREFS", MODE_PRIVATE);
                SharedPreferences.Editor prefEditor = settings.edit();
                prefEditor.putBoolean("isLoggedIn",false);
                prefEditor.putString("studentEmail","");
                prefEditor.apply();
                Toast.makeText(view.getContext(), "Logout Successful", Toast.LENGTH_SHORT).show();
                getActivity().finishAffinity();
                startActivity(new Intent(view.getContext().getApplicationContext(), NavigationActivity.class));
            }
        });

        return view;
    }


}