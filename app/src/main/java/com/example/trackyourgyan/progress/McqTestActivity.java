package com.example.trackyourgyan.progress;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.trackyourgyan.R;
import com.example.trackyourgyan.objects.Quiz;
import com.example.trackyourgyan.utils.Helpers;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class McqTestActivity extends AppCompatActivity {
    Quiz quiz;
    TextView questionTxt, questionCount, timer;
    Button optionA, optionB, optionC, optionD, nextBtn, backBtn;
    int currentIndex = -1, questionNumber, correctAnsCount = 0;
    String selectedAns = "";
    HashMap<Integer, List<String>> resultMap = new HashMap<>();
    List<String> weakChaptersList;
    Map<String, String> questionResult;
    Helpers helpers = new Helpers();


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mcq_test);
        quiz = (Quiz) getIntent().getSerializableExtra("quiz");
        questionTxt = findViewById(R.id.question_txt);
        timer = findViewById(R.id.timer);
        optionA = findViewById(R.id.ans_A);
        optionB = findViewById(R.id.ans_B);
        optionC = findViewById(R.id.ans_C);
        optionD = findViewById(R.id.ans_D);
        nextBtn = findViewById(R.id.next_btn);
        backBtn = findViewById(R.id.previous_question_btn);
        questionCount = findViewById(R.id.question_count_txt);
        weakChaptersList = new ArrayList<>();
        loadQuestions(1);
        new CountDownTimer(900000, 1000) {
            public void onTick(long millisUntilFinished) {
                // Used for formatting digit to be in 2 digits only
                NumberFormat f = new DecimalFormat("00");
                long hour = (millisUntilFinished / 3600000) % 24;
                long min = (millisUntilFinished / 60000) % 60;
                long sec = (millisUntilFinished / 1000) % 60;
                timer.setText(f.format(hour) + ":" + f.format(min) + ":" + f.format(sec));
            }
            // When the task is over it will print 00:00:00 there
            public void onFinish() {
                timer.setText("00:00:00");
                Toast.makeText(McqTestActivity.this, "Times Up!", Toast.LENGTH_SHORT).show();
                endTest();
            }
        }.start();
    }

    private void loadQuestions(int index) {
        if (currentIndex >= 1){
            backBtn.setEnabled(true);
        }
        if (questionNumber == quiz.quizQuestions.size()){
            Log.d("*McqTestActivity*", "loadQuestions: *********EVERYTHING GOOOD");
            endTest();
        }
        resetColor();
        currentIndex = currentIndex + index;
        questionNumber = currentIndex +1;
        Log.d("******* MCQ TEST *******", String.valueOf(currentIndex));
        Log.d("******* MCQ TEST *******", String.valueOf(questionNumber));
        questionCount.setText(String.format("%d / %d", questionNumber, quiz.quizQuestions.size()));
        questionTxt.setText(quiz.quizQuestions.get(currentIndex).question);
        optionA.setText(quiz.quizQuestions.get(currentIndex).option_a);
        optionB.setText(quiz.quizQuestions.get(currentIndex).option_b);
        optionC.setText(quiz.quizQuestions.get(currentIndex).option_c);
        optionD.setText(quiz.quizQuestions.get(currentIndex).option_d);
    }

    private void endTest() {
        Intent intent = new Intent(getApplicationContext(), ResultActivity.class);
        intent.putExtra("result", resultMap);
        intent.putExtra("quizData", quiz);
        intent.putExtra("correctAnsCount", correctAnsCount);
        intent.putExtra("weakChapter", weakChaptersList.toString());
//            intent.putCharSequenceArrayListExtra("resultData", resultList);
        startActivity(intent);
    }


    public void optionSelection(View view) {
        Button button = (Button) view;
        if (button.getId() == R.id.next_btn){
            checkAns(selectedAns);
            loadQuestions(1);
        }else{
            resetColor();
            button.setBackgroundColor(getColor(R.color.orange));
            selectedAns = button.getText().toString();
        }

    }

    private void checkAns(String selectedAns) {
        String result = (selectedAns.equals(quiz.quizQuestions.get(currentIndex).correct)) ? "CORRECT" : "INCORRECT";
        if (result.equals("CORRECT")){
            correctAnsCount += 1;
        }else{
            weakChaptersList.add(helpers.getSubjectChaptersMap().get(quiz.title).get(quiz.quizQuestions.get(currentIndex).chapter - 1));
        }
        resultMap.put(questionNumber, Arrays.asList(result, selectedAns,String.valueOf(quiz.quizQuestions.get(currentIndex).chapter)));
        Log.d("EXAM***********", "checkAns: "+resultMap.keySet());
        Log.d("EXAM***********", "checkAns: "+resultMap.values());
    }

    private void resetColor() {
        optionA.setBackgroundColor(getColor(R.color.gray));
        optionB.setBackgroundColor(getColor(R.color.gray));
        optionC.setBackgroundColor(getColor(R.color.gray));
        optionD.setBackgroundColor(getColor(R.color.gray));
    }

    public void previousQuestion(View view) {
        resetColor();
        loadQuestions(-1);
    }
}