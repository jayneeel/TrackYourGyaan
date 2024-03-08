package com.example.trackyourgyan;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.example.trackyourgyan.adapters.DetailedResultAdapter;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

public class PastDetailResultActivity extends AppCompatActivity {
    ExpandableListView expandableListView;
    String documentReference;
    FirebaseFirestore db;
    FirebaseAuth auth;
    TextView resultTxt;
    List<List<String>> resultList;
    List<List<String>> questionList;

    public static final String[] QUESTIONS = {
            "Which package contains the AWT classes?",
            "What is JDBC?",
            "At the root of Java event hierarchy, which class is located?",
            "What is the role of the ResultSet interface in JDBC?",
            "When slider of scrollbar was dragged, which event is generated?",
            "Which class represents a pop-up menu in AWT?",
            "Which interface is responsible for establishing a connection to a database in JDBC?",
            "What is the maximum size of cookie?",
            "Which class is used to create a simple window?",
            "How constructor can be used for a servlet?",
            "What is Swing in Java?",
            "When the InputEvent is not generated?",
            "Which of the following methods is used to execute an SQL query in JDBC?",
            "FocusEvent is subclass of ...",
            "Which tag should be used to pass information from JSP to included JSP?"
    };

    public static final String[] ANSWERS = {
            "java.awt",
            "Java Database Connectivity",
            "EVENTOBJECT",
            "To represent the result set of a query",
            "SCROLL_BAR MOVED",
            "PopUpMenu",
            "DriverManager",
            "4kb",
            "Frame",
            "Initialization and Constructor function",
            "A graphical user interface (GUI) toolkit",
            "Key is entered through keyboard",
            "All of the above",
            "ContainerEvent",
            "Using <%jsp:page> tag"
    };

    public static final String[] RESULT ={
            "CORRECT",
            "CORRECT",
            "CORRECT",
            "CORRECT",
            "INCORRECT",
            "CORRECT",
            "CORRECT",
            "CORRECT",
            "InCORRECT",
            "CORRECT",
            "CORRECT",
            "INCORRECT",
            "CORRECT",
            "CORRECT",
            "CORRECT",
    };

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_past_detail_result);
        documentReference = getIntent().getStringExtra("documentReferenceId");
        db = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
        resultTxt = findViewById(R.id.data);
        StringBuilder data = new StringBuilder();
        for (int i =0;i<15;i++){
            data.append(QUESTIONS[i]+"\nANSWER: "+ANSWERS[i]+"\nRESULT : "+RESULT[i]+"\n\n");
        }

        resultTxt.setText(data);

    }
}