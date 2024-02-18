package com.example.trackyourgyan.progress;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.trackyourgyan.R;
import com.example.trackyourgyan.objects.Question;
import com.example.trackyourgyan.objects.Quiz;
import com.example.trackyourgyan.utils.Helpers;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class ChapterSelectionActivity extends AppCompatActivity {
    String selectedSubject, selectedChapter, subjectDb;
    int chapterNo;
    private TextView subjectTxt;
    private MaterialButton proceedBtn;
    private AutoCompleteTextView chapterSpinner;
    private ArrayAdapter<String> chapterAdapter;
    private Map<String, List<String>> subjectMap;
    public Map<String, String> subjectDbMap;
    private List<String> chaps;
    private FirebaseFirestore db;
    public ArrayList<Question> questionsList;
    Intent intent;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chapter_selection);
        selectedSubject = getIntent().getStringExtra("selected_subject");
        db = FirebaseFirestore.getInstance();
        subjectTxt = findViewById(R.id.selected_subject);
        chapterSpinner = findViewById(R.id.chapters_dropdown);
        proceedBtn = findViewById(R.id.proceed_chapter_btn);
        subjectTxt.setText(selectedSubject);
        questionsList = new ArrayList<>();
        Helpers helpers = new Helpers();
        subjectMap = helpers.getSubjectChaptersMap();
        subjectDbMap = helpers.getSubjectDbMap();
        chaps = subjectMap.get(selectedSubject);
        chapterAdapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.dropdown_item, chaps);
        chapterSpinner.setAdapter(chapterAdapter);
        subjectDb = subjectDbMap.get(selectedSubject);

        proceedBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedChapter = chapterSpinner.getText().toString();
                chapterNo =  chaps.indexOf(selectedChapter) + 1;
                Log.d("CHAPTER SELECTION!", selectedChapter);
                Log.d("CHAPTER SELECTION!", String.valueOf(chapterNo));
                Log.d("CHAPTER SELECTION!", subjectDb);
                db.collection(subjectDb).get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isComplete()){
                                    for(DocumentSnapshot queryDocumentSnapshot: task.getResult().getDocuments()){
                                        if(queryDocumentSnapshot.getReference().getId().startsWith(String.valueOf(chapterNo))){
                                            Log.d("TAG!!!!!!!!", queryDocumentSnapshot.getReference().getId());
                                            Question question = queryDocumentSnapshot.toObject(Question.class);
                                            questionsList.add(question);
                                        }
                                    }
                                    Collections.shuffle(questionsList);
                                    Quiz quiz = new Quiz(String.valueOf(System.currentTimeMillis()), selectedChapter, 5,questionsList);
                                    intent = new Intent(getApplicationContext(),McqTestActivity.class);
                                    intent.putExtra("quiz",quiz);
                                    startActivity(intent);
                                }
                            }
                        });
            }
        });
    }
}