package com.example.shopapp;

import android.content.Context;
import android.content.SharedPreferences;

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
                "http://journalby.com/files/styles/1000x1000/public/field/image/d0d40a3e2c4ddaba8149082be5de.jpeg",
                "drink", 2.3, 8);
        allItems.add(milk);
        GroceryItem milk1 = new GroceryItem("Milk", "смачненькае малачко",
                "http://journalby.com/files/styles/1000x1000/public/field/image/d0d40a3e2c4ddaba8149082be5de.jpeg",
                "drink", 2.3, 8);
        allItems.add(milk1);
        GroceryItem milk2 = new GroceryItem("Milk", "смачненькае малачко",
                "http://journalby.com/files/styles/1000x1000/public/field/image/d0d40a3e2c4ddaba8149082be5de.jpeg",
                "drink", 2.3, 8);
        allItems.add(milk2);
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

    public static int getID() {
        ID++;
        return ID;
    }
}
