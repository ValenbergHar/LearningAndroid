package com.example.mydata;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Canvas;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

import static com.example.mydata.Util.KEY_ID;
import static com.example.mydata.Util.KEY_NAME;
import static com.example.mydata.Util.KEY_POPULATION;
import static com.example.mydata.Util.LOG_TAG;
import static com.example.mydata.Util.TABLE_NAME;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button btnAdd, btnDel;
    private EditText textId, textName, textPopulation;
    private List<City> cities;
    private List<City> deleteCity;

    private DBHelper dbHelper;

    private RecyclerView recyclerView;
    private RecyclerAdapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    long rowID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnAdd = findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(this);
        btnDel = findViewById(R.id.btnDelete);
        btnDel.setOnClickListener(this);
        textId = findViewById(R.id.editTextId);
        textName = findViewById(R.id.editTextName);
        textPopulation = findViewById(R.id.editTextPopulation);

        cities = new ArrayList<>();
        deleteCity = new ArrayList<>();

        cities.add(new City(1, "Hrodna", 350000));

        recyclerView = findViewById(R.id.recycleView);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(MainActivity.this);
        recyclerView.setLayoutManager(layoutManager);

        mAdapter = new RecyclerAdapter(cities, MainActivity.this);
        recyclerView.setAdapter(mAdapter);

        dbHelper = new DBHelper(this);

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallBack);
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }

    ItemTouchHelper.SimpleCallback simpleCallBack = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT ) {

        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            switch (direction) {
                case ItemTouchHelper.LEFT:
                    int position = viewHolder.getAdapterPosition();
                    final City city = cities.get(position);

                    SQLiteDatabase db = dbHelper.getWritableDatabase();
                    db.delete(Util.TABLE_NAME, KEY_NAME + "=?", null);
                    db.close();
                    Log.d(LOG_TAG, "--- Delete: " + city.getName());
                    deleteCity.add(city);
                    cities.remove(position);
                    mAdapter.notifyItemRemoved(position);
                    mAdapter.notifyDataSetChanged();

//                    Snackbar.make(recyclerView, city.getName() + ", deleted", Snackbar.LENGTH_SHORT).setAction("Undo", new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            deleteCity.remove(deleteCity.lastIndexOf(city));
//                            cities.add(position, city);
//                            mAdapter.notifyItemInserted(position);
//                            Log.d(LOG_TAG, "--- Delete: "+city.getName());
//                        }
//                    }).show();

                    break;
            }
        }

        @Override
        public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {

            new RecyclerViewSwipeDecorator.Builder(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                    .addSwipeLeftBackgroundColor(ContextCompat.getColor(MainActivity.this, R.color.design_default_color_primary))
                    .addSwipeLeftActionIcon(R.drawable.ic_delete)
                    .addSwipeRightBackgroundColor(ContextCompat.getColor(MainActivity.this, R.color.design_default_color_secondary))
                    .addSwipeRightActionIcon(R.drawable.ic_delete)
                    .create()
                    .decorate();
            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);

        }
    };


    @Override
    public void onClick(View v) {
        // создаем объект для данных
        ContentValues cv = new ContentValues();
        // получаем данные из полей ввода
        int id = Integer.valueOf(textId.getText().toString());
        int population = Integer.valueOf(textPopulation.getText().toString());
        String name = textName.getText().toString();
        // подключаемся к БД
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        switch (v.getId()) {
            case R.id.btnAdd:
                Toast.makeText(MainActivity.this, "gghfyc", Toast.LENGTH_SHORT).show();
                Log.d(LOG_TAG, "--- Insert in mytable: ---");
                cv.put(KEY_ID, id);
                cv.put(KEY_NAME, name);
                cv.put(KEY_POPULATION, population);

                rowID = db.insert(TABLE_NAME, null, cv);
                Log.d(LOG_TAG, "row inserted, ID = " + rowID);
                cities.add(new City(id, name, population));
                Log.d(LOG_TAG, "cities " + cities.toString());
                mAdapter.notifyDataSetChanged();
                break;


            default:
                break;
        }
        db.close();
    }
}