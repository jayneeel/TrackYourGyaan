package com.example.trackyourgyan.utils;

import android.content.Context;

import com.example.trackyourgyan.objects.Question;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class Helpers {

    public String[] FIRST_YEAR_SUBJECTS = {"Physics"};
    public String[] THIRD_YEAR_SUBJECTS = {"Advance Java", "EVS", "Management", "ETI"};

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


}
