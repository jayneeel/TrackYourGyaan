package com.example.trackyourgyan;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ExpandableListView;

import com.example.trackyourgyan.adapters.DetailedResultAdapter;
import com.example.trackyourgyan.objects.Quiz;

import java.util.HashMap;

public class DetailedResultActivity extends AppCompatActivity {
    ExpandableListView expandableListView;
    DetailedResultAdapter detailedResultAdapter;
    Quiz quiz;
    HashMap hashMap;
    int correctAnsCount;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_result);

        quiz = (Quiz) getIntent().getSerializableExtra("quizData");
        hashMap = (HashMap) getIntent().getSerializableExtra("result");
        correctAnsCount = getIntent().getIntExtra("correctAnsCount", 0);
        expandableListView = findViewById(R.id.expandable_result);
        detailedResultAdapter = new DetailedResultAdapter(this, quiz, hashMap);
        expandableListView.setAdapter(detailedResultAdapter);
        Log.d("**** DETAILED ****", hashMap.keySet().toString());
    }
}