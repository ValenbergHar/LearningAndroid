package com.example.currency;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    private Document doc;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();

    }

    private void init() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                getWeb();
            }
        }).start();

    }

    private void getWeb() {
        try {
            doc = Jsoup.connect("https://minfin.com.ua/currency/").get();
            Elements tables = doc.getElementsByTag("tbody");
            Element ourTable = tables.get(0);
            Elements elementsFromTable = ourTable.children();
            Element dollar = elementsFromTable.get(0);
            Elements dollarElements = dollar.children();


            Log.i("my", "Tbody size: " + tables.size());
            Log.i("my", "Tbody: " + tables.get(0).text());
            Log.i("my", "dollarElements: " + dollarElements.get(2).text());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}