package by.carkva_gazeta.malitounik;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.collection.ArrayMap;
import androidx.core.content.ContextCompat;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class navinyView extends AppCompatActivity {

    private TextView_Roboto_Condensed textView;
    private boolean dzenNoch = false;

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!MainActivity.checkBrightness) {
            WindowManager.LayoutParams lp = getWindow().getAttributes();
            lp.screenBrightness = (float) MainActivity.brightness / 100;
            getWindow().setAttributes(lp);
        }
        SharedPreferences kq = getSharedPreferences("biblia", MODE_PRIVATE);
        dzenNoch = kq.getBoolean("dzen_noch", false);
        if (dzenNoch)
            setTheme(R.style.AppCompatDark);
        setContentView(R.layout.activity_naviny);

        setTollbarTheme();

        if (dzenNoch) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                Window window = getWindow();
                window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.setStatusBarColor(ContextCompat.getColor(this, R.color.colorPrimary_text));
                window.setNavigationBarColor(ContextCompat.getColor(this, R.color.colorPrimary_text));
            }
        }
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        ArrayMap<String, String> mnemonics = new ArrayMap<>();
        mnemonics.put("&amp;", "\u0026");
        mnemonics.put("&lt;", "\u003C");
        mnemonics.put("&gt;", "\u003E");
        mnemonics.put("&laquo;", "\u00AB");
        mnemonics.put("&raquo;", "\u00BB");
        mnemonics.put("&nbsp;", "\u0020");
        mnemonics.put("&mdash;", "\u0020-\u0020");

        String output = Objects.requireNonNull(getIntent().getExtras()).getString("url");
        for (String key : mnemonics.keySet()) {
            Matcher matcher = Pattern.compile(key).matcher(Objects.requireNonNull(output));
            output = matcher.replaceAll(Objects.requireNonNull(mnemonics.get(key)));
        }
        textView.setText(output);
        WebView mviewWeb = findViewById(R.id.viewWeb);
        mviewWeb.getSettings().setJavaScriptEnabled(true);
        mviewWeb.setWebViewClient(new WebViewClient() {

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (MainActivity.isNetworkAvailable(navinyView.this)) {
                    if (Objects.requireNonNull(Uri.parse(url).getHost()).endsWith("m.carkva-gazeta.by")) {
                        return false;
                    }
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    view.getContext().startActivity(intent);
                } else {
                    Dialog_no_internet dialog_no_internet = new Dialog_no_internet();
                    dialog_no_internet.show(getSupportFragmentManager(), "no_internet");
                }
                return true;
            }
        });
        mviewWeb.loadDataWithBaseURL("file:///android_asset/", getIntent().getExtras().getString("htmlData"), "text/html", "UTF-8", null);
    }

    private void setTollbarTheme() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        textView = findViewById(R.id.title_toolbar);
        textView.setOnClickListener((v) -> {
            textView.setHorizontallyScrolling(true);
            textView.setFreezesText(true);
            textView.setMarqueeRepeatLimit(-1);
            if (textView.isSelected()) {
                textView.setEllipsize(TextUtils.TruncateAt.END);
                textView.setSelected(false);
            } else {
                textView.setEllipsize(TextUtils.TruncateAt.MARQUEE);
                textView.setSelected(true);
            }
        });
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, SettingsActivity.GET_FONT_SIZE_MIN + 4);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        if (dzenNoch) {
            toolbar.setBackgroundResource(R.color.colorprimary_material_dark);
        }
    }
}
