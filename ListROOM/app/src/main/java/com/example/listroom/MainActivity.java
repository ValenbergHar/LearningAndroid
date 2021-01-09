package com.example.listroom;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private List<Person> personsList;
    AppDatabase db;
    private RecyclerView recyclerView;
    private RecyclerViewAdapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    private EditText firstName, secondName, email;


    private FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fab = findViewById(R.id.fab);
        //     personsList = new ArrayList<>();

        recyclerView = findViewById(R.id.recycleView);

        db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "production")
                .allowMainThreadQueries()
                .build();
        personsList = db.personDao().getAllPersons();

        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(MainActivity.this);
        recyclerView.setLayoutManager(layoutManager);

        mAdapter = new RecyclerViewAdapter(personsList, MainActivity.this);
        recyclerView.setAdapter(mAdapter);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openAlert();
            }
        });

    }

    public void openAlert() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        LinearLayout constraintLayout = (LinearLayout) getLayoutInflater().inflate(R.layout.create_user, null);
        alertDialog.setView(constraintLayout);
        alertDialog.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
//        alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//
//            }
//        });
        alertDialog.setPositiveButton("Add", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                AlertDialog alertDialog = (AlertDialog) dialog;
                firstName = alertDialog.findViewById(R.id.firstName);
                secondName = alertDialog.findViewById(R.id.secondName);
                email = alertDialog.findViewById(R.id.email);
                Person person = new Person(firstName.getText().toString(), secondName.getText().toString(), email.getText().toString());
                personsList.add(person);
                db.personDao().insertAll(person);
                mAdapter.notifyDataSetChanged();

            }
        });
        AlertDialog dialog = alertDialog.create();
        dialog.show();
    }

    ;
}