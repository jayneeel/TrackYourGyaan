package com.example.trackyourgyan.utils;

import android.content.Context;
import android.util.Log;

import com.example.trackyourgyan.objects.Question;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Helpers {

    public String[] ADMIN_SUBJECTS = {"Physics","Advance Java", "EVS", "Management", "ETI"};
    public String[] OPTIONS = {"A","B","C","D"};
    public String[] FIRST_YEAR_SUBJECTS = {"Physics"};
    public String[] THIRD_YEAR_SUBJECTS = {"Advance Java", "EVS", "Management", "ETI"};
    public String[] PHYSICS_CHAPTERS = {"Units and Measurements", "Electricity,magnetism and semiconductors", "Heat and Optics"};
    public String[] ADVANCE_JAVA_CHAPTERS = {"Abstract windowing Toolkit (AWT)", "Swing", "Event Handling", "Networking Basics", "Database", "Servlets"};
    public String[] ETI_CHAPTERS = {"Artificial intelligence", " Internet of Things", "Basics of digital forensics", "Digital Evidence", "Basics of Hacking", "Types of hacking"};
    public String[] MANAGEMENT_CHAPTERS = {"Introduction to Management", " Planning and Organizing", "Directing and Controlling ", "Safety Management", "Legislative Acts"};
    public String[] EVS_CHAPTERS = {"Environment", "Energy Resources", "Ecosystem and Biodiversity", "Environmental Pollution", "Social issues and Environmental education"};

    Map<String, List<String>> subjectChaptersMap = new HashMap<>();
    Map<String, String> subjectDbMap = new HashMap<>();

    public Map<String, String> getSubjectDbMap() {
        subjectDbMap.put("Physics", "physics");
        subjectDbMap.put("Advance Java", "ajp");
        subjectDbMap.put("EVS", "evs");
        subjectDbMap.put("Management", "management");
        subjectDbMap.put("ETI", "eti");
        return subjectDbMap;
    }

    public Map<String, List<String>> getSubjectChaptersMap() {
        subjectChaptersMap.put("Physics", Arrays.asList(PHYSICS_CHAPTERS));
        subjectChaptersMap.put("Advance Java", Arrays.asList(ADVANCE_JAVA_CHAPTERS));
        subjectChaptersMap.put("EVS", Arrays.asList(EVS_CHAPTERS));
        subjectChaptersMap.put("Management", Arrays.asList(MANAGEMENT_CHAPTERS));
        subjectChaptersMap.put("ETI", Arrays.asList(ETI_CHAPTERS));
        return subjectChaptersMap;
    }

    Gson gson = new Gson();

    public List<Question> loadQuestionsFromAsset(Context context, String fileName, Gson gson) {
        try {
            // Open the JSON file from the assets directory
            InputStream inputStream = context.getAssets().open(fileName);

            // Read the contents of the file into a string
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();
            String jsonString = new String(buffer, StandardCharsets.UTF_8);

            // Deserialize JSON into a list of Question objects
            return gson.fromJson(jsonString, new TypeToken<List<Question>>(){}.getType());
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public String getTimestamp(){
        Long tsLong = System.currentTimeMillis()/1000;
        return tsLong.toString();
    }

    public String convertToArrayList(String str){
        Log.d("*********** STR **********", str);
        String[] elements = str.substring(1,str.length() - 1).split(", ");
        Log.d("*********** STR **********", elements.toString());
        Set<String> mySet = new HashSet<String>();
        Collections.addAll(mySet, elements);
        return String.join("\n", mySet);
    }

    public HashMap<String, List> convertMap(HashMap<Integer, List> hashMap){
        HashMap<String, List> stringKeyMap = new HashMap<>();

        // Convert keys from integer to string
        for (Integer key : hashMap.keySet()) {
            String stringKey = key.toString();
            List value = hashMap.get(key);
            stringKeyMap.put(stringKey, value);
        }
        return stringKeyMap;
    }

    public Map<String, List<String>> convertToQuestionList(List<Question> questionsList){
        Map<String, List<String>> questionAnswerMap = new HashMap<>();
        for (Question question: questionsList){
            List<String> questionAnswerList = new ArrayList<>();
            questionAnswerList.add(question.question);
            questionAnswerList.add(question.correct);
            questionAnswerMap.put(String.valueOf(questionsList.indexOf(question)), questionAnswerList);
        }
        return questionAnswerMap;
    }
}
