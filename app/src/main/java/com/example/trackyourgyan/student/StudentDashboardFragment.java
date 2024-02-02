package com.example.trackyourgyan.student;

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
import com.example.trackyourgyan.objects.Student;
import com.example.trackyourgyan.objects.Announcement;


import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;


public class StudentDashboardFragment extends Fragment {

    private Student student;
    private TextView welcomeStudentName;
    private FirebaseFirestore db;
    private AnnouncementAdapter adapter;
    private RecyclerView recyclerView;

    private ArrayList<Announcement> announcementArrayList;
    CollapsingToolbarLayout collapsingToolbarLayout;





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
        View view = inflater.inflate(R.layout.fragment_student_dashboard, container, false);

        welcomeStudentName = view.findViewById(R.id.dashboardName);
        collapsingToolbarLayout = view.findViewById(R.id.collapse_toolbar);
        welcomeStudentName.setText("Hi\n"+student.getFirstName()+"!");
        announcementArrayList = new ArrayList<>();
        recyclerView = view.findViewById(R.id.announcementRecyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new AnnouncementAdapter(announcementArrayList);
        recyclerView.setAdapter(adapter);
        db = FirebaseFirestore.getInstance();
        db.collection("announcements").get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                            Log.d("----------ANNOUNCEMENTS------",documentSnapshot.getReference().getId().toString());
                            Announcement announcement = documentSnapshot.toObject(Announcement.class);
                            announcementArrayList.add(announcement);
                        }
                        adapter.notifyDataSetChanged();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        adapter.notifyDataSetChanged();
    }


}