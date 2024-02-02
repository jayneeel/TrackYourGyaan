package com.example.trackyourgyan.adapters;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.trackyourgyan.R;
import com.example.trackyourgyan.objects.Quiz;

import java.util.HashMap;
import java.util.List;

public class DetailedResultAdapter extends BaseExpandableListAdapter {

    private Context context;
    private Quiz quiz;
    public HashMap<Integer, List<String>> resultMap;
    private String questionText, correctAnswer, selectedAnswer, chapter, status;

    public DetailedResultAdapter(Context context, Quiz quiz, HashMap<Integer, List<String>> resultMap) {
        this.context = context;
        this.quiz = quiz;
        this.resultMap = resultMap;
    }

    @Override
    public int getGroupCount() {
        return this.resultMap.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return 1;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return null;
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return resultMap.get(groupPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater = (LayoutInflater) this.context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = layoutInflater.inflate(R.layout.list_group, null);
        TextView titleTxtView = (TextView) convertView.findViewById(R.id.listTitle);
        titleTxtView.setText(String.format("Question %d", (groupPosition+1)));
        ImageView resultIcon = (ImageView) convertView.findViewById(R.id.status_icon);
        if (resultMap.get(groupPosition+1).get(0).equals("CORRECT")){
            resultIcon.setImageResource(R.drawable.icon_right);
        }else {
            resultIcon.setImageResource(R.drawable.icon_wrong);
        }
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = layoutInflater.inflate(R.layout.list_item, null);

        questionText = quiz.quizQuestions.get(groupPosition).question;
        correctAnswer = resultMap.get(groupPosition+1).get(1);
        status = resultMap.get(groupPosition+1).get(0);
        chapter = resultMap.get(groupPosition+1).get(2);
        TextView listQuestionTxt = (TextView) convertView.findViewById(R.id.list_question_txt);
        TextView listAnswerTxt = (TextView) convertView.findViewById(R.id.correct_ans);
        TextView listChapterTxt = (TextView) convertView.findViewById(R.id.chapter_txt);
        listQuestionTxt.setText(questionText);
        listAnswerTxt.setText(String.format("Correct Answer : %s", correctAnswer));
        listChapterTxt.setText(chapter);
        if (status.equals("CORRECT")){
            convertView.setBackgroundColor(Color.parseColor("#87FFC5"));
        }else{
            convertView.setBackgroundColor(Color.parseColor("#FF4C4C"));
        }
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
