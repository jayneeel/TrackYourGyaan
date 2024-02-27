package com.example.trackyourgyan.progress;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.trackyourgyan.R;
import com.example.trackyourgyan.objects.Quiz;
import com.example.trackyourgyan.objects.QuizResult;
import com.example.trackyourgyan.student.StudentDashboardActivity;
import com.example.trackyourgyan.utils.Helpers;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
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
    FirebaseAuth auth;
    String userEmail;
    Map<String, String> result=new HashMap<>();
    QuizResult quizResult;
    Timestamp timestamp;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        Log.d("RESUUUUUUUUUULLT","*******STARTED**************");
        quiz = (Quiz) getIntent().getSerializableExtra("quizData");
        hashMap = (HashMap) getIntent().getSerializableExtra("result");
        correctAnsCount = getIntent().getIntExtra("correctAnsCount", 0);
        score = findViewById(R.id.score);
        testAgainBtn = findViewById(R.id.test_again_btn);
        levelStatus = findViewById(R.id.level_status_text);
        gifImageView = findViewById(R.id.completed_gif);
        detailedResultBtn = findViewById(R.id.detailed_result);
        quizResult = new QuizResult();
        totalQuestions = quiz.quizQuestions.size();
        db = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
        SharedPreferences settings = getSharedPreferences("TYG_PREFS", MODE_PRIVATE);
        SharedPreferences.Editor prefEditor = settings.edit();
        userEmail = settings.getString("studentEmail",null);
        result.put("correct_ans", String.valueOf(correctAnsCount));
        score.setText(correctAnsCount+" / "+totalQuestions);
        quizResult.setScore(correctAnsCount);
        quizResult.setSubject(quiz.title);
        quizResult.setUid(auth.getUid());
        quizResult.setTimestamp(new Helpers().getTimestamp());

        if (!(correctAnsCount >= (totalQuestions/2))){
            gifImageView.setImageResource(R.drawable.try_again);
            levelStatus.setText("You couldn't pass this level");
            result.put("result", "FAIL");
            quizResult.setStatus("Fail");
        }else{
            levelStatus.setText("LEVEL "+settings.getInt("currentLevel",0)+" Completed");
            quizResult.setStatus("Pass");
            prefEditor.putInt("currentLevel",settings.getInt("currentLevel",0) + 1);
            prefEditor.putString("currentSubject",quizResult.getSubject());
            prefEditor.apply();
            db.collection("results").document(auth.getCurrentUser().getEmail()).collection(quizResult.getSubject()).document("Level "+(settings.getInt("currentLevel",0))+"_"+quizResult.getTimestamp()).set(quizResult);
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