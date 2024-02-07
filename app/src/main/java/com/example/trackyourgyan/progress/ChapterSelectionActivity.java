package com.example.trackyourgyan.progress;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;

import com.example.trackyourgyan.R;
import com.example.trackyourgyan.utils.Helpers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChapterSelectionActivity extends AppCompatActivity {
    String selectedSubject;
    private TextView subject_txt;
    private AutoCompleteTextView chapterSpinner;
    private ArrayAdapter<String> chapterAdapter;
    private Map<String, List<String>> subjectMap;
    private List<String> chaps;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chapter_selection);
        selectedSubject = getIntent().getStringExtra("selected_subject");

        subject_txt = findViewById(R.id.selected_subject);
        chapterSpinner = findViewById(R.id.chapters_dropdown);
        subject_txt.setText(selectedSubject);
        Helpers helpers = new Helpers();
        subjectMap = helpers.getSubjectChaptersMap();
        chaps = subjectMap.get(selectedSubject);
        chapterAdapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.dropdown_item, chaps);
        chapterSpinner.setAdapter(chapterAdapter);
    }
}