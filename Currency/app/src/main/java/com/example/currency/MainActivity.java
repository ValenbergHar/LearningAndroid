package com.example.currency;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private Document doc;
    private List<Data> dataList;

    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init() {
        dataList = new ArrayList<>();
        recyclerView = findViewById(R.id.recycle);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(MainActivity.this);
        recyclerView.setLayoutManager(layoutManager);

        mAdapter = new RecyclerViewAdapter(dataList, this);
        recyclerView.setAdapter(mAdapter);
//        Data data = new Data();
//        data.setData1("zdfbgfds");
//        data.setData2("zdfbgfds");
//        data.setData3("zdfbgfds");
//        data.setData4("zdfbgfds");
//        dataList.add(data);

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
            Log.i("my", "dollarElements: " + ourTable.children().get(0).text());

            for (int i = 0; i < 3; i++) {
                Data data = new Data();
                data.setData1(ourTable.children().get(i).child(0).text());
                data.setData2(ourTable.children().get(i).child(1).text());
                data.setData3(ourTable.children().get(i).child(2).text().substring(0,7));
                data.setData4(ourTable.children().get(i).child(3).text());
                dataList.add(data);
            }
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mAdapter.notifyDataSetChanged();
                }
            });

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}