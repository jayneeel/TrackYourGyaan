package com.example.trackyourgyan.progress;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.trackyourgyan.R;
import com.example.trackyourgyan.objects.Quiz;
import com.example.trackyourgyan.student.StudentDashboardActivity;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

import pl.droidsonroids.gif.GifImageView;

public class ResultActivity extends AppCompatActivity {
    Quiz quiz;
    HashMap hashMap;
    TextView score, levelStatus;
    int correctAnsCount, totalQuestions;
    Button testAgainBtn, detailedResultBtn;
    GifImageView gifImageView;
    FirebaseFirestore db;
    String userEmail;
    Map<String, String> result=new HashMap<>();


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        quiz = (Quiz) getIntent().getSerializableExtra("quizData");
        hashMap = (HashMap) getIntent().getSerializableExtra("result");
        correctAnsCount = getIntent().getIntExtra("correctAnsCount", 0);
        score = findViewById(R.id.score);
        testAgainBtn = findViewById(R.id.test_again_btn);
        levelStatus = findViewById(R.id.level_status_text);
        gifImageView = findViewById(R.id.completed_gif);
        detailedResultBtn = findViewById(R.id.detailed_result);
        totalQuestions = quiz.quizQuestions.size();
        db = FirebaseFirestore.getInstance();
        SharedPreferences settings = getSharedPreferences("TYG_PREFS", MODE_PRIVATE);
        userEmail = settings.getString("studentEmail",null);
        result.put("correct_ans", String.valueOf(correctAnsCount));
        score.setText(correctAnsCount+" / "+totalQuestions);

        if (!(correctAnsCount >= (totalQuestions/2))){
            gifImageView.setImageResource(R.drawable.try_again);
            levelStatus.setText("You couldn't pass this level");
            result.put("result", "FAIL");
        }else{
            result.put("result", "PASS");
            db.collection("results").document(userEmail).collection(quiz.title).add(result);
        }
        testAgainBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), StudentDashboardActivity.class));
            }
        });

        detailedResultBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), DetailedResultActivity.class);
                intent.putExtra("result", hashMap);
                intent.putExtra("quizData", quiz);
                intent.putExtra("correctAnsCount", correctAnsCount);
                startActivity(intent);

            }
        });

    }
}