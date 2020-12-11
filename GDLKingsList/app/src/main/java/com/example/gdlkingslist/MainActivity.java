package com.example.gdlkingslist;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.lang.reflect.Member;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static com.example.gdlkingslist.King.kingDateAscComparator;
import static com.example.gdlkingslist.King.kingDateDescComparator;
import static com.example.gdlkingslist.King.kingNameAZComparator;
import static com.example.gdlkingslist.King.kingNameZAComparator;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "Kings App";
    private Button btn_addOne;
    private Menu menu;
    MyApplication myApplication = (MyApplication) this.getApplication();
    private List<King> kingsList;


    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        kingsList = myApplication.getKingsList();

        btn_addOne = findViewById(R.id.btn_addOne);

        recyclerView = findViewById(R.id.lv_kingList);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(MainActivity.this);
        recyclerView.setLayoutManager(layoutManager);

        mAdapter = new RecyclerViewAdapter(kingsList, this);
        recyclerView.setAdapter(mAdapter);


        btn_addOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddEditOne.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.sort_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_a_to_z:
                Collections.sort(kingsList, kingNameAZComparator );
                mAdapter.notifyDataSetChanged();
                return true;

            case R.id.menu_z_to_a:
                Collections.sort(kingsList, kingNameZAComparator );
                mAdapter.notifyDataSetChanged();
                return true;
            case R.id.menu_date_ascending:
                Collections.sort(kingsList, kingDateAscComparator );
                mAdapter.notifyDataSetChanged();
                return true;
            case R.id.menu_data_desc:
                Collections.sort(kingsList, kingDateDescComparator );
                mAdapter.notifyDataSetChanged();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}