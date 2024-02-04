package com.example.trackyourgyan.progress;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import com.example.trackyourgyan.ChapterSelectionActivity;
import com.example.trackyourgyan.R;
import com.example.trackyourgyan.objects.Question;
import com.example.trackyourgyan.objects.Quiz;
import com.example.trackyourgyan.objects.Student;
import com.example.trackyourgyan.utils.Helpers;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class ProgressFragment extends Fragment {
    private Student student;
    private List<String> subs;
    private MaterialButton quickTestBtn, chapterWiseBtn;
    private AutoCompleteTextView subjectSpinner;
    private ArrayAdapter<String> subjectAdapter;
    private Intent intent;
    public ArrayList<Question> questionsList;
    private FirebaseFirestore db;


    Helpers helpers = new Helpers();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getArguments();
        if (arguments != null) {
            student = (Student) arguments.getSerializable("loggedInStudent");
        }
        switch (student.getYear()){
            case "First Year":
                subs = Arrays.asList(helpers.FIRST_YEAR_SUBJECTS);
                break;

            case "Third Year":
                subs = Arrays.asList(helpers.THIRD_YEAR_SUBJECTS);
                break;
        }

    }

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_progress, container, false);
        db = FirebaseFirestore.getInstance();
        questionsList = new ArrayList<>();
        subjectSpinner = view.findViewById(R.id.subjects_dropdown);
        subjectAdapter = new ArrayAdapter<String>(view.getContext(), R.layout.dropdown_item, subs);
        subjectSpinner.setAdapter(subjectAdapter);
        quickTestBtn = view.findViewById(R.id.option_one_btn);
        chapterWiseBtn = view.findViewById(R.id.option_two_btn);

        quickTestBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db = FirebaseFirestore.getInstance();
                Log.d("******************************","STARTED______________________________");
                db.collection("ajp").get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isComplete()){
                                    for(DocumentSnapshot queryDocumentSnapshot: task.getResult().getDocuments()){
                                        Question question = queryDocumentSnapshot.toObject(Question.class);
                                        questionsList.add(question);
                                    }
                                    Log.d("---------------*---------------", "onComplete: "+questionsList.size());
                                    Quiz quiz = new Quiz(String.valueOf(System.currentTimeMillis()), subjectSpinner.getText().toString(), 5,questionsList);
                                    intent = new Intent(view.getContext(),McqTestActivity.class);
                                    intent.putExtra("quiz",quiz);
                                    startActivity(intent);
                                }
                            }
                        });
            }
        });


        chapterWiseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(view.getContext(), ChapterSelectionActivity.class);
                startActivity(intent);
            }
        });


        return view;
    }
}