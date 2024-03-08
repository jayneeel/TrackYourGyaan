package com.example.trackyourgyan.admin;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import com.example.trackyourgyan.R;
import com.example.trackyourgyan.objects.Question;
import com.example.trackyourgyan.utils.Helpers;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Arrays;
import java.util.List;

public class AddQuestionActivity extends AppCompatActivity {
    private String subject;
    TextInputEditText question, option_A,option_B, option_C, option_D;
    MaterialButton proceedBtn;
    private AutoCompleteTextView chapterSpinner, optionSpinner;

    private ArrayAdapter<String> chapterAdapter, optionAdapter;
    private List<String> chapters;
    FirebaseFirestore db;
    Helpers helpers = new Helpers();





    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_question);
        subject = getIntent().getStringExtra("subjectAdd");
        db = FirebaseFirestore.getInstance();
        question = findViewById(R.id.question_txt_admin);
        option_A = findViewById(R.id.option_a);
        option_B = findViewById(R.id.option_b);
        option_C = findViewById(R.id.option_c);
        option_D = findViewById(R.id.option_d);
        proceedBtn = findViewById(R.id.add_question_final);
        chapterSpinner = findViewById(R.id.admin_chapter_dropdown);
        chapters =new Helpers().getSubjectChaptersMap().get(subject);
        chapterAdapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.dropdown_item, chapters);
        chapterSpinner.setAdapter(chapterAdapter);
        optionSpinner = findViewById(R.id.admin_option_dropdown);
        optionAdapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.dropdown_item, Arrays.asList(helpers.OPTIONS));
        optionSpinner.setAdapter(optionAdapter);


        proceedBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String questionTxt = question.getEditableText().toString();
                String optionATxt = option_A.getEditableText().toString();
                String optionBTxt = option_B.getEditableText().toString();
                String optionCTxt = option_C.getEditableText().toString();
                String optionDTxt = option_D.getEditableText().toString();
                String correct = "";
                int chapterNo = chapters.indexOf(chapterSpinner.getText().toString()) + 1;
                switch (optionSpinner.getText().toString()){
                    case "A":
                        correct = optionATxt;
                        break;

                    case "B":
                        correct = optionBTxt;
                        break;

                    case "C":
                        correct = optionCTxt;
                        break;

                    case "D":
                        correct = optionDTxt;
                        break;
                }
                Question question = new Question(questionTxt,optionATxt, optionBTxt, optionCTxt, optionDTxt, correct, chapterNo);
                db.collection(helpers.getSubjectDbMap().get(subject)).document(chapterNo+"_"+helpers.getTimestamp()).set(question);
                Toast.makeText(AddQuestionActivity.this, "Question Added Successfully", Toast.LENGTH_SHORT).show();
            }
        });


    }
}