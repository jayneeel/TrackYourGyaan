package com.example.trackyourgyan.progress;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.trackyourgyan.PastDetailResultActivity;
import com.example.trackyourgyan.R;
import com.example.trackyourgyan.adapters.ResultAdapter;
import com.example.trackyourgyan.adapters.StudentAdapter;
import com.example.trackyourgyan.interfaces.MyItemClick;
import com.example.trackyourgyan.objects.QuizResult;
import com.example.trackyourgyan.objects.Student;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class TrackResultActivity extends AppCompatActivity  implements MyItemClick {

    private RecyclerView recyclerView;
    private FirebaseFirestore db;
    private FirebaseAuth auth;
    private ResultAdapter adapter;
    private ArrayList<QuizResult> quizResultArrayList;
    int index = 0;
    Map<Integer, String> positionIdMap = new HashMap<>();

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_track_result);
        Log.d("!TRACKRESULTACTIVITY!", "onCreate: ***********************");
        quizResultArrayList = new ArrayList<>();
        recyclerView = findViewById(R.id.resultRecyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ResultAdapter(quizResultArrayList, this);
        recyclerView.setAdapter(adapter);
        db = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();

        db.collection("results").document(auth.getCurrentUser().getEmail()).collection("Advance Java").get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                            QuizResult quizResult = documentSnapshot.toObject(QuizResult.class);
                            quizResultArrayList.add(quizResult);
                            positionIdMap.put(index, documentSnapshot.getId());
                            index++;
                        }
                        adapter.notifyDataSetChanged();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });
    }

    @Override
    public void onStart() {
        super.onStart();
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onItemClick(String id, int position) {
        String documentReference = "LEVEL "+position+"_"+id;
        Intent intent= new Intent(getApplicationContext(), PastDetailResultActivity.class);
        intent.putExtra("documentReferenceId", documentReference);
        startActivity(intent);

    }
}