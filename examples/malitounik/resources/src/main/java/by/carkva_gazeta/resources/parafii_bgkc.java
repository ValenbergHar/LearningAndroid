package by.carkva_gazeta.resources;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.MenuItem;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Objects;

import by.carkva_gazeta.malitounik.MainActivity;
import by.carkva_gazeta.malitounik.SettingsActivity;
import by.carkva_gazeta.malitounik.TextView_Roboto_Condensed;

public class parafii_bgkc extends AppCompatActivity {

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
        SharedPreferences k = getSharedPreferences("biblia", MODE_PRIVATE);
        float fontBiblia = k.getInt("font_malitounik", SettingsActivity.GET_DEFAULT_FONT_SIZE);
        boolean dzenNoch = k.getBoolean("dzen_noch", false);
        if (dzenNoch) setTheme(by.carkva_gazeta.malitounik.R.style.AppCompatDark);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parafii_bgkc);
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
        WebView webView = findViewById(R.id.WebView);
        WebSettings webSettings = webView.getSettings();
        webSettings.setStandardFontFamily("sans-serif-condensed");
        webSettings.setDefaultFontSize((int) fontBiblia);
        int prynagodnyia = Objects.requireNonNull(getIntent().getExtras()).getInt("bgkc_parafii", 0);
        bgkc = getIntent().getExtras().getInt("bgkc", 0);
        try {
            InputStream inputStream = getResources().openRawResource(R.raw.dzie_kuryia);
            switch (bgkc) {
                case 0:
                    if (prynagodnyia == 0)
                        inputStream = getResources().openRawResource(R.raw.dzie_kuryia);
                    if (prynagodnyia == 1)
                        inputStream = getResources().openRawResource(R.raw.dzie_centr_dekan);
                    if (prynagodnyia == 2)
                        inputStream = getResources().openRawResource(R.raw.dzie_usxod_dekan);
                    if (prynagodnyia == 3)
                        inputStream = getResources().openRawResource(R.raw.dzie_zaxod_dekan);
                    break;
                case 1:
                    if (prynagodnyia == 0)
                        inputStream = getResources().openRawResource(R.raw.dzie_centr_dekan);
                    if (prynagodnyia == 1)
                        inputStream = getResources().openRawResource(R.raw.dzie_barysau);
                    if (prynagodnyia == 2)
                        inputStream = getResources().openRawResource(R.raw.dzie_jodino);
                    if (prynagodnyia == 3)
                        inputStream = getResources().openRawResource(R.raw.dzie_zaslaue);
                    if (prynagodnyia == 4)
                        inputStream = getResources().openRawResource(R.raw.dzie_maladechna);
                    if (prynagodnyia == 5)
                        inputStream = getResources().openRawResource(R.raw.dzie_marenagorka);
                    if (prynagodnyia == 6)
                        inputStream = getResources().openRawResource(R.raw.dzie_mensk);
                    break;
                case 2:
                    if (prynagodnyia == 0)
                        inputStream = getResources().openRawResource(R.raw.dzie_usxod_dekan);
                    if (prynagodnyia == 1)
                        inputStream = getResources().openRawResource(R.raw.dzie_vitebsk);
                    if (prynagodnyia == 2)
                        inputStream = getResources().openRawResource(R.raw.dzie_orsha);
                    if (prynagodnyia == 3)
                        inputStream = getResources().openRawResource(R.raw.dzie_gomel);
                    if (prynagodnyia == 4)
                        inputStream = getResources().openRawResource(R.raw.dzie_polachk);
                    if (prynagodnyia == 5)
                        inputStream = getResources().openRawResource(R.raw.dzie_magilev);
                    break;
                case 3:
                    if (prynagodnyia == 0)
                        inputStream = getResources().openRawResource(R.raw.dzie_zaxod_dekan);
                    if (prynagodnyia == 1)
                        inputStream = getResources().openRawResource(R.raw.dzie_baranavichi);
                    if (prynagodnyia == 2)
                        inputStream = getResources().openRawResource(R.raw.dzie_brest);
                    if (prynagodnyia == 3)
                        inputStream = getResources().openRawResource(R.raw.dzie_grodno);
                    if (prynagodnyia == 4)
                        inputStream = getResources().openRawResource(R.raw.dzie_ivachevichi);
                    if (prynagodnyia == 5)
                        inputStream = getResources().openRawResource(R.raw.dzie_lida);
                    if (prynagodnyia == 6)
                        inputStream = getResources().openRawResource(R.raw.dzie_navagrudak);
                    if (prynagodnyia == 7)
                        inputStream = getResources().openRawResource(R.raw.dzie_pinsk);
                    if (prynagodnyia == 8)
                        inputStream = getResources().openRawResource(R.raw.dzie_slonim);
                    break;
                case 4:
                    if (prynagodnyia == 0)
                        inputStream = getResources().openRawResource(R.raw.dzie_anverpan);
                    if (prynagodnyia == 1)
                        inputStream = getResources().openRawResource(R.raw.dzie_londan);
                    if (prynagodnyia == 2)
                        inputStream = getResources().openRawResource(R.raw.dzie_warshava);
                    if (prynagodnyia == 3)
                        inputStream = getResources().openRawResource(R.raw.dzie_vilnia);
                    if (prynagodnyia == 4)
                        inputStream = getResources().openRawResource(R.raw.dzie_vena);
                    if (prynagodnyia == 5)
                        inputStream = getResources().openRawResource(R.raw.dzie_kaliningrad);
                    if (prynagodnyia == 6)
                        inputStream = getResources().openRawResource(R.raw.dzie_praga);
                    if (prynagodnyia == 7)
                        inputStream = getResources().openRawResource(R.raw.dzie_rym);
                    if (prynagodnyia == 8)
                        inputStream = getResources().openRawResource(R.raw.dzie_sanktpeterburg);
                    if (prynagodnyia == 9)
                        inputStream = getResources().openRawResource(R.raw.dzie_miniapolis);
                    break;
            }
            InputStreamReader is = new InputStreamReader(inputStream);
            BufferedReader reader = new BufferedReader(is);
            String line;
            StringBuilder builder = new StringBuilder();
            if (dzenNoch)
                builder.append("<html><head><style type=\"text/css\">a {color:#f44336;} body{color: #fff; background-color: #303030;}</style></head><body>\n");
            else
                builder.append("<html><head><style type=\"text/css\">a {color:#d00505;} body{color: #000; background-color: #fff;}</style></head><body>\n");
            while ((line = reader.readLine()) != null) {
                if (dzenNoch)
                    line = line.replace("#d00505", "#f44336");
                builder.append(line).append("\n");
            }
            builder.append("</body></html>");
            webView.loadDataWithBaseURL(null, builder.toString(), "text/html", "utf-8", null);
            is.close();
        } catch (Exception ignored) {
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem menuItem) {
        if (menuItem.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(menuItem);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent();
        intent.putExtra("bgkc", bgkc);
        setResult(Activity.RESULT_OK, intent);
        finish();
    }
}

