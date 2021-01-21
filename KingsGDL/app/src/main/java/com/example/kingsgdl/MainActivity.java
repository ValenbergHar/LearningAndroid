package com.example.kingsgdl;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.kingsgdl.tablelayout.MainActivityTable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<King> kingsList;

    private RecyclerView recyclerView;
    private RecyclerViewAdapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        kingsList = new ArrayList<>();

        King p0 = new King(0, "Міндоўг", 1236, "https://upload.wikimedia.org/wikipedia/commons/2/24/Mendog_1578.png");
        King p1 = new King(1, "Транята", 1263, "https://upload.wikimedia.org/wikipedia/commons/2/2e/Treniota.jpg");
        King p2 = new King(2, "Войшалк", 1264, "https://upload.wikimedia.org/wikipedia/commons/thumb/6/69/Vojshalk.png/230px-Vojshalk.png");
        King p3 = new King(3, "Шварн", 1267, "https://i.c97.org/gi/44659/229844-1541254501-big.jpg");
        King p4 = new King(4, "Тройдзень", 1269, "https://upload.wikimedia.org/wikipedia/commons/b/bd/Lithuanian_Grand_Duke_Traidenis.JPG");
        King p5 = new King(5, "Даўмонт", 1282, "https://i.c97.org/gi/44659/229844-1541254501-big.jpg");
        King p6 = new King(6, "Будзікід", 1285, "https://i.c97.org/gi/44659/229844-1541254501-big.jpg");
        King p7 = new King(7, "Будзівід", 1290, "https://i.c97.org/gi/44659/229844-1541254501-big.jpg");
        King p8 = new King(8, "Віцень", 1295, "https://upload.wikimedia.org/wikipedia/commons/thumb/6/6d/Witenes.PNG/274px-Witenes.PNG");
        King p9 = new King(9, "Гедымін (Гедзімін)", 1316, "https://upload.wikimedia.org/wikipedia/commons/thumb/4/4f/Gedimin_grav_xvii.jpg/274px-Gedimin_grav_xvii.jpg");
        King p10 = new King(10, "Яўнут", 1341, "https://i.c97.org/gi/44659/229844-1541254501-big.jpg");
        King p11 = new King(11, "Альгерд", 1345, "https://upload.wikimedia.org/wikipedia/commons/thumb/7/70/Algierd._Альгерд_%28A._Guagnini%2C_1578%29.jpg/274px-Algierd._Альгерд_%28A._Guagnini%2C_1578%29.jpg");
        King p12 = new King(12, "Ягайла", 1377, "https://upload.wikimedia.org/wikipedia/commons/c/ca/Jagiello.jpg");
        King p13 = new King(13, "Кейстут", 1377, "https://www.calend.ru/img/content_persons/i3/3790.jpg");
        King p14 = new King(14, "Вітаўт", 1392, "https://s2.15min.lt/images/photos/2010/09/07/original/1283882267vytautas_didysis.jpg");
        King p15 = new King(15, "Свідрыгайла", 1430, "https://upload.wikimedia.org/wikipedia/commons/thumb/a/a5/Śvidrygajła._Сьвідрыгайла_%28A._Guagnini%2C_1578%29.jpg/280px-Śvidrygajła._Сьвідрыгайла_%28A._Guagnini%2C_1578%29.jpg");
        King p16 = new King(16, "Жыгімонт Кейстутавіч", 1432, "https://upload.wikimedia.org/wikipedia/commons/b/b4/Žygimont_Kiejstutavič._Жыгімонт_Кейстутавіч_%28J._Aziambłoŭski%2C_1840%29.jpg");
        King p17 = new King(17, "Казімір", 1440, "https://upload.wikimedia.org/wikipedia/commons/5/5d/Kazimierz_Jagiellonczyk.jpg");
        King p18 = new King(18, "Аляксандр", 1492, "https://upload.wikimedia.org/wikipedia/commons/9/9c/Aleksander_Jagiellonczyk.jpg");
        King p19 = new King(19, "Жыгімонт I Стары", 1506, "https://upload.wikimedia.org/wikipedia/commons/thumb/6/6a/Lesseur-Zygmunt_I_Stary.jpg/420px-Lesseur-Zygmunt_I_Stary.jpg");
        King p20 = new King(20, "Жыгімонт II Аўгуст", 1544, "https://upload.wikimedia.org/wikipedia/commons/thumb/4/4c/Cranach_the_Younger_Sigismund_II_Augustus.jpg/800px-Cranach_the_Younger_Sigismund_II_Augustus.jpg");
        King p21 = new King(21, "Генрых Валуа", 1573, "https://upload.wikimedia.org/wikipedia/commons/f/f1/Henri_III_Versailles.jpg");
        King p22 = new King(22, "Стэфан Баторый", 1576, "https://upload.wikimedia.org/wikipedia/commons/thumb/8/8a/Stefan_Batory_King.jpg/209px-Stefan_Batory_King.jpg");
        King p23 = new King(23, "Жыгімонт III Ваза", 1587, "https://upload.wikimedia.org/wikipedia/commons/b/b4/Sigismund_at_horse.jpg");
        King p24 = new King(24, "Уладзіслаў IV Ваза", 1632, "https://uploads.knightlab.com/storymapjs/47785f2faa76c6741c6c0eb2dc400ebc/vaza/_images/Vladislavs-IV-Vasa.jpg");
        King p25 = new King(25, "Ян II Казімір Ваза", 1648, "https://upload.wikimedia.org/wikipedia/commons/5/59/Bacciarelli_-_Jan_Kazimierz.jpeg");
        King p26 = new King(26, "Міхал Карыбут-Вішнявецкі", 1669, "https://upload.wikimedia.org/wikipedia/commons/e/e5/Daniel_Schultz_d._J._005.jpg");
        King p27 = new King(27, "Ян III Сабескі", 1673, "https://upload.wikimedia.org/wikipedia/commons/c/cd/John_III_Sobieski_of_Poland.PNG");
        King p28 = new King(28, "Аўгуст Моцны", 1697, "https://upload.wikimedia.org/wikipedia/commons/d/d6/Aŭgust_Mocny._Аўгуст_Моцны_%28L._Silvestre%2C_1718%29.jpg");
        King p29 = new King(29, "Аўгуст III", 1734, "https://upload.wikimedia.org/wikipedia/commons/thumb/b/ba/King_Augustus_III_of_Poland.jpg/800px-King_Augustus_III_of_Poland.jpg");
        King p30 = new King(30, "Станіслаў II Аўгуст Панятоўскі", 1764, "https://upload.wikimedia.org/wikipedia/commons/thumb/0/00/Stanisław_August_Poniatowski_coronation_robes.jpg/800px-Stanisław_August_Poniatowski_coronation_robes.jpg");

        kingsList.addAll(Arrays.asList(new King[]{p0,p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13, p14, p15, p16, p17, p18, p19, p20, p21, p22, p23, p24, p25, p26, p27, p28, p29, p30}));
        //kingsList.addAll(Arrays.asList(new King[]{p1, p2, p3, p4, p5, p6, p7}));

        recyclerView = findViewById(R.id.lv_kingList);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(MainActivity.this);
        recyclerView.setLayoutManager(layoutManager);

        mAdapter = new RecyclerViewAdapter(kingsList, MainActivity.this);
        recyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new RecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Intent intent = new Intent(MainActivity.this, MainActivityTable.class);

                intent.putExtra("id", String.valueOf(kingsList.get(position).getId()));
                intent.putExtra("name", kingsList.get(position).getName());
                intent.putExtra("date", String.valueOf(kingsList.get(position).getDateOfElection()));
                intent.putExtra("image", kingsList.get(position).getImageUrl());

                startActivity(intent);
                Toast.makeText(MainActivity.this, String.valueOf(position), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        MenuItem item = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) item.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                mAdapter.getFilter().filter(newText);
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }
}