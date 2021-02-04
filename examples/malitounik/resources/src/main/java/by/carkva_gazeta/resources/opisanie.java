package by.carkva_gazeta.resources;

import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.Window;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Calendar;
import java.util.Objects;

import by.carkva_gazeta.malitounik.MainActivity;
import by.carkva_gazeta.malitounik.SettingsActivity;
import by.carkva_gazeta.malitounik.TextView_Roboto_Condensed;

/**
 * Created by oleg on 22.7.16
 */
public class opisanie extends AppCompatActivity {

    private InputStream inputStream;
    private boolean dzenNoch;

    @Override
    protected void onResume() {
        super.onResume();
        overridePendingTransition(by.carkva_gazeta.malitounik.R.anim.alphain, by.carkva_gazeta.malitounik.R.anim.alphaout);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (!MainActivity.checkBrightness) {
            WindowManager.LayoutParams lp = getWindow().getAttributes();
            lp.screenBrightness = (float) MainActivity.brightness / 100;
            getWindow().setAttributes(lp);
        }
        SharedPreferences chin = getSharedPreferences("biblia", MODE_PRIVATE);
        dzenNoch = chin.getBoolean("dzen_noch", false);
        super.onCreate(savedInstanceState);
        if (dzenNoch) setTheme(by.carkva_gazeta.malitounik.R.style.AppCompatDark);
        setContentView(R.layout.akafist_under);
        TextView_Roboto_Condensed textView = findViewById(R.id.TextView);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            if (dzenNoch) {
                window.setStatusBarColor(ContextCompat.getColor(this, by.carkva_gazeta.malitounik.R.color.colorPrimary_text));
                window.setNavigationBarColor(ContextCompat.getColor(this, by.carkva_gazeta.malitounik.R.color.colorPrimary_text));
            } else {
                window.setStatusBarColor(ContextCompat.getColor(this, by.carkva_gazeta.malitounik.R.color.colorPrimaryDark));
                window.setNavigationBarColor(ContextCompat.getColor(this, by.carkva_gazeta.malitounik.R.color.colorPrimaryDark));
            }
        }
        Calendar c = Calendar.getInstance();
        int mun = Objects.requireNonNull(getIntent().getExtras()).getInt("mun", c.get(Calendar.MONTH));
        int day = getIntent().getExtras().getInt("day", c.get(Calendar.DATE));
        String svity = getIntent().getExtras().getString("svity", "");
        Resources r = getResources();
        if (getIntent().getExtras().getBoolean("glavnyia", false)) {
            if (svity.toLowerCase().contains("уваход у ерусалім"))
                inputStream = r.openRawResource(R.raw.opisanie_sv0);
            if (svity.toLowerCase().contains("уваскрасеньне"))
                inputStream = r.openRawResource(R.raw.opisanie_sv1);
            if (svity.toLowerCase().contains("узьнясеньне"))
                inputStream = r.openRawResource(R.raw.opisanie_sv2);
            if (svity.toLowerCase().contains("зыход"))
                inputStream = r.openRawResource(R.raw.opisanie_sv3);
            String resFile = day + "_" + mun;
            if (resFile.contains("1_0"))
                inputStream = r.openRawResource(R.raw.opisanie1_0);
            if (resFile.contains("2_1"))
                inputStream = r.openRawResource(R.raw.opisanie2_1);
            if (resFile.contains("6_0"))
                inputStream = r.openRawResource(R.raw.opisanie6_0);
            if (resFile.contains("6_7"))
                inputStream = r.openRawResource(R.raw.opisanie6_7);
            if (resFile.contains("8_8"))
                inputStream = r.openRawResource(R.raw.opisanie8_8);
            if (resFile.contains("14_8"))
                inputStream = r.openRawResource(R.raw.opisanie14_8);
            if (resFile.contains("15_7"))
                inputStream = r.openRawResource(R.raw.opisanie15_7);
            if (resFile.contains("25_2"))
                inputStream = r.openRawResource(R.raw.opisanie25_2);
            if (resFile.contains("21_10"))
                inputStream = r.openRawResource(R.raw.opisanie21_10);
            if (resFile.contains("25_11"))
                inputStream = r.openRawResource(R.raw.opisanie25_11);
            if (inputStream != null) {
                try {
                    InputStreamReader isr = new InputStreamReader(inputStream);
                    BufferedReader reader = new BufferedReader(isr);
                    String line;
                    StringBuilder builder = new StringBuilder();
                    while ((line = reader.readLine()) != null) {
                        line = line.replace("h3", "h6");
                        builder.append(line).append("\n");
                    }
                    inputStream.close();
                    if (dzenNoch)
                        textView.setTextColor(ContextCompat.getColor(this, by.carkva_gazeta.malitounik.R.color.colorIcons));
                    else
                        textView.setTextColor(ContextCompat.getColor(this, by.carkva_gazeta.malitounik.R.color.colorPrimary_text));
                    textView.setText(MainActivity.fromHtml(builder.toString()));
                } catch (Throwable ignored) {
                }
            } else {
                if (dzenNoch)
                    textView.setTextColor(ContextCompat.getColor(this, by.carkva_gazeta.malitounik.R.color.colorIcons));
                else
                    textView.setTextColor(ContextCompat.getColor(this, by.carkva_gazeta.malitounik.R.color.colorPrimary_text));
                textView.setText(MainActivity.fromHtml("<h6>" + svity + "<h6>"));
            }
        } else {
            switch (mun) {
                case 0:
                    inputStream = r.openRawResource(R.raw.opisanie1);
                    break;
                case 1:
                    inputStream = r.openRawResource(R.raw.opisanie2);
                    break;
                case 2:
                    inputStream = r.openRawResource(R.raw.opisanie3);
                    break;
                case 3:
                    inputStream = r.openRawResource(R.raw.opisanie4);
                    break;
                case 4:
                    inputStream = r.openRawResource(R.raw.opisanie5);
                    break;
                case 5:
                    inputStream = r.openRawResource(R.raw.opisanie6);
                    break;
                case 6:
                    inputStream = r.openRawResource(R.raw.opisanie7);
                    break;
                case 7:
                    inputStream = r.openRawResource(R.raw.opisanie8);
                    break;
                case 8:
                    inputStream = r.openRawResource(R.raw.opisanie9);
                    break;
                case 9:
                    inputStream = r.openRawResource(R.raw.opisanie10);
                    break;
                case 10:
                    inputStream = r.openRawResource(R.raw.opisanie11);
                    break;
                case 11:
                    inputStream = r.openRawResource(R.raw.opisanie12);
                    break;
            }
            if (inputStream != null) {
                try {
                    InputStreamReader isr = new InputStreamReader(inputStream);
                    BufferedReader reader = new BufferedReader(isr);
                    String line;
                    StringBuilder builder = new StringBuilder();
                    while ((line = reader.readLine()) != null) {
                        line = line.replace("h3", "h6");
                        builder.append(line).append("\n");
                    }
                    inputStream.close();
                    String dataR;
                    String munR;
                    if (day < 10) dataR = "0" + day;
                    else dataR = String.valueOf(day);
                    mun++;
                    if (mun < 10) munR = "0" + mun;
                    else munR = String.valueOf(mun);
                    String res = builder.toString();
                    int tN = res.indexOf("<div id=\"" + dataR + munR + "\">");
                    int tK = res.indexOf("</div>", tN);
                    res = res.substring(tN, tK + 6);
                    if (dzenNoch)
                        textView.setTextColor(ContextCompat.getColor(this, by.carkva_gazeta.malitounik.R.color.colorIcons));
                    else
                        textView.setTextColor(ContextCompat.getColor(this, by.carkva_gazeta.malitounik.R.color.colorPrimary_text));
                    textView.setText(MainActivity.fromHtml(res));
                } catch (Throwable ignored) {
                }
            }
        }

        setTollbarTheme();
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
        title_toolbar.setText(getResources().getText(by.carkva_gazeta.malitounik.R.string.zmiest));
        if (dzenNoch) {
            toolbar.setPopupTheme(by.carkva_gazeta.malitounik.R.style.AppCompatDark);
            toolbar.setBackgroundResource(by.carkva_gazeta.malitounik.R.color.colorprimary_material_dark);
        }
    }
}
