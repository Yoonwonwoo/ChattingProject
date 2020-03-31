package com.example.chattingproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class ChatAddActivity extends AppCompatActivity {
    EditText add_room, add_id;
    Button add_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_add);

        add_room = findViewById(R.id.add_room);
        add_id = findViewById(R.id.add_id);

        add_btn = findViewById(R.id.add_btn);
        add_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(add_room.getText().toString().equals("")||add_id.getText().toString().equals(""))
                    return;

                Intent intent = new Intent(ChatAddActivity.this, ChatActivity.class);
                intent.putExtra("chatName", add_room.getText().toString());
                intent.putExtra("userName", add_id.getText().toString());
                startActivity(intent);
            }
        });


    }
}
