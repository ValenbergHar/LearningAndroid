package com.example.shopapp;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class Utils {
    private static int ID = 0;
    private static final String ALL_ITEMS_KEY = "all_items";
    private static final String DB_NAME = "fake_database";
    private static Gson gson = new Gson();
    private static final Type groceryListType = new TypeToken<List<GroceryItem>>() {
    }.getType();

    public static void initSharedPreferences(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(DB_NAME, Context.MODE_PRIVATE);
        List<GroceryItem> allItems = gson.fromJson(sharedPreferences.getString(ALL_ITEMS_KEY, null), groceryListType);
        if (null == allItems) {
            initAllItem(context);
        }
    }

    private static void initAllItem(Context context) {
        List<GroceryItem> allItems = new ArrayList<>();
        GroceryItem milk = new GroceryItem("Milk", "смачненькае малачко",
                "https://cdn.loveandlemons.com/wp-content/uploads/2020/01/oat-milk.jpg",
                "drink", 2.1, 10);
        milk.setUserPoint(7);
        allItems.add(milk);
        GroceryItem milk1 = new GroceryItem("Milkss", "смачненькае малачко",
                "https://cdn.loveandlemons.com/wp-content/uploads/2020/01/oat-milk.jpg",
                "drink", 2.2, 15);
        milk1.setPopularityPoint(15);
        allItems.add(milk1);
        GroceryItem milk2 = new GroceryItem("Mmmmmmilk", "смачненькае малачко",
                "https://cdn.loveandlemons.com/wp-content/uploads/2020/01/oat-milk.jpg",
                "drink", 2.3, 8);
        milk2.setPopularityPoint(10);
        milk2.setUserPoint(12);
        allItems.add(milk2);
        GroceryItem milk3 = new GroceryItem("Mmmmmmilk", "смачненькае малачко",
                "https://cdn.loveandlemons.com/wp-content/uploads/2020/01/oat-milk.jpg",
                "drink", 2.4, 7);
        allItems.add(milk3);
        SharedPreferences sharedPreferences = context.getSharedPreferences(DB_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(ALL_ITEMS_KEY, gson.toJson(allItems));
        editor.commit();
    }

    public static List<GroceryItem> getAllItems(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(DB_NAME, Context.MODE_PRIVATE);
        List<GroceryItem> allItems = gson.fromJson(sharedPreferences.getString(ALL_ITEMS_KEY, null), groceryListType);
        return allItems;
    }

    public static void clearSharedPreferences(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(DB_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
    }

    public static int getID() {
        ID++;
        return ID;
    }
}
