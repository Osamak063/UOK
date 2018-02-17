package com.example.osamakhalid.schoolsystem.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.osamakhalid.schoolsystem.Model.Chat_Model;
import com.example.osamakhalid.schoolsystem.Model.ItemClickListener;
import com.example.osamakhalid.schoolsystem.Model.Teacher_Model;
import com.example.osamakhalid.schoolsystem.R;

import java.util.List;

/**
 * Created by Osama Khalid on 2/4/2018.
 */

public class Chat_Adapter extends RecyclerView.Adapter<Chat_Adapter.ViewHolder> {
    Context context;
    List<Chat_Model> chatModels;

    public Chat_Adapter(List<Chat_Model> chatModels, Context context) {
        this.context = context;
        this.chatModels = chatModels;
    }

    @Override
    public Chat_Adapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_view_layout, parent, false);
        return new Chat_Adapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(Chat_Adapter.ViewHolder holder, int position) {
        final Chat_Model chatModel = chatModels.get(position);
        holder.message.setText(chatModel.getMessage());
        holder.date.setText(chatModel.getDate());
    }

    @Override
    public int getItemCount() {
        return chatModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView message;
        TextView date;

        public ViewHolder(View itemView) {
            super(itemView);
            message = itemView.findViewById(R.id.chat_msg);
            date = itemView.findViewById(R.id.chat_date);
        }
    }
}
