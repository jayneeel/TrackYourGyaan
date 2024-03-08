package com.example.trackyourgyan.progress;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.trackyourgyan.R;
import com.example.trackyourgyan.objects.Quiz;
import com.example.trackyourgyan.objects.QuizResult;
import com.example.trackyourgyan.student.StudentDashboardActivity;
import com.example.trackyourgyan.utils.Helpers;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

import pl.droidsonroids.gif.GifImageView;

public class ResultActivity extends AppCompatActivity {
    Quiz quiz;
    HashMap hashMap;
    TextView score, levelStatus, weak_chapters_txt;
    int correctAnsCount, totalQuestions;
    Button testAgainBtn, detailedResultBtn;
    GifImageView gifImageView;
    FirebaseFirestore db;
    FirebaseAuth auth;
    String userEmail, weakChaptersStr, chaptersList;
    Map<String, String> result=new HashMap<>();
    QuizResult quizResult;
    Timestamp timestamp;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        quiz = (Quiz) getIntent().getSerializableExtra("quizData");
        hashMap = (HashMap) getIntent().getSerializableExtra("result");
        correctAnsCount = getIntent().getIntExtra("correctAnsCount", 0);
        weakChaptersStr = getIntent().getStringExtra("weakChapter");
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
                db.collection("results").document(auth.getCurrentUser().getEmail()).collection(quizResult.getSubject()).document("Level "+(settings.getInt("currentLevel",0))+"_"+quizResult.getTimestamp()).collection("DETAILED_RESULT").document("result").set(new Helpers().convertMap(hashMap));
                db.collection("results").document(auth.getCurrentUser().getEmail()).collection(quizResult.getSubject()).document("Level "+(settings.getInt("currentLevel",0))+"_"+quizResult.getTimestamp()).collection("DETAILED_RESULT").document("questions").set(new Helpers().convertToQuestionList(quiz.quizQuestions));
                startActivity(intent);

            }
        });
    }

    public void showWeakChapters(View view) {
        showBottomSheet(weakChaptersStr);
    }

    @SuppressLint("MissingInflatedId")
    private void showBottomSheet(String weakChaptersStr) {
        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(
                this, com.google.android.material.R.style.Theme_Design_Light_BottomSheetDialog);
        View bottomSheetView = LayoutInflater.from(getApplicationContext())
                .inflate(R.layout.modal_bottom_sheet,
                        (LinearLayout)findViewById(R.id.bottom_sheet_layout));
        weak_chapters_txt = bottomSheetView.findViewById(R.id.weak_chapter_tv);
        chaptersList = new Helpers().convertToArrayList(weakChaptersStr);
        weak_chapters_txt.setText(chaptersList);
        bottomSheetDialog.setContentView(bottomSheetView);
        bottomSheetDialog.show();
    }
}