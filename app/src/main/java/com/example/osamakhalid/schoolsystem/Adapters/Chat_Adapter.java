package com.example.osamakhalid.schoolsystem.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.osamakhalid.schoolsystem.GlobalCalls.CommonCalls;
import com.example.osamakhalid.schoolsystem.Model.ChatDataResponse;
import com.example.osamakhalid.schoolsystem.Model.Chat_Model;
import com.example.osamakhalid.schoolsystem.Model.ItemClickListener;
import com.example.osamakhalid.schoolsystem.Model.Teacher_Model;
import com.example.osamakhalid.schoolsystem.R;

import java.util.List;

/**
 * Created by Osama Khalid on 2/4/2018.
 */

public class Chat_Adapter extends RecyclerView.Adapter {
    Context context;
    List<ChatDataResponse> chatModels;

    public Chat_Adapter(List<ChatDataResponse> chatModels, Context context) {
        this.context = context;
        this.chatModels = chatModels;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == 1) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_view_layout, parent, false);
            return new Chat_Adapter.ViewHolderOne(v);
        } else {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_view_layout_2, parent, false);
            return new Chat_Adapter.ViewHolderTwo(v);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ChatDataResponse chatDataResponse = chatModels.get(position);
        if (chatDataResponse.getEmail().equals(CommonCalls.getUserData(context).getEmail())) {
            ((ViewHolderOne) holder).date.setText(chatDataResponse.getDateTime());
            ((ViewHolderOne) holder).message.setText(chatDataResponse.getMessageContent());
            Glide.with(context).load(chatDataResponse.getImage()).into(((ViewHolderOne) holder).image);
        } else {
            ((ViewHolderTwo) holder).date.setText(chatDataResponse.getDateTime());
            ((ViewHolderTwo) holder).message.setText(chatDataResponse.getMessageContent());
            Glide.with(context).load(chatDataResponse.getImage()).into(((ViewHolderTwo) holder).image);
        }
    }
//
//    @Override
//    public void onBindViewHolder(Chat_Adapter.ViewHolder holder, int position) {
//        final ChatDataResponse chatModel = chatModels.get(position);
//        holder.message.setText(chatModel.getMessageContent());
//        holder.date.setText(chatModel.getDateTime());
//    }

    @Override
    public int getItemViewType(int position) {
        if (chatModels.get(position).getEmail().equals(CommonCalls.getUserData(context).getEmail())) {
            return 1;
        } else {
            return 2;
        }
    }

    @Override
    public int getItemCount() {
        return chatModels.size();
    }

    public class ViewHolderOne extends RecyclerView.ViewHolder {

        TextView message;
        TextView date;
        RelativeLayout chatLayout;
        ImageView image;

        public ViewHolderOne(View itemView) {
            super(itemView);
            message = itemView.findViewById(R.id.chat_msg_1);
            date = itemView.findViewById(R.id.chat_date_1);
            chatLayout = itemView.findViewById(R.id.chat_layout_1);
            image = itemView.findViewById(R.id.chat_image_1);
        }
    }

    public class ViewHolderTwo extends RecyclerView.ViewHolder {

        TextView message;
        TextView date;
        RelativeLayout chatLayout;
        ImageView image;

        public ViewHolderTwo(View itemView) {
            super(itemView);
            message = itemView.findViewById(R.id.chat_msg_2);
            date = itemView.findViewById(R.id.chat_date_2);
            chatLayout = itemView.findViewById(R.id.chat_layout_2);
            image = itemView.findViewById(R.id.chat_image_2);
        }
    }
}
