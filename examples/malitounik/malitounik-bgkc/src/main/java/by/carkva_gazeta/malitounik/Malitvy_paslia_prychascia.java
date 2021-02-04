package by.carkva_gazeta.malitounik;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.SystemClock;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.WindowManager;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.Objects;

/**
 * Created by oleg on 30.5.16
 */
public class Malitvy_paslia_prychascia extends AppCompatActivity {

    private final String[] data = {"Малітва падзякі", "Малітва сьв. Васіля Вялікага", "Малітва Сымона Мэтафраста", "Iншая малітва", "Малітва да Найсьвяцейшай Багародзіцы"};
    private boolean result = false;
    private long mLastClickTime = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        SharedPreferences chin = getSharedPreferences("biblia", MODE_PRIVATE);
        boolean dzenNoch = chin.getBoolean("dzen_noch", false);
        if (dzenNoch) setTheme(R.style.AppCompatDark);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.akafist_list);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
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
        title_toolbar.setText(getResources().getText(R.string.pasliaPrychscia));
        if (dzenNoch) {
            toolbar.setPopupTheme(R.style.AppCompatDark);
            toolbar.setBackgroundResource(R.color.colorprimary_material_dark);
            title_toolbar.setBackgroundResource(R.color.colorprimary_material_dark);
        }
        ListView listView = findViewById(R.id.ListView);

        Menu_ListAdaprer adapter = new Menu_ListAdaprer(this, data);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener((adapterView, view, position, id) -> {
            if (SystemClock.elapsedRealtime() - mLastClickTime < 1000) {
                return;
            }
            mLastClickTime = SystemClock.elapsedRealtime();
            if (MainActivity.checkModule_resources(this)) {
                try {
                    Intent intent = new Intent(this, Class.forName("by.carkva_gazeta.resources.paslia_prychascia"));
                    intent.putExtra("paslia_prychascia", position + 1);
                    startActivityForResult(intent, 300);
                } catch (ClassNotFoundException ignored) {
                }
            } else {
                Dialog_install_Dadatak dadatak = new Dialog_install_Dadatak();
                dadatak.show(getSupportFragmentManager(), "dadatak");
            }
        });
        if (savedInstanceState != null) {
            result = savedInstanceState.getBoolean("result");
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!MainActivity.checkBrightness) {
            WindowManager.LayoutParams lp = getWindow().getAttributes();
            lp.screenBrightness = (float) MainActivity.brightness / 100;
            getWindow().setAttributes(lp);
        }
        overridePendingTransition(R.anim.alphain, R.anim.alphaout);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 300) {
            result = true;
            recreate();
        }
    }

    @Override
    public void onBackPressed() {
        if (result)
            onSupportNavigateUp();
        else
            super.onBackPressed();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean("result", result);
    }
}
