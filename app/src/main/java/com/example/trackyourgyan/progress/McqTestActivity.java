package com.example.trackyourgyan.progress;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.trackyourgyan.R;
import com.example.trackyourgyan.objects.Quiz;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class McqTestActivity extends AppCompatActivity {
    Quiz quiz;
    TextView questionTxt, questionCount;
    Button optionA, optionB, optionC, optionD, nextBtn;
    int currentIndex = -1, questionNumber, correctAnsCount =0;
    String selectedAns = "";
    HashMap<Integer, List<String>> resultMap = new HashMap<>();

    List<String> weakChaptersList;
    Map<String, String> questionResult;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mcq_test);
        quiz = (Quiz) getIntent().getSerializableExtra("quiz");
        questionTxt = findViewById(R.id.question_txt);
        optionA = findViewById(R.id.ans_A);
        optionB = findViewById(R.id.ans_B);
        optionC = findViewById(R.id.ans_C);
        optionD = findViewById(R.id.ans_D);
        nextBtn = findViewById(R.id.next_btn);
        questionCount = findViewById(R.id.question_count_txt);
        loadQuestions();
    }

    private void loadQuestions() {
        if (questionNumber == 15){
            Intent intent = new Intent(getApplicationContext(), ResultActivity.class);
            intent.putExtra("result", resultMap);
            intent.putExtra("quizData", quiz);
            intent.putExtra("correctAnsCount", correctAnsCount);
//            intent.putCharSequenceArrayListExtra("resultData", resultList);
            startActivity(intent);
        }


        resetColor();
        currentIndex++;
        questionNumber = currentIndex +1;
        questionCount.setText(String.format("%d / 15", questionNumber));
        questionTxt.setText(quiz.quizQuestions.get(currentIndex).question);
        optionA.setText(quiz.quizQuestions.get(currentIndex).option_a);
        optionB.setText(quiz.quizQuestions.get(currentIndex).option_b);
        optionC.setText(quiz.quizQuestions.get(currentIndex).option_c);
        optionD.setText(quiz.quizQuestions.get(currentIndex).option_d);
    }


    public void optionSelection(View view) {
        Button button = (Button) view;
        if (button.getId() == R.id.next_btn){
            checkAns(selectedAns);
            loadQuestions();
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
}