package com.example.recviewonclick;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private List<ExampleItem> exampleItems;

    private RecyclerView recyclerView;
    // clickRecycle 5  замена RecyclerView.Adapter на RecyclerViewAdapter
    private RecyclerViewAdapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;


    private Button insert;
    private Button delete;
    private EditText et_add;
    private EditText et_delete;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        createExampleList();
        buildRecycleView();



        insert = findViewById(R.id.btn_addd);
        delete = findViewById(R.id.btn_deletet);
        et_add = findViewById(R.id.et_add);
        et_delete = findViewById(R.id.et_del);


        insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = Integer.parseInt(et_add.getText().toString());
                insertItem(position);

            }
        });
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = Integer.parseInt(et_delete.getText().toString());
                deleteItem(position);
            }
        });
    }



    private void deleteItem(int position) {
        exampleItems.remove(position);
        mAdapter.notifyDataSetChanged();
    }

    private void insertItem(int position) {
        exampleItems.add(position, new ExampleItem(R.drawable.ic_android, "Дадатачак нумар " + position, "fgef"));
        mAdapter.notifyDataSetChanged();
    }

    public void changeItem(int position, String text) {
        exampleItems.get(position).changeText(text);
        mAdapter.notifyDataSetChanged();
    }

    private void buildRecycleView() {
        recyclerView = findViewById(R.id.rView);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(MainActivity.this);
        recyclerView.setLayoutManager(layoutManager);

        mAdapter = new RecyclerViewAdapter(exampleItems, MainActivity.this);
        recyclerView.setAdapter(mAdapter);


        // clickRecycle 6
        mAdapter.setOnItemClickListener(new RecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                changeItem(position, "Clicked!");
            }

            @Override
            public void onDelete(int position) {
                deleteItem(position);
                mAdapter.notifyDataSetChanged();
            }

        });
    }

    private void createExampleList() {
        exampleItems = new ArrayList<>();
        exampleItems.add(new ExampleItem(R.drawable.ic_android, "dfvsdfv", "fgef"));
        exampleItems.add(new ExampleItem(R.drawable.ic_baseline, "dfvsdfv", "fgef"));
        exampleItems.add(new ExampleItem(R.drawable.ic_irport_shuttle_24, "dfvsdfv", "fgef"));

//        ExampleItem exampleItem1 = new ExampleItem(R.drawable.ic_android, "dfvsdfv", "fgef");
//        ExampleItem exampleItem2 = new ExampleItem(R.drawable.ic_baseline, "dfvsdfv", "fgef");
//        ExampleItem exampleItem3 = new ExampleItem(R.drawable.ic_irport_shuttle_24, "dfvsdfv", "fgef");

//        exampleItems = Arrays.asList(exampleItem1, exampleItem2, exampleItem3, exampleItem1, exampleItem2, exampleItem3);
//        exampleItems.remove(3);
//        mAdapter.notifyDataSetChanged();
    }
}