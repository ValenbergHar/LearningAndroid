package com.example.kingsgdl.kings;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.kingsgdl.R;
import com.example.kingsgdl.kings.tablelayout.MainActivityTable;
import com.example.kingsgdl.kings.tablelayout.Utils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivityKings extends AppCompatActivity {

    private List<King> kingsList;
    private Toolbar toolbarKings;

    private RecyclerView recyclerView;
    private RecyclerViewAdapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_kings);

        toolbarKings = findViewById(R.id.toolBarKings);
        setSupportActionBar(toolbarKings);

        kingsList = (List<King>) getIntent().getSerializableExtra("intent");

        recyclerView = findViewById(R.id.lv_kingList);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(MainActivityKings.this);
        recyclerView.setLayoutManager(layoutManager);

        mAdapter = new RecyclerViewAdapter(kingsList, MainActivityKings.this);
        recyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new RecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Intent intent = new Intent(MainActivityKings.this, MainActivityTable.class);
                intent.putExtra("intent",  kingsList.get(position));

//                intent.putExtra("id", kingsList.get(position).getId());
//                intent.putExtra("name", kingsList.get(position).getKingName());
//                intent.putExtra("date", String.valueOf(kingsList.get(position).getKingDateReign()));
//                intent.putExtra("image", kingsList.get(position).getKingPhotos().get(0));

                startActivity(intent);
                Toast.makeText(MainActivityKings.this, String.valueOf(position), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        MenuItem item = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) item.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                mAdapter.getFilter().filter(newText);
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }
}