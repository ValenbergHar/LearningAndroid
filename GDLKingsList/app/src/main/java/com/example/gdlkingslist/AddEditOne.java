package com.example.gdlkingslist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.List;

public class AddEditOne extends AppCompatActivity {

    public static final String ID = "id";

    private Button btn_ok, btn_cancel;
    List<King> kingsList;
    MyApplication myApplication = (MyApplication) this.getApplication();
    private EditText et_king_date, et_king_name, et_king_picture_url;
    TextView tv_kingId;
    int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_one);
        btn_cancel = findViewById(R.id.btn_cancel);
        btn_ok = findViewById(R.id.btn_ok);
        et_king_date = findViewById(R.id.et_date_of_rule);
        et_king_name = findViewById(R.id.et_king_name);
        et_king_picture_url = findViewById(R.id.et_king_photo_url);
        tv_kingId = findViewById(R.id.txtKingId);
        kingsList = myApplication.getKingsList();

        Intent intent = getIntent();
        id = intent.getIntExtra(ID, -1);
        King king = null;
        if (id >= 0) {
            for (King k : kingsList) {
                if (k.getId() == id) {
                    king = k;
                }
            }
            et_king_name.setText(king.getName());
            et_king_date.setText(String.valueOf(king.getDateOfElection()));
            et_king_picture_url.setText(king.getImageUrl());
            tv_kingId.setText(String.valueOf(id));
        } else {

        }



        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (id >= 0) {
                    //update
                    King updateKing = new King(id, et_king_name.getText().toString(), Integer.parseInt(et_king_date.getText().toString()), et_king_picture_url.getText().toString());
                    kingsList.set(id, updateKing);
                } else {
                    //add new
                    int nextId = myApplication.getNextId();
                    King king = new King(nextId, et_king_name.getText().toString(), Integer.parseInt(et_king_date.getText().toString()), et_king_picture_url.getText().toString());
                    kingsList.add(king);
                    myApplication.setNextId(nextId++);
                }


                Intent intent = new Intent(AddEditOne.this, MainActivity.class);
                startActivity(intent);

            }
        });

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddEditOne.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}