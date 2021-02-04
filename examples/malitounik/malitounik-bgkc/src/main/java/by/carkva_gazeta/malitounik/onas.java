package by.carkva_gazeta.malitounik;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.util.TypedValue;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Objects;

/**
 * Created by oleg on 9.7.16
 */
public class onas extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (!MainActivity.checkBrightness) {
            WindowManager.LayoutParams lp = getWindow().getAttributes();
            lp.screenBrightness = (float) MainActivity.brightness / 100;
            getWindow().setAttributes(lp);
        }
        SharedPreferences k = getSharedPreferences("biblia", MODE_PRIVATE);
        boolean dzenNoch = k.getBoolean("dzen_noch", false);
        if (dzenNoch) setTheme(R.style.AppCompatDark);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.onas);
        TextView_Roboto_Condensed textView = findViewById(R.id.onas);
        int fontBiblia = k.getInt("font_malitounik", SettingsActivity.GET_DEFAULT_FONT_SIZE);
        if (dzenNoch) {
            textView.setTextColor(ContextCompat.getColor(this, R.color.colorIcons));
        }
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, fontBiblia);
        textView.setMovementMethod(LinkMovementMethod.getInstance());
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
        title_toolbar.setText(getResources().getString(R.string.PRA_NAS));
        try {
            InputStream inputStream = getResources().openRawResource(R.raw.onas);
            InputStreamReader isr = new InputStreamReader(inputStream);
            BufferedReader reader = new BufferedReader(isr);
            String line;
            StringBuilder builder = new StringBuilder();
            String version = BuildConfig.VERSION_NAME;
            while ((line = reader.readLine()) != null) {
                if (dzenNoch)
                    line = line.replace("#d00505", "#f44336");
                if (line.contains("<!--<VERSION></VERSION>-->")) {
                    line = line.replace("<!--<VERSION></VERSION>-->", "<em>Версія праграмы: " + version + "</em><br><br>");
                }
                builder.append(line).append("\n");
            }
            inputStream.close();
            //CaseInsensitiveResourcesFontLoader FontLoader = new CaseInsensitiveResourcesFontLoader();
            textView.setText(MainActivity.fromHtml(builder.toString()));
        } catch (Throwable ignored) {
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        overridePendingTransition(R.anim.alphain, R.anim.alphaout);
    }
}
