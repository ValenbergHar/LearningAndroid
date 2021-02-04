package by.carkva_gazeta.malitounik;

import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
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
public class Help extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        if (!MainActivity.checkBrightness) {
            WindowManager.LayoutParams lp = getWindow().getAttributes();
            lp.screenBrightness = (float) MainActivity.brightness / 100;
            getWindow().setAttributes(lp);
        }
        SharedPreferences k = getSharedPreferences("biblia", MODE_PRIVATE);
        int fontBiblia = k.getInt("font_malitounik", SettingsActivity.GET_DEFAULT_FONT_SIZE);
        boolean dzenNoch = k.getBoolean("dzen_noch", false);
        if (dzenNoch) setTheme(R.style.AppCompatDark);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.help);
        TextView_Roboto_Condensed textView = findViewById(R.id.textView);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, fontBiblia);
        textView.setMovementMethod(LinkMovementMethod.getInstance());
        if (dzenNoch) {
            textView.setTextColor(ContextCompat.getColor(this, R.color.colorIcons));
        }
        try {
            InputStream inputStream = getResources().openRawResource(R.raw.help);
            InputStreamReader isr = new InputStreamReader(inputStream);
            BufferedReader reader = new BufferedReader(isr);
            String line;
            StringBuilder builder = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                if (dzenNoch)
                    line = line.replace("#d00505", "#f44336");
                builder.append(line).append("\n");
            }
            inputStream.close();
            textView.setText(MainActivity.fromHtml(builder.toString().replace("<!--version-->", "API " + Build.VERSION.SDK_INT)));
        } catch (Exception ignored) {
        }
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        TextView_Roboto_Condensed title_toolbar = findViewById(R.id.title_toolbar);
        title_toolbar.setTextSize(TypedValue.COMPLEX_UNIT_SP, SettingsActivity.GET_FONT_SIZE_MIN + 4);
        title_toolbar.setText(getResources().getString(R.string.help));
    }

    @Override
    protected void onResume() {
        super.onResume();
        overridePendingTransition(R.anim.alphain, R.anim.alphaout);
    }
}
