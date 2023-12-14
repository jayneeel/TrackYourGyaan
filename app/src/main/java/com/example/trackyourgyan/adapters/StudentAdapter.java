package com.example.trackyourgyan.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.trackyourgyan.R;
import com.example.trackyourgyan.objects.Announcement;
import com.example.trackyourgyan.objects.Student;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.MyViewHolder> {

    ArrayList<Student> studentArrayList;
    FirebaseFirestore db;

    public StudentAdapter(ArrayList<Student> studentArrayList) {
        this.studentArrayList = studentArrayList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.student_card,parent,false);
        db = FirebaseFirestore.getInstance();
        return new StudentAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Student currentStudent = studentArrayList.get(position);
        holder.studentName.setText(currentStudent.getFirstName());
        holder.studentYearDept.setText(currentStudent.getYear()+" | "+currentStudent.getDept());
    }

    @Override
    public int getItemCount() {
        return studentArrayList.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView studentName, studentYearDept;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            studentName = itemView.findViewById(R.id.student_name);
            studentYearDept = itemView.findViewById(R.id.year_dept);

        }
    }
}
