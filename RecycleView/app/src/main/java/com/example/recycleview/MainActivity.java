package com.example.recycleview;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;

import static androidx.recyclerview.widget.LinearLayoutManager.*;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.contactRecView);
        ArrayList<Contact> contacts = new ArrayList<>();
        contacts.add(new Contact("dfbdf", "dbddfhf", "https://i.pinimg.com/originals/4c/c6/f1/4cc6f1f365fce288fcae170fa17b6875.jpg"));
        contacts.add(new Contact("dfbdf", "dbddfhf", "https://i.pinimg.com/originals/4c/c6/f1/4cc6f1f365fce288fcae170fa17b6875.jpg"));
        contacts.add(new Contact("dfbdf", "dbddfhf", "https://i.pinimg.com/originals/4c/c6/f1/4cc6f1f365fce288fcae170fa17b6875.jpg"));
        contacts.add(new Contact("dfbdf", "dbddfhf", "https://i.pinimg.com/originals/4c/c6/f1/4cc6f1f365fce288fcae170fa17b6875.jpg"));
        contacts.add(new Contact("dfbdf", "dbddfhf", "https://i.pinimg.com/originals/4c/c6/f1/4cc6f1f365fce288fcae170fa17b6875.jpg"));
        contacts.add(new Contact("dfbdf", "dbddfhf", "https://img.112.international/original/2020/09/23/291559.jpg"));
        contacts.add(new Contact("dfbdf", "dbddfhf", "https://www.nv-online.info/wp-content/webp-express/webp-images/doc-root/wp-content/uploads/2018/03/1.-Kanstantsin-Kalinouski.-Fotazdymak-Ahila-Banoldzi.jpg.webp"));
        contacts.add(new Contact("dfbdf", "dbddfhf", "https://www.nv-online.info/wp-content/webp-express/webp-images/doc-root/wp-content/uploads/2018/03/1.-Kanstantsin-Kalinouski.-Fotazdymak-Ahila-Banoldzi.jpg.webp"));
        contacts.add(new Contact("dfbdf", "dbddfhf", "https://img.112.international/original/2020/09/23/291559.jpg"));
        contacts.add(new Contact("dfbdf", "dbddfhf", "https://img.112.international/original/2020/09/23/291559.jpg"));
        contacts.add(new Contact("dfbdf", "dbddfhf", "https://upload.wikimedia.org/wikipedia/commons/thumb/8/8c/2008.06.16._Zianon_Pazniak_Fot_Mariusz_Kubik_01.JPG/250px-2008.06.16._Zianon_Pazniak_Fot_Mariusz_Kubik_01.JPG"));
        contacts.add(new Contact("dfbdf", "dbddfhf", "https://upload.wikimedia.org/wikipedia/commons/thumb/8/8c/2008.06.16._Zianon_Pazniak_Fot_Mariusz_Kubik_01.JPG/250px-2008.06.16._Zianon_Pazniak_Fot_Mariusz_Kubik_01.JPG"));
        contacts.add(new Contact("dfbdf", "dbddfhf", "https://upload.wikimedia.org/wikipedia/commons/thumb/8/8c/2008.06.16._Zianon_Pazniak_Fot_Mariusz_Kubik_01.JPG/250px-2008.06.16._Zianon_Pazniak_Fot_Mariusz_Kubik_01.JPG"));
        contacts.add(new Contact("dfbdf", "dbddfhf", "https://upload.wikimedia.org/wikipedia/commons/thumb/8/8c/2008.06.16._Zianon_Pazniak_Fot_Mariusz_Kubik_01.JPG/250px-2008.06.16._Zianon_Pazniak_Fot_Mariusz_Kubik_01.JPG"));

        ContactRecViewAdapter adapter = new ContactRecViewAdapter(this);
        adapter.setContacts(contacts);
        recyclerView.setAdapter(adapter);

        // гэта гартаць угору-дадолу
        //recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));


        // гэта гартаць праваруч-леваруч
        //recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        // гэта зрабіць табліцай, але трэ змяніць xml width -> wrap_content
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));

    }
}