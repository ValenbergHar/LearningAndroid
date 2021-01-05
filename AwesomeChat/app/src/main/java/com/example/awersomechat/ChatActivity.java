package com.example.awersomechat;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.List;


import static com.example.awersomechat.UserListActivity.RECIPIENT_USER_ID;


public class ChatActivity extends AppCompatActivity {

    public String recipientUserId;

    public static final int RC_IMAGE_PICKER = 124;
    public static final String USER_NAME_CHAT_USER_LIST = "userNameChatUserList";
    private FirebaseAuth auth;
    private FirebaseDatabase database;
    private DatabaseReference messagesDataBaseReference;
    private ChildEventListener messagesChildEventListener;

    private DatabaseReference usersDataBaseReference;
    private ChildEventListener usersChildEventListener;

    private FirebaseStorage storage;
    private StorageReference chatImagesStorageReference;

    //RecycleView
    private RecyclerView recyclerView;
    private AwesomeRecycleViewAdapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

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

        auth = FirebaseAuth.getInstance();

        Intent intent = getIntent();
        if (intent != null) {
            userName = intent.getStringExtra(USER_NAME_CHAT_USER_LIST);
            recipientUserId = intent.getStringExtra(RECIPIENT_USER_ID);
        } else {
            userName = "DefaultUser";
        }

        database = FirebaseDatabase.getInstance();
        messagesDataBaseReference = database.getReference().child("messages");
        usersDataBaseReference = database.getReference().child("users");

        storage = FirebaseStorage.getInstance();
        chatImagesStorageReference = storage.getReference().child("chat_images");


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

        //RecycleView
        recyclerView = findViewById(R.id.messageRecycleView);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(ChatActivity.this);
        recyclerView.setLayoutManager(layoutManager);

        mAdapter = new AwesomeRecycleViewAdapter(awesomeMessages, ChatActivity.this);
        recyclerView.setAdapter(mAdapter);

        sendMessageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("auth", String.valueOf(auth == null));
                    AwesomeMessage message = new AwesomeMessage();
                    message.setText(messageEditText.getText().toString());
                    message.setName(userName);
                    message.setSender(auth.getCurrentUser().getUid());
                    message.setRecipient(recipientUserId);
                    message.setImageUrl(null);

                    messagesDataBaseReference.push().setValue(message);
                    messageEditText.setText("");


            }
        });
        sendImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                intent.putExtra(intent.EXTRA_LOCAL_ONLY, true);
                startActivityForResult(Intent.createChooser(intent, "Choose an image"), RC_IMAGE_PICKER);
            }
        });


        usersChildEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                User user = snapshot.getValue(User.class);
                if (user.getId().equals(FirebaseAuth.getInstance().getCurrentUser().getUid())) {
                    userName = user.getName();
                }
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
        usersDataBaseReference.addChildEventListener(usersChildEventListener);

        messagesChildEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                AwesomeMessage message = snapshot.getValue(AwesomeMessage.class);

                Log.d("mmmm","message.getSender() - " +message.getSender());
                Log.d("mmmm", "auth.getCurrentUser().getUid() - " + auth.getCurrentUser().getUid());
                Log.d("mmmm", "message.getRecipient() - " + message.getRecipient() );
                Log.d("mmmm", "recipientUserId - " + recipientUserId );


                if (message.getSender().equals(auth.getCurrentUser().getUid()) && message.getRecipient().equals(recipientUserId) ||
                     message.getRecipient().equals(auth.getCurrentUser().getUid()) && message.getSender().equals(recipientUserId))
                {
                    awesomeMessages.add(message);
                    mAdapter.notifyDataSetChanged();
                }


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


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_IMAGE_PICKER && resultCode == RESULT_OK) {
            Uri selectImageUri = data.getData();
            final StorageReference imageReference = chatImagesStorageReference.child(selectImageUri.getLastPathSegment());
            UploadTask uploadTask = imageReference.putFile(selectImageUri);

            uploadTask = imageReference.putFile(selectImageUri);


            Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                @Override
                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                    if (!task.isSuccessful()) {
                        throw task.getException();
                    }

                    // Continue with the task to get the download URL
                    return imageReference.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if (task.isSuccessful()) {
                        Uri downloadUri = task.getResult();
                        AwesomeMessage message = new AwesomeMessage();
                        message.setImageUrl(downloadUri.toString());
                        message.setName(userName);
                        messagesDataBaseReference.push().setValue(message);
                    } else {
                        // Handle failures
                        // ...
                    }
                }
            });
        }
    }
}