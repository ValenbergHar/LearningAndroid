package com.example.kingsgdl.kings.tablelayout;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.example.kingsgdl.R;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.tabs.TabLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

public class MainActivityTable extends AppCompatActivity {
    private TextView kings_name;
    private TabLayout tabLayout;
    private AppBarLayout appBarLayout;
    private ViewPager viewPager;
    private TextView nameKingFragOne;
    private int id;
    private String name;
    private String urlImage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tabl);
        tabLayout = findViewById(R.id.tablayout_id);
        appBarLayout = findViewById(R.id.appbar);
        viewPager = findViewById(R.id.viewPagerId);
        kings_name = findViewById(R.id.kings_name);
        nameKingFragOne = findViewById(R.id.name_reign);


        Intent intent = getIntent();
        name = intent.getStringExtra("name");
        id = Integer.valueOf(intent.getStringExtra("id"));
        urlImage = intent.getStringExtra("image");


        kings_name.setText(name);
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager(), 3);

        adapter.addFragment(new FragmentOne(id, urlImage), "Звесткі");
        adapter.addFragment(new FragmentTwo(id), "Гісторыя");
        adapter.addFragment(new FragmentThree(id), "Фоткі");

        String[] strings = getResources().getStringArray((R.array.kings));

//        for (int i = 0; i < strings.length; i++) {
//            if (id == i) nameKingFragOne.setText(strings[i]);
//        }


        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
    }
}