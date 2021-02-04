package by.carkva_gazeta.malitounik;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.SystemClock;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.ExpandableListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.ArrayList;
import java.util.Objects;

public class stary_zapaviet2 extends AppCompatActivity {

    private boolean dzenNoch;
    private long mLastClickTime = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences k = getSharedPreferences("biblia", MODE_PRIVATE);
        dzenNoch = k.getBoolean("dzen_noch", false);
        if (dzenNoch) setTheme(R.style.AppCompatDark);
        setContentView(R.layout.content_bible);

        ExpandableListView elvMain = findViewById(R.id.elvMain);

        ArrayList<ArrayList<String>> groups = new ArrayList<>();
        ArrayList<String> children1 = new ArrayList<>();
        ArrayList<String> children2 = new ArrayList<>();
        ArrayList<String> children3 = new ArrayList<>();
        ArrayList<String> children4 = new ArrayList<>();
        ArrayList<String> children5 = new ArrayList<>();
        ArrayList<String> children6 = new ArrayList<>();
        ArrayList<String> children7 = new ArrayList<>();
        ArrayList<String> children8 = new ArrayList<>();
        ArrayList<String> children9 = new ArrayList<>();
        ArrayList<String> children10 = new ArrayList<>();
        ArrayList<String> children11 = new ArrayList<>();
        ArrayList<String> children12 = new ArrayList<>();
        ArrayList<String> children13 = new ArrayList<>();
        ArrayList<String> children14 = new ArrayList<>();
        ArrayList<String> children15 = new ArrayList<>();
        ArrayList<String> children16 = new ArrayList<>();
        ArrayList<String> children17 = new ArrayList<>();
        ArrayList<String> children18 = new ArrayList<>();
        ArrayList<String> children19 = new ArrayList<>();
        ArrayList<String> children20 = new ArrayList<>();
        ArrayList<String> children21 = new ArrayList<>();
        ArrayList<String> children22 = new ArrayList<>();
        ArrayList<String> children23 = new ArrayList<>();
        ArrayList<String> children24 = new ArrayList<>();
        ArrayList<String> children25 = new ArrayList<>();
        ArrayList<String> children26 = new ArrayList<>();
        ArrayList<String> children27 = new ArrayList<>();
        ArrayList<String> children28 = new ArrayList<>();
        ArrayList<String> children29 = new ArrayList<>();
        ArrayList<String> children30 = new ArrayList<>();
        ArrayList<String> children31 = new ArrayList<>();
        ArrayList<String> children32 = new ArrayList<>();
        ArrayList<String> children33 = new ArrayList<>();
        ArrayList<String> children34 = new ArrayList<>();
        ArrayList<String> children35 = new ArrayList<>();
        ArrayList<String> children36 = new ArrayList<>();
        ArrayList<String> children37 = new ArrayList<>();
        ArrayList<String> children38 = new ArrayList<>();
        ArrayList<String> children39 = new ArrayList<>();

        for (int i = 1; i <= 50; i++) {
            children1.add("Разьдзел " + i);
        }
        groups.add(children1);
        for (int i = 1; i <= 40; i++) {
            children2.add("Разьдзел " + i);
        }
        groups.add(children2);
        for (int i = 1; i <= 27; i++) {
            children3.add("Разьдзел " + i);
        }
        groups.add(children3);
        for (int i = 1; i <= 36; i++) {
            children4.add("Разьдзел " + i);
        }
        groups.add(children4);
        for (int i = 1; i <= 34; i++) {
            children5.add("Разьдзел " + i);
        }
        groups.add(children5);
        for (int i = 1; i <= 24; i++) {
            children6.add("Разьдзел " + i);
        }
        groups.add(children6);
        for (int i = 1; i <= 21; i++) {
            children7.add("Разьдзел " + i);
        }
        groups.add(children7);
        for (int i = 1; i <= 4; i++) {
            children8.add("Разьдзел " + i);
        }
        groups.add(children8);
        for (int i = 1; i <= 31; i++) {
            children9.add("Разьдзел " + i);
        }
        groups.add(children9);
        for (int i = 1; i <= 24; i++) {
            children10.add("Разьдзел " + i);
        }
        groups.add(children10);
        for (int i = 1; i <= 22; i++) {
            children11.add("Разьдзел " + i);
        }
        groups.add(children11);
        for (int i = 1; i <= 25; i++) {
            children12.add("Разьдзел " + i);
        }
        groups.add(children12);
        for (int i = 1; i <= 29; i++) {
            children13.add("Разьдзел " + i);
        }
        groups.add(children13);
        for (int i = 1; i <= 36; i++) {
            children14.add("Разьдзел " + i);
        }
        groups.add(children14);
        for (int i = 1; i <= 10; i++) {
            children15.add("Разьдзел " + i);
        }
        groups.add(children15);
        for (int i = 1; i <= 13; i++) {
            children16.add("Разьдзел " + i);
        }
        groups.add(children16);
        for (int i = 1; i <= 10; i++) {
            children17.add("Разьдзел " + i);
        }
        groups.add(children17);
        for (int i = 1; i <= 42; i++) {
            children18.add("Разьдзел " + i);
        }
        groups.add(children18);
        for (int i = 1; i <= 151; i++) {
            children19.add("Псальма " + i);
        }
        groups.add(children19);
        for (int i = 1; i <= 31; i++) {
            children20.add("Разьдзел " + i);
        }
        groups.add(children20);
        for (int i = 1; i <= 12; i++) {
            children21.add("Разьдзел " + i);
        }
        groups.add(children21);
        for (int i = 1; i <= 8; i++) {
            children22.add("Разьдзел " + i);
        }
        groups.add(children22);
        for (int i = 1; i <= 66; i++) {
            children23.add("Разьдзел " + i);
        }
        groups.add(children23);
        for (int i = 1; i <= 52; i++) {
            children24.add("Разьдзел " + i);
        }
        groups.add(children24);
        for (int i = 1; i <= 5; i++) {
            children25.add("Разьдзел " + i);
        }
        groups.add(children25);
        for (int i = 1; i <= 48; i++) {
            children26.add("Разьдзел " + i);
        }
        groups.add(children26);
        for (int i = 1; i <= 12; i++) {
            children27.add("Разьдзел " + i);
        }
        groups.add(children27);
        for (int i = 1; i <= 14; i++) {
            children28.add("Разьдзел " + i);
        }
        groups.add(children28);
        for (int i = 1; i <= 3; i++) {
            children29.add("Разьдзел " + i);
        }
        groups.add(children29);
        for (int i = 1; i <= 9; i++) {
            children30.add("Разьдзел " + i);
        }
        groups.add(children30);
        for (int i = 1; i <= 1; i++) {
            children31.add("Разьдзел " + i);
        }
        groups.add(children31);
        for (int i = 1; i <= 4; i++) {
            children32.add("Разьдзел " + i);
        }
        groups.add(children32);
        for (int i = 1; i <= 7; i++) {
            children33.add("Разьдзел " + i);
        }
        groups.add(children33);
        for (int i = 1; i <= 3; i++) {
            children34.add("Разьдзел " + i);
        }
        groups.add(children34);
        for (int i = 1; i <= 3; i++) {
            children35.add("Разьдзел " + i);
        }
        groups.add(children35);
        for (int i = 1; i <= 3; i++) {
            children36.add("Разьдзел " + i);
        }
        groups.add(children36);
        for (int i = 1; i <= 2; i++) {
            children37.add("Разьдзел " + i);
        }
        groups.add(children37);
        for (int i = 1; i <= 14; i++) {
            children38.add("Разьдзел " + i);
        }
        groups.add(children38);
        for (int i = 1; i <= 4; i++) {
            children39.add("Разьдзел " + i);
        }
        groups.add(children39);

