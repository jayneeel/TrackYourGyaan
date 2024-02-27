package com.example.trackyourgyan.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.trackyourgyan.R;
import com.example.trackyourgyan.objects.QuizResult;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class ResultAdapter extends RecyclerView.Adapter<ResultAdapter.MyViewHolder> {
    ArrayList<QuizResult> quizResultArrayList;
    FirebaseFirestore db;

    public ResultAdapter(ArrayList<QuizResult> quizResultArrayList) {
        this.quizResultArrayList = quizResultArrayList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.result_card,parent,false);
        db = FirebaseFirestore.getInstance();
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
            holder.title.setText(quizResultArrayList.get(position).getSubject());
            holder.score.setText("Score : "+String.valueOf(quizResultArrayList.get(position).getScore()));
            holder.timestamp.setText(quizResultArrayList.get(position).getTimestamp());
    }

    @Override
    public int getItemCount() {
        return quizResultArrayList.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView title, score, timestamp;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            timestamp = itemView.findViewById(R.id.result_timestamp);
            title = itemView.findViewById(R.id.result_title);
            score = itemView.findViewById(R.id.result_score);
        }
    }
}
