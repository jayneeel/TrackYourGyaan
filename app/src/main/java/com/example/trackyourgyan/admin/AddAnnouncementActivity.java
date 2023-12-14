package com.example.trackyourgyan.admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import com.example.trackyourgyan.R;
import com.example.trackyourgyan.objects.Announcement;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class AddAnnouncementActivity extends AppCompatActivity {

    private FirebaseFirestore db;
    private TextInputEditText title, message;
    private MaterialButton announceBtn;
    private AutoCompleteTextView forYearSpinner;
    private ArrayAdapter<String> forYearAdapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_announcement);
        db = FirebaseFirestore.getInstance();
        title = findViewById(R.id.announcement_title);
        message = findViewById(R.id.announcement_message);
        forYearSpinner = findViewById(R.id.for_year);
        announceBtn = findViewById(R.id.announcement_btn);

        ArrayList<String> yearList = new ArrayList<>();
        yearList.add("First Year");
        yearList.add("Second Year");
        yearList.add("Third Year");

        forYearAdapter = new ArrayAdapter<String>(AddAnnouncementActivity.this, R.layout.dropdown_item, yearList);
        forYearSpinner.setAdapter(forYearAdapter);
        announceBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String titleTxt = title.getText().toString();
                String messageTxt = message.getText().toString();
                String yearTxt = forYearSpinner.getText().toString();
                Long timestamp = System.currentTimeMillis() / 1000L;
                Announcement announcement = new Announcement(messageTxt,titleTxt,yearTxt);
                db.collection("announcements").document(timestamp.toString()).set(announcement)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(getApplicationContext(), "Annoucement Posted Successfully", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {

                            }
                        });

            }
        });
    }
}