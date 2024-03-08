package com.example.trackyourgyan.admin;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import com.example.trackyourgyan.R;
import com.example.trackyourgyan.utils.Helpers;
import com.google.android.material.button.MaterialButton;

import java.util.Arrays;

public class AddQuestionFragment extends Fragment {

    MaterialButton proceedBtn;
    private AutoCompleteTextView subjectSpinner;
    private ArrayAdapter<String> subjectAdapter;

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_question, container, false);
        subjectSpinner = view.findViewById(R.id.admin_year_dropdown);
        proceedBtn = view.findViewById(R.id.admin_year_proceed);
        subjectAdapter = new ArrayAdapter<String>(view.getContext(), R.layout.dropdown_item, Arrays.asList(new Helpers().ADMIN_SUBJECTS));
        subjectSpinner.setAdapter(subjectAdapter);
        proceedBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String dbSubject = subjectSpinner.getText().toString();
                Intent intent= new Intent(view.getContext(), AddQuestionActivity.class);
                intent.putExtra("subjectAdd",dbSubject);
                startActivity(intent);
            }
        });



        return  view;
    }
}