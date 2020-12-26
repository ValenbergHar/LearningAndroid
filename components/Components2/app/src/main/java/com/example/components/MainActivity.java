package com.example.components;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.components.database.NoteEntity;
import com.example.components.ui.NotesAdapter;
import com.example.components.viewmodel.MainViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;


    @OnClick(R.id.fab)
    void fabClickHandler(){
        Intent intent=new Intent(this, EditorActivity.class);
        startActivity(intent);
    }

    private FloatingActionButton fab;
    private List<NoteEntity> notesData = new ArrayList<>();
    private NotesAdapter mAdapter;
    private MainViewModel maViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
        initViewModel();
        initRecycleView();


        notesData.addAll(maViewModel.mNotes);
        for (NoteEntity note : notesData) {
            Log.i("Notes", note.toString());
        }
    }

    private void initViewModel() {
        maViewModel=new ViewModelProvider(this).get(MainViewModel.class);

    }

    private void initRecycleView() {
        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mAdapter = new NotesAdapter(notesData, this);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add_sample_data:
                addSampleData();
                Toast.makeText(this, "Add data", Toast.LENGTH_SHORT).show();
        }

        return true;
    }

    private void addSampleData() {
        maViewModel.addSampleData();
    }
}