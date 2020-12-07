package com.example.mylibrary;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;

public class AllBooksActivity extends AppCompatActivity {
    private RecyclerView bookRecView;
    private BookRecViewAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_books);

        adapter= new BookRecViewAdapter(this);
        bookRecView = findViewById(R.id.booksRecView);
        bookRecView.setAdapter(adapter);
        bookRecView.setLayoutManager(new GridLayoutManager(this, 2));

        ArrayList<Book> books = new ArrayList<>();
        books.add(new Book(1, "IQ48",  "Haruki Murakami", 1350, "https://s1.livelib.ru/boocover/1000712784/o/be77/Haruki_Murakami__1Q84._Tysyacha_Nevestsot_Vosemdesyat_Chetyre._Kniga_3._Oktyabrd.jpeg",
        "fdbsdfbh", "gsfngfn"));
        adapter.setBooks(books);

    }
}