        ExpListAdapterStaryZapaviet adapter = new ExpListAdapterStaryZapaviet(this, groups);
        elvMain.setAdapter(adapter);

        elvMain.setOnChildClickListener((parent, v, groupPosition, childPosition, id) -> {
            if (SystemClock.elapsedRealtime() - mLastClickTime < 1000){
                return true;
            }
            mLastClickTime = SystemClock.elapsedRealtime();
            if (MainActivity.checkModule_resources(this)) {
                try {
                    Intent intent = new Intent(this, Class.forName("by.carkva_gazeta.resources.stary_zapaviet3"));
                    intent.putExtra("kniga", groupPosition);
                    intent.putExtra("glava", childPosition);
                    startActivity(intent);
                } catch (ClassNotFoundException ignored) {
                }
            } else {
                Dialog_install_Dadatak dadatak = new Dialog_install_Dadatak();
                dadatak.show(getSupportFragmentManager(), "dadatak");
            }
            return false;
        });

        if (getIntent().getExtras() != null && getIntent().getExtras().getBoolean("prodolzyt", false)) {
            try {
                Intent intent = new Intent(this, Class.forName("by.carkva_gazeta.resources.stary_zapaviet3"));
                intent.putExtra("kniga", getIntent().getExtras().getInt("kniga"));
                intent.putExtra("glava", getIntent().getExtras().getInt("glava"));
                intent.putExtra("stix", getIntent().getExtras().getInt("stix"));
                startActivity(intent);
            } catch (ClassNotFoundException ignored) {
            }
        }
    }

    private void setTollbarTheme() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        TextView_Roboto_Condensed title_toolbar = findViewById(R.id.title_toolbar);
        title_toolbar.setOnClickListener((v) -> {
            title_toolbar.setHorizontallyScrolling(true);
            title_toolbar.setFreezesText(true);
            title_toolbar.setMarqueeRepeatLimit(-1);
            if (title_toolbar.isSelected()) {
                title_toolbar.setEllipsize(TextUtils.TruncateAt.END);
                title_toolbar.setSelected(false);
            } else {
                title_toolbar.setEllipsize(TextUtils.TruncateAt.MARQUEE);
                title_toolbar.setSelected(true);
            }
        });
        title_toolbar.setTextSize(TypedValue.COMPLEX_UNIT_SP, SettingsActivity.GET_FONT_SIZE_MIN + 4);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        title_toolbar.setText(R.string.stary_zapaviet);
        if (dzenNoch) {
            toolbar.setPopupTheme(R.style.AppCompatDark);
            toolbar.setBackgroundResource(R.color.colorprimary_material_dark);
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!MainActivity.checkBrightness) {
            WindowManager.LayoutParams lp = getWindow().getAttributes();
            lp.screenBrightness = (float) MainActivity.brightness / 100;
            getWindow().setAttributes(lp);
        }
        setTollbarTheme();
        overridePendingTransition(R.anim.alphain, R.anim.alphaout);
    }
}
