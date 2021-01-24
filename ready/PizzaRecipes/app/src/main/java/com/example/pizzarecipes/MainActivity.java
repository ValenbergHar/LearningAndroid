package com.example.pizzarecipes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ArrayList<PizzaRecipeItem> pizzaRecipeItems = new ArrayList<>();
        pizzaRecipeItems.add(new PizzaRecipeItem(R.drawable.pizza_1, Utils.TITLE, Utils.DESCRIPTION, Utils.RECEPE));
        pizzaRecipeItems.add(new PizzaRecipeItem(R.drawable.pizza_2, Utils.TITLE, Utils.DESCRIPTION, Utils.RECEPE));
        pizzaRecipeItems.add(new PizzaRecipeItem(R.drawable.pizza_3, Utils.TITLE, Utils.DESCRIPTION, Utils.RECEPE));
        pizzaRecipeItems.add(new PizzaRecipeItem(R.drawable.pizza_4, Utils.TITLE, Utils.DESCRIPTION, Utils.RECEPE));
        pizzaRecipeItems.add(new PizzaRecipeItem(R.drawable.pizza_5, Utils.TITLE, Utils.DESCRIPTION, Utils.RECEPE));
        pizzaRecipeItems.add(new PizzaRecipeItem(R.drawable.pizza_6, Utils.TITLE, Utils.DESCRIPTION, Utils.RECEPE));
        pizzaRecipeItems.add(new PizzaRecipeItem(R.drawable.pizza_7, Utils.TITLE, Utils.DESCRIPTION, Utils.RECEPE));
        pizzaRecipeItems.add(new PizzaRecipeItem(R.drawable.pizza_8, Utils.TITLE, Utils.DESCRIPTION, Utils.RECEPE));
        pizzaRecipeItems.add(new PizzaRecipeItem(R.drawable.pizza_9, Utils.TITLE, Utils.DESCRIPTION, Utils.RECEPE));
        pizzaRecipeItems.add(new PizzaRecipeItem(R.drawable.pizza_10, Utils.TITLE, Utils.DESCRIPTION, Utils.RECEPE));

        recyclerView = findViewById(R.id.recycleView);
        recyclerView.setHasFixedSize(true);
        adapter = new PizzaRecipeAdapter(pizzaRecipeItems, this);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(layoutManager);


    }
}
