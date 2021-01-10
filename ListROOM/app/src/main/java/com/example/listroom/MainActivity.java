package com.example.listroom;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;
import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;

import android.content.DialogInterface;
import android.graphics.Canvas;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private List<Person> personsList;
    private List<Person> deletedPersonList;
    AppDatabase db;
    private RecyclerView recyclerView;
    private RecyclerViewAdapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private EditText firstName, secondName, email;
 //   private EditText firstNameC, secondNameC, emailC;


    private FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fab = findViewById(R.id.fab);
        deletedPersonList = new ArrayList<>();
//        firstNameC=findViewById(R.id.firstNameC);
//        secondNameC=findViewById(R.id.secondNameC);
//        emailC=findViewById(R.id.emailC);

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

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallBack);
        itemTouchHelper.attachToRecyclerView(recyclerView);

        mAdapter.setOnItemClickListener(new RecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
 //               changePerson(position);
                Toast.makeText(MainActivity.this, personsList.get(position).getName(), Toast.LENGTH_SHORT).show();
            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openAlert();
            }
        });

    }

    private void changePerson(int position) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        LinearLayout constraintLayout = (LinearLayout) getLayoutInflater().inflate(R.layout.change_user, null);
        alertDialog.setView(constraintLayout);
        Person person = personsList.get(position);
//        firstNameC.setText(person.getName());
//        secondNameC.setText(person.getName());
//        emailC.setText(person.getName());
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
        alertDialog.setPositiveButton("Change", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                AlertDialog alertDialog = (AlertDialog) dialog;
                Person person = new Person(firstName.getText().toString(), secondName.getText().toString(), email.getText().toString());
                personsList.add(person);
                db.personDao().insertAll(person);
                mAdapter.notifyDataSetChanged();

            }
        });
        AlertDialog dialog = alertDialog.create();
        dialog.show();
    }

    ItemTouchHelper.SimpleCallback simpleCallBack = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            int position = viewHolder.getAdapterPosition();
            switch (direction) {
                case ItemTouchHelper.LEFT:
                    Person person = personsList.get(position);
                    deletedPersonList.add(person);
                    personsList.remove(position);
                    db.personDao().delete(person);
                    mAdapter.notifyItemRemoved(position);

                    Snackbar.make(recyclerView, person.getName(), Snackbar.LENGTH_SHORT).setAction("Undo", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            personsList.add(position, person);
                            db.personDao().insertAll(person);
                            mAdapter.notifyDataSetChanged();
                        }
                    }).show();

                    break;
            }
        }

        @Override
        public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            new RecyclerViewSwipeDecorator.Builder(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                    .addSwipeLeftBackgroundColor(ContextCompat.getColor(MainActivity.this, R.color.design_default_color_primary))
                    .addSwipeLeftActionIcon(R.drawable.ic_delete)
                    .create()
                    .decorate();
        }
    };


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
}