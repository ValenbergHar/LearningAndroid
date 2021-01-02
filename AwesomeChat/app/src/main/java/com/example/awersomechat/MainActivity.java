package com.example.awersomechat;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private FirebaseDatabase database;
    private DatabaseReference messagesDataBaseReference;
    private ChildEventListener messagesChildEventListener;

    //RecycleView
    private RecyclerView recyclerView;
    private AwesomeRecycleViewAdapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;


    //ListView
//    private ListView messageListView;
//    private AwesomeMessageAdapter adapter;


    private ProgressBar progressBar;
    private ImageButton sendImageButton;
    private Button sendMessageButton;
    private EditText messageEditText;
    private String userName;
    private List<AwesomeMessage> awesomeMessages = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_rec);

        database = FirebaseDatabase.getInstance();
        messagesDataBaseReference = database.getReference().child("messages");


        progressBar = findViewById(R.id.progressBar);
        sendImageButton = findViewById(R.id.sendPhotoButton);

        sendMessageButton = findViewById(R.id.sendMessageButton);
        messageEditText = findViewById(R.id.messageEditText);
        progressBar.setVisibility(ProgressBar.INVISIBLE);

        messageEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().trim().length() > 0) {
                    sendMessageButton.setEnabled(true);
                } else {
                    sendMessageButton.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        messageEditText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(500) {
        }});

        userName = "Default User";

        //ListView
//        messageListView = findViewById(R.id.messageListView);
//        messageListView.setAdapter(adapter);
//        adapter = new AwesomeMessageAdapter(this, R.layout.message_item, awesomeMessages);


        //RecycleView
        recyclerView = findViewById(R.id.messageRecycleView);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(MainActivity.this);
        recyclerView.setLayoutManager(layoutManager);

        mAdapter = new AwesomeRecycleViewAdapter(awesomeMessages, MainActivity.this);
        recyclerView.setAdapter(mAdapter);

        sendMessageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AwesomeMessage message = new AwesomeMessage();
                message.setText(messageEditText.getText().toString());
                message.setName(userName);
                message.setImageUrl(null);

                messagesDataBaseReference.push().setValue(message);
                messageEditText.setText("");

            }
        });
        sendImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        messagesChildEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                AwesomeMessage message = snapshot.getValue(AwesomeMessage.class);
                //ListView
//                adapter.add(message);
                awesomeMessages.add(message);
                mAdapter.notifyDataSetChanged();
                Log.d("my" , String.valueOf(message));

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };
        messagesDataBaseReference.addChildEventListener(messagesChildEventListener);
    }
}