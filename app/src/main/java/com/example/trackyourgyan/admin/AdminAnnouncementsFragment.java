package com.example.trackyourgyan.admin;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.trackyourgyan.R;
import com.example.trackyourgyan.adapters.AnnouncementAdapter;
import com.example.trackyourgyan.adapters.StudentAdapter;
import com.example.trackyourgyan.admin.AddAnnouncementActivity;
import com.example.trackyourgyan.objects.Announcement;
import com.example.trackyourgyan.objects.Student;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class AdminAnnouncementsFragment extends Fragment {

    FloatingActionButton floatingActionButton;
    private FirebaseFirestore db;
    private AnnouncementAdapter adapter;
    private RecyclerView recyclerView;

    private ArrayList<Announcement> announcementArrayList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_admin_announcements, container, false);
        floatingActionButton = view.findViewById(R.id.fab_add);
        announcementArrayList = new ArrayList<>();
        recyclerView = view.findViewById(R.id.admin_announcements_recyclerview);
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

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(view.getContext().getApplicationContext(), AddAnnouncementActivity.class));
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