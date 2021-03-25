package com.example.kingsgdl.kings.tablelayout;

import android.content.Intent;
import android.os.Bundle;
import android.text.Spanned;
import android.widget.TextView;

import com.example.kingsgdl.R;
import com.example.kingsgdl.StaticContextFactory;
import com.example.kingsgdl.kings.King;
import com.example.kingsgdl.kings.KingFull;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.tabs.TabLayout;

import java.lang.reflect.Field;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.text.HtmlCompat;
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

    private KingFull kingFull;
    private King king;


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
        king = (King) intent.getSerializableExtra("intent");

        String name = king.getLink();
        int resID = getResId(name, R.raw.class);

        kingFull=LoadKing.kingsListWithAdd(StaticContextFactory.getAppContext(), resID);

        String htmlString = kingFull.getKingName();
        Spanned spanned = HtmlCompat.fromHtml(htmlString, HtmlCompat.FROM_HTML_MODE_COMPACT);
        kings_name.setText(spanned);
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager(), 3);




        adapter.addFragment(new FragmentOne(kingFull), "Звесткі");
        adapter.addFragment(new FragmentTwo(kingFull), "Гісторыя");
        adapter.addFragment(new FragmentThree(kingFull), "Відарысы");

        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
    }

    public static int getResId(String resName, Class<?> c) {

        try {
            Field idField = c.getDeclaredField(resName);
            return idField.getInt(idField);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }
}