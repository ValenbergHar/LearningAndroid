package com.example.mylibrary;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class BookActivity extends AppCompatActivity {
    public static final String BOOK_ID_KEY  = "bookId";
    public static final String BOOK_NAME_KEY = "bookName";
    private TextView txtBookName, txtAuthor, txtPages, txtLongDescr;
    private Button btnAddToCurrentReading, btnWantToRead, btnAddToFavorities, btnAlreadyRead;
    private ImageView bookImage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);

        init();

        Book book = new Book(1, "IQ48", "Haruki Murakami", 1350, "https://s1.livelib.ru/boocover/1000712784/o/be77/Haruki_Murakami__1Q84._Tysyacha_Nevestsot_Vosemdesyat_Chetyre._Kniga_3._Oktyabrd.jpeg",
                "fdbsdfbh", "gsfngfn");
        Intent intent = getIntent();
        if(intent != null){
            int bookId = intent.getIntExtra(BOOK_ID_KEY, -1);
            if(bookId != -1){
                Book incomingBook = Utils.getInstance().getBookById(bookId);
                if(incomingBook!=null) setData(incomingBook);
            }

        }
        setData(book);
    }



    private void setData(Book book) {
        txtBookName.setText(book.getName());
        txtAuthor.setText(book.getAuthor());
        txtPages.setText(String.valueOf(book.getPages()));
        txtLongDescr.setText(book.getShortDesc());
        Glide.with(this).asBitmap().load(book.getImageUrl()).into(bookImage);
    }

    private void init() {
        bookImage = findViewById(R.id.imageBook);

        btnAddToCurrentReading = findViewById(R.id.btnAddToCurrentReading);
        btnWantToRead = findViewById(R.id.btnWantToRead);
        btnAddToFavorities = findViewById(R.id.btnAddToFavorities);
        btnAlreadyRead = findViewById(R.id.btnAlreadyRead);

        txtBookName = findViewById(R.id.txtBookName);
        txtAuthor = findViewById(R.id.txtAuthor);
        txtPages = findViewById(R.id.txtPages);
        txtLongDescr = findViewById(R.id.txtLongDescr);
    }
}