package com.example.trackyourgyan.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.trackyourgyan.objects.Announcement;
import com.example.trackyourgyan.R;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class AnnouncementAdapter extends RecyclerView.Adapter<AnnouncementAdapter.MyViewHolder> {
    ArrayList<Announcement> announcementArrayList;
    FirebaseFirestore db;

    public AnnouncementAdapter(ArrayList<Announcement> announcementArrayList) {
        this.announcementArrayList = announcementArrayList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.announcement_card,parent,false);
        db = FirebaseFirestore.getInstance();
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.title.setText(announcementArrayList.get(position).getTitle());
        holder.message.setText(announcementArrayList.get(position).getMessage());
    }

    @Override
    public int getItemCount() {
        return announcementArrayList.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView title, message, datetimestamp;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.announcementTitle);
            message = itemView.findViewById(R.id.announcementMessage);
            datetimestamp = itemView.findViewById(R.id.timestamp);

        }
    }
}
