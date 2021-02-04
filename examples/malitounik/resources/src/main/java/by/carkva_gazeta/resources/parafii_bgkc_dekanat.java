package by.carkva_gazeta.resources;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.WindowManager;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.ArrayList;
import java.util.Objects;

import by.carkva_gazeta.malitounik.MainActivity;
import by.carkva_gazeta.malitounik.Menu_ListAdaprer;
import by.carkva_gazeta.malitounik.SettingsActivity;
import by.carkva_gazeta.malitounik.TextView_Roboto_Condensed;

/**
 * Created by oleg on 30.5.16
 */
public class parafii_bgkc_dekanat extends AppCompatActivity {

    private int bgkc;

    @Override
    protected void onResume() {
        super.onResume();
        overridePendingTransition(by.carkva_gazeta.malitounik.R.anim.alphain, by.carkva_gazeta.malitounik.R.anim.alphaout);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        if (!MainActivity.checkBrightness) {
            WindowManager.LayoutParams lp = getWindow().getAttributes();
            lp.screenBrightness = (float) MainActivity.brightness / 100;
            getWindow().setAttributes(lp);
        }
        SharedPreferences chin = getSharedPreferences("biblia", MODE_PRIVATE);
        boolean dzenNoch = chin.getBoolean("dzen_noch", false);
        if (dzenNoch) setTheme(by.carkva_gazeta.malitounik.R.style.AppCompatDark);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bgkc_list);
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
        title_toolbar.setText(getResources().getText(by.carkva_gazeta.malitounik.R.string.parafii));
        ListView listView = findViewById(R.id.ListView);
        ArrayList<String> data = new ArrayList<>();

        bgkc = getIntent().getIntExtra("bgkc", 1);

        if (bgkc == 1) {
            data.add("Цэнтральны дэканат");
            data.add("Барысаў");
            data.add("Жодзіна");
            data.add("Заслаўе");
            data.add("Маладэчна");
            data.add("Мар'іна Горка");
            data.add("Менск");
        }
        if (bgkc == 2) {
            data.add("Усходні дэканат");
            data.add("Віцебск");
            data.add("Ворша");
            data.add("Гомель");
            data.add("Полацак");
            data.add("Магілёў");
        }
        if (bgkc == 3) {
            data.add("Заходні дэканат");
            data.add("Баранавічы");
            data.add("Берасьце");
            data.add("Горадня");
            data.add("Івацэвічы");
            data.add("Ліда");
            data.add("Наваградак");
            data.add("Пінск");
            data.add("Слонім");
        }
        if (bgkc == 4) {
            data.add("Антвэрпан (Бельгія)");
            data.add("Лондан (Вялікабрытанія)");
            data.add("Варшава (Польшча)");
            data.add("Вільня (Літва)");
            data.add("Вена (Аўстрыя)");
            data.add("Калінінград (Расея)");
            data.add("Прага (Чэхія)");
            data.add("Рым (Італія)");
            data.add("Санкт-Пецярбург (Расея)");
            data.add("Мінеапаліс (ЗША)");
        }

        Menu_ListAdaprer adapter = new Menu_ListAdaprer(this, data);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener((adapterView, view, position, id) -> {
            Intent intent = new Intent(this, parafii_bgkc.class);
            intent.putExtra("bgkc_parafii", position);
            intent.putExtra("bgkc", bgkc);
            startActivityForResult(intent, 25);
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 25) {
            if (data != null) bgkc = data.getIntExtra("bgkc", 1);
        }
    }
}
