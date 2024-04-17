package com.example.sweproject.GlobalChatRoom;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sweproject.R;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

import de.hdodenhof.circleimageview.CircleImageView;


public class ChatAdapter extends FirestoreRecyclerAdapter<ChatModel, ChatAdapter.ChatViewHolder> {


    public ChatAdapter(@NonNull FirestoreRecyclerOptions<ChatModel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull ChatViewHolder chatViewHolder, int i, @NonNull ChatModel chatModel) {
        chatViewHolder.message.setText(chatModel.getMessage());
        Glide.with(chatViewHolder.user_image.getContext().getApplicationContext())
                .load(chatModel.getUser_image_url())
                .into(chatViewHolder.user_image);
    }

    @NonNull
    @Override
    public ChatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_item,parent,false);
        return new ChatViewHolder(v);
    }

    static class ChatViewHolder extends RecyclerView.ViewHolder{

        TextView message;
        CircleImageView user_image;

        public ChatViewHolder(@NonNull View itemView) {
            super(itemView);
            message = itemView.findViewById(R.id.message);
            user_image = itemView.findViewById(R.id.user_image);
        }
    }
}
