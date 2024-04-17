package com.example.sweproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.sweproject.GlobalChatRoom.GlobalChatHome;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class HomePage extends AppCompatActivity {


    FloatingActionButton dummy;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_page);
        dummy = findViewById(R.id.floatingActionButton);
        dummy.setOnClickListener(v -> {
            Intent intent = new Intent(HomePage.this , GlobalChatHome.class);
            startActivity(intent);
            finish();
        });
    }


}

















