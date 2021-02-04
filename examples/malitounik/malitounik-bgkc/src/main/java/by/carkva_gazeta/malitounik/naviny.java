package by.carkva_gazeta.malitounik;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.collection.ArrayMap;
import androidx.core.content.ContextCompat;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class naviny extends AppCompatActivity {

    private static final int UI_ANIMATION_DELAY = 300;
    private final Handler mHideHandler = new Handler();
    private final Runnable mHidePart2Runnable = new Runnable() {
        @SuppressLint("InlinedApi")
        @Override
        public void run() {
            relative.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                    | View.SYSTEM_UI_FLAG_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        }
    };
    private final Runnable mShowPart2Runnable = () -> {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.show();
        }
    };
    private WebView mviewWeb;
    private SharedPreferences kq;
    private final ArrayList<String> searchHistory = new ArrayList<>();
    private String mUrl;
    private TextView_Roboto_Condensed textView;
    private ListView listView;
    private final ArrayList<ArrayList<String>> arrayList = new ArrayList<>();
    private boolean fullscreenPage = false;
    private boolean dzenNoch = false;
    private RelativeLayout relative;
    private boolean errorInternet = false;
    private ValueCallback<Uri> mUploadMessage;
    private ValueCallback<Uri[]> mUploadMessageArr;
    private long mLastClickTime = 0;

    @SuppressLint({"SetTextI18n", "SetJavaScriptEnabled"})
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        kq = getSharedPreferences("biblia", MODE_PRIVATE);
        dzenNoch = kq.getBoolean("dzen_noch", false);
        if (dzenNoch)
            setTheme(R.style.AppCompatDark);
        setContentView(R.layout.activity_naviny);

        if (dzenNoch) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                Window window = getWindow();
                window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.setStatusBarColor(ContextCompat.getColor(this, R.color.colorPrimary_text));
                window.setNavigationBarColor(ContextCompat.getColor(this, R.color.colorPrimary_text));
            }
        }
        relative = findViewById(R.id.relative);
        textView = findViewById(R.id.title_toolbar);
        listView = findViewById(R.id.dynamic);
        listView.setAdapter(new navinyAdapter(naviny.this));
        listView.setOnItemClickListener(((parent, view, position, id) -> {
            if (SystemClock.elapsedRealtime() - mLastClickTime < 1000){
                return;
            }
            mLastClickTime = SystemClock.elapsedRealtime();
            ArrayList<String> re = arrayList.get(position);
            String htmlData = readerFile(new File(getFilesDir() + "/Site/" + re.get(0)));
            htmlData = "<link rel=\"stylesheet\" type=\"text/css\" href=\"carkva.css\" /><script src=\"jquery-3.4.1.min.js\"></script>" + htmlData;
            Intent intent = new Intent(this, navinyView.class);
            intent.putExtra("htmlData", htmlData);
            intent.putExtra("url", re.get(1));
            startActivity(intent);
        }));
        if (savedInstanceState != null) {
            fullscreenPage = savedInstanceState.getBoolean("fullscreen");
            if (savedInstanceState.getBoolean("getModule")) {
                findViewById(R.id.linear).setVisibility(View.VISIBLE);
            }
        }
        int naviny = kq.getInt("naviny", 0);
        mviewWeb = findViewById(R.id.viewWeb);
        mviewWeb.getSettings().setJavaScriptEnabled(true);
        mviewWeb.getSettings().setDomStorageEnabled(true);
        mviewWeb.setWebViewClient(new MyWebViewClient());
        mviewWeb.setWebChromeClient(new WebChromeClient() {
            // For Android 4.1+
            @SuppressWarnings("unused")
            public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType, String capture) {
                mUploadMessage = uploadMsg;

                Intent i = new Intent(Intent.ACTION_GET_CONTENT);

                i.addCategory(Intent.CATEGORY_OPENABLE);
                i.setType(acceptType);

                startActivityForResult(Intent.createChooser(i, "SELECT"), 100);
            }

            // For Android 5.0+
            @SuppressLint("NewApi")
            public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> filePathCallback, FileChooserParams fileChooserParams) {
                if (mUploadMessageArr != null) {
                    mUploadMessageArr.onReceiveValue(null);
                    mUploadMessageArr = null;
                }
                mUploadMessageArr = filePathCallback;
                Intent intent = fileChooserParams.createIntent();
                try {
                    startActivityForResult(intent, 101);
                } catch (ActivityNotFoundException e) {
                    mUploadMessageArr = null;
                    return false;
                }
                return true;
            }
        });
        boolean error = false;
        switch (naviny) {
            case 0:
                if (MainActivity.isNetworkAvailable(this))
                    searchHistory.add("https://m.carkva-gazeta.by/");
                textView.setText("«Царква» — беларуская грэка-каталіцкая газета");//https://m.carkva-gazeta.by/
                File file = new File(getFilesDir() + "/Site/" + "http:__m.carkva-gazeta.by_");//
                if (MainActivity.isNetworkAvailable(this)) {
                    writeFile("https://m.carkva-gazeta.by/");
                }
                if (MainActivity.isNetworkAvailable(this)) {
                    mviewWeb.loadUrl("https://m.carkva-gazeta.by/");
                } else if (file.exists()) {
                    String htmlData = readerFile(file);
                    htmlData = "<link rel=\"stylesheet\" type=\"text/css\" href=\"carkva.css\" /><script src=\"jquery-3.4.1.min.js\"></script>" + htmlData;
                    mviewWeb.loadDataWithBaseURL("file:///android_asset/", htmlData, "text/html", "UTF-8", null);
                } else error = true;
                break;
            case 1:
                if (MainActivity.isNetworkAvailable(this))
                    searchHistory.add("https://m.carkva-gazeta.by/index.php?his=");
                textView.setText("Гісторыя Царквы");//https://m.carkva-gazeta.by/index.php?his=
                file = new File(getFilesDir() + "/Site/" + "http:__m.carkva-gazeta.by_index.php?his=");//
                if (MainActivity.isNetworkAvailable(this)) {
                    writeFile("https://m.carkva-gazeta.by/index.php?his=");
                }
                if (MainActivity.isNetworkAvailable(this)) {
                    mviewWeb.loadUrl("https://m.carkva-gazeta.by/index.php?his=");
                } else if (file.exists()) {
                    String htmlData = readerFile(file);
                    htmlData = "<link rel=\"stylesheet\" type=\"text/css\" href=\"carkva.css\" /><script src=\"jquery-3.4.1.min.js\"></script>" + htmlData;
                    mviewWeb.loadDataWithBaseURL("file:///android_asset/", htmlData, "text/html", "UTF-8", null);
                } else error = true;
                break;
            case 2:
                if (MainActivity.isNetworkAvailable(this))
                    searchHistory.add("https://m.carkva-gazeta.by/index.php?sva=");
                textView.setText("Сьвятло ўсходу");//https://m.carkva-gazeta.by/index.php?sva=
                file = new File(getFilesDir() + "/Site/" + "http:__m.carkva-gazeta.by_index.php?sva=");//
                if (MainActivity.isNetworkAvailable(this)) {
                    writeFile("https://m.carkva-gazeta.by/index.php?sva=");
                }
                if (MainActivity.isNetworkAvailable(this)) {
                    mviewWeb.loadUrl("https://m.carkva-gazeta.by/index.php?sva=");
                } else if (file.exists()) {
                    String htmlData = readerFile(file);
                    htmlData = "<link rel=\"stylesheet\" type=\"text/css\" href=\"carkva.css\" /><script src=\"jquery-3.4.1.min.js\"></script>" + htmlData;
                    mviewWeb.loadDataWithBaseURL("file:///android_asset/", htmlData, "text/html", "UTF-8", null);
                } else error = true;
                break;
            case 3:
                if (MainActivity.isNetworkAvailable(this))
                    searchHistory.add("https://m.carkva-gazeta.by/index.php?gra=");
                textView.setText("Царква і грамадзтва");//https://m.carkva-gazeta.by/index.php?gra=
                file = new File(getFilesDir() + "/Site/" + "http:__m.carkva-gazeta.by_index.php?gra=");
                if (MainActivity.isNetworkAvailable(this)) {
                    writeFile("https://m.carkva-gazeta.by/index.php?gra=");
                }
                if (MainActivity.isNetworkAvailable(this)) {
                    mviewWeb.loadUrl("https://m.carkva-gazeta.by/index.php?gra=");
                } else if (file.exists()) {
                    String htmlData = readerFile(file);
                    htmlData = "<link rel=\"stylesheet\" type=\"text/css\" href=\"carkva.css\" /><script src=\"jquery-3.4.1.min.js\"></script>" + htmlData;
                    mviewWeb.loadDataWithBaseURL("file:///android_asset/", htmlData, "text/html", "UTF-8", null);
                } else error = true;
                break;
            case 4:
                if (MainActivity.isNetworkAvailable(this))
                    searchHistory.add("https://m.carkva-gazeta.by/index.php?it=");
                textView.setText("Катэдральны пляц");//https://m.carkva-gazeta.by/index.php?it=
                file = new File(getFilesDir() + "/Site/" + "http:__m.carkva-gazeta.by_index.php?it=");
                if (MainActivity.isNetworkAvailable(this)) {
                    writeFile("https://m.carkva-gazeta.by/index.php?it=");
                }
                if (MainActivity.isNetworkAvailable(this)) {
                    mviewWeb.loadUrl("https://m.carkva-gazeta.by/index.php?it=");
                } else if (file.exists()) {
                    String htmlData = readerFile(file);
                    htmlData = "<link rel=\"stylesheet\" type=\"text/css\" href=\"carkva.css\" /><script src=\"jquery-3.4.1.min.js\"></script>" + htmlData;
                    mviewWeb.loadDataWithBaseURL("file:///android_asset/", htmlData, "text/html", "UTF-8", null);
                } else error = true;
                break;
            case 5:
                if (MainActivity.isNetworkAvailable(this))
                    searchHistory.add("https://m.carkva-gazeta.by/index.php?ik=");
                textView.setText("Відэа");//https://m.carkva-gazeta.by/index.php?ik=
                file = new File(getFilesDir() + "/Site/" + "http:__m.carkva-gazeta.by_index.php?ik=");
                if (MainActivity.isNetworkAvailable(this)) {
                    writeFile("https://m.carkva-gazeta.by/index.php?ik=");
                }
                if (MainActivity.isNetworkAvailable(this)) {
                    mviewWeb.loadUrl("https://m.carkva-gazeta.by/index.php?ik=");
                } else if (file.exists()) {
                    String htmlData = readerFile(file);
                    htmlData = "<link rel=\"stylesheet\" type=\"text/css\" href=\"carkva.css\" /><script src=\"jquery-3.4.1.min.js\"></script>" + htmlData;
                    mviewWeb.loadDataWithBaseURL("file:///android_asset/", htmlData, "text/html", "UTF-8", null);
                } else error = true;
                break;
        }
        if (error) {
            error();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 150 && resultCode == RESULT_OK) {
            MainActivity.downloadDynamicModule(this);
        }
        if (requestCode == 100) {
            if (mUploadMessage == null) return;
            Uri result = data == null || resultCode != Activity.RESULT_OK ? null : data.getData();
            mUploadMessage.onReceiveValue(result);
            mUploadMessage = null;
        } else if (requestCode == 101) {
            if (mUploadMessageArr == null) return;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                mUploadMessageArr.onReceiveValue(WebChromeClient.FileChooserParams.parseResult(resultCode, data));
            }
            mUploadMessageArr = null;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuInflater infl = getMenuInflater();
        infl.inflate(R.menu.site, menu);
        for (int i = 0; i < menu.size(); i++) {
            MenuItem item = menu.getItem(i);
            SpannableString spanString = new SpannableString(menu.getItem(i).getTitle().toString());
            int end = spanString.length();
            spanString.setSpan(new AbsoluteSizeSpan(SettingsActivity.GET_FONT_SIZE_MIN, true), 0, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            item.setTitle(spanString);
        }
        return true;
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean("fullscreen", fullscreenPage);
        if (findViewById(R.id.linear).getVisibility() == View.VISIBLE) {
            outState.putBoolean("getModule", true);
        } else {
            outState.putBoolean("getModule", false);
        }
    }

    private void hide() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        mHideHandler.removeCallbacks(mShowPart2Runnable);
        mHideHandler.postDelayed(mHidePart2Runnable, UI_ANIMATION_DELAY);
    }

    private void show() {
        relative.setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
        mHideHandler.removeCallbacks(mHidePart2Runnable);
        mHideHandler.postDelayed(mShowPart2Runnable, UI_ANIMATION_DELAY);
    }

    @Override
    protected void onResume() {
        super.onResume();
        setTollbarTheme();
        if (!MainActivity.checkBrightness) {
            WindowManager.LayoutParams lp = getWindow().getAttributes();
            lp.screenBrightness = (float) MainActivity.brightness / 100;
            getWindow().setAttributes(lp);
        }
        if (fullscreenPage) hide();
        overridePendingTransition(R.anim.alphain, R.anim.alphaout);
    }

    private void writeFile(final String url) {
        if (Objects.requireNonNull(Uri.parse(url).getHost()).endsWith("m.carkva-gazeta.by")) {
            Thread myThread = new Thread(() -> {
                try {
                    URL myUrl = new URL(url);
                    String filename = url;
                    filename = filename.replace("/", "_");
                    InputStream inpstr = myUrl.openStream();
                    File file = new File(getFilesDir() + "/Site/" + filename);
                    FileOutputStream outputStream = new FileOutputStream(getFilesDir() + "/Site/" + filename);
                    byte[] buffer = new byte[1024];
                    int bytesRead;
                    while ((bytesRead = inpstr.read(buffer)) != -1) {
                        outputStream.write(buffer, 0, bytesRead);
                    }
                    outputStream.close();
                    String htmlData = readerFile(file);
                    if (htmlData.contains("iframe")) {
                        int r1 = htmlData.indexOf("<iframe");
                        int r2 = htmlData.indexOf("</iframe>", r1 + 7);
                        String s2 = htmlData.substring(r1, r2 + 9);
                        if (!s2.contains("https://")) {
                            s2 = s2.replace("//", "https://");
                        }
                        String s1 = htmlData.substring(0, r1);
                        String s3 = htmlData.substring(r2 + 9);
                        File fileNew = new File(getFilesDir() + "/Site/" + filename);
                        FileWriter output;
                        output = new FileWriter(fileNew);
                        output.write(s1 + s2 + s3);
                        output.close();
                    }
                } catch (IOException ignored) {
                }
            });
            myThread.start();
        }
    }

    private String readerFile(File file) {
        StringBuilder builder = new StringBuilder();
        try {
            FileReader inputStream = new FileReader(file);
            BufferedReader reader = new BufferedReader(inputStream);
            String line;
            builder = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                builder.append(line).append("\n");
            }
            inputStream.close();
        } catch (Throwable ignored) {
        }
        return builder.toString();
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        if (errorInternet) {
            menu.findItem(R.id.num).setVisible(false);
            menu.findItem(R.id.sva).setVisible(false);
            menu.findItem(R.id.his).setVisible(false);
            menu.findItem(R.id.gra).setVisible(false);
            menu.findItem(R.id.calendar).setVisible(false);
            menu.findItem(R.id.biblia).setVisible(false);
            menu.findItem(R.id.it).setVisible(false);
            menu.findItem(R.id.ik).setVisible(false);
            menu.findItem(R.id.bib).setVisible(false);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        boolean error = false;
        if (id == R.id.num) {
            if (MainActivity.isNetworkAvailable(this))
                searchHistory.add("https://m.carkva-gazeta.by/index.php?num=");
            textView.setText("Навіны");//https://m.carkva-gazeta.by/
            File file = new File(getFilesDir() + "/Site/" + "http:__m.carkva-gazeta.by_index.php?num=");//
            if (MainActivity.isNetworkAvailable(this)) {
                writeFile("https://m.carkva-gazeta.by/index.php?num=");
            }
            if (MainActivity.isNetworkAvailable(this)) {
                mviewWeb.loadUrl("https://m.carkva-gazeta.by/index.php?num=");
            } else if (file.exists()) {
                String htmlData = readerFile(file);
                htmlData = "<link rel=\"stylesheet\" type=\"text/css\" href=\"carkva.css\" /><script src=\"jquery-3.4.1.min.js\"></script>" + htmlData;
                mviewWeb.loadDataWithBaseURL("file:///android_asset/", htmlData, "text/html", "UTF-8", null);
            } else error = true;
        }
        if (id == R.id.sva) {
            if (MainActivity.isNetworkAvailable(this))
                searchHistory.add("https://m.carkva-gazeta.by/index.php?sva=");
            textView.setText("Сьвятло ўсходу");//https://m.carkva-gazeta.by/index.php?sva=
            File file = new File(getFilesDir() + "/Site/" + "http:__m.carkva-gazeta.by_index.php?sva=");//
            if (MainActivity.isNetworkAvailable(this)) {
                writeFile("https://m.carkva-gazeta.by/index.php?sva=");
            }
            if (MainActivity.isNetworkAvailable(this)) {
                mviewWeb.loadUrl("https://m.carkva-gazeta.by/index.php?sva=");
            } else if (file.exists()) {
                String htmlData = readerFile(file);
                htmlData = "<link rel=\"stylesheet\" type=\"text/css\" href=\"carkva.css\" /><script src=\"jquery-3.4.1.min.js\"></script>" + htmlData;
                mviewWeb.loadDataWithBaseURL("file:///android_asset/", htmlData, "text/html", "UTF-8", null);
            } else error = true;
        }
        if (id == R.id.his) {
            if (MainActivity.isNetworkAvailable(this))
                searchHistory.add("https://m.carkva-gazeta.by/index.php?his=");
            textView.setText("Гісторыя Царквы");//https://m.carkva-gazeta.by/index.php?his=
            File file = new File(getFilesDir() + "/Site/" + "http:__m.carkva-gazeta.by_index.php?his=");//
            if (MainActivity.isNetworkAvailable(this)) {
                writeFile("https://m.carkva-gazeta.by/index.php?his=");
            }
            if (MainActivity.isNetworkAvailable(this)) {
                mviewWeb.loadUrl("https://m.carkva-gazeta.by/index.php?his=");
            } else if (file.exists()) {
                String htmlData = readerFile(file);
                htmlData = "<link rel=\"stylesheet\" type=\"text/css\" href=\"carkva.css\" /><script src=\"jquery-3.4.1.min.js\"></script>" + htmlData;
                mviewWeb.loadDataWithBaseURL("file:///android_asset/", htmlData, "text/html", "UTF-8", null);
            } else error = true;
        }
        if (id == R.id.gra) {
            if (MainActivity.isNetworkAvailable(this))
                searchHistory.add("https://m.carkva-gazeta.by/index.php?gra=");
            textView.setText("Царква і грамадзтва");//https://m.carkva-gazeta.by/index.php?gra=
            File file = new File(getFilesDir() + "/Site/" + "http:__m.carkva-gazeta.by_index.php?gra=");
            if (MainActivity.isNetworkAvailable(this)) {
                writeFile("https://m.carkva-gazeta.by/index.php?gra=");
            }
            if (MainActivity.isNetworkAvailable(this)) {
                mviewWeb.loadUrl("https://m.carkva-gazeta.by/index.php?gra=");
            } else if (file.exists()) {
                String htmlData = readerFile(file);
                htmlData = "<link rel=\"stylesheet\" type=\"text/css\" href=\"carkva.css\" /><script src=\"jquery-3.4.1.min.js\"></script>" + htmlData;
                mviewWeb.loadDataWithBaseURL("file:///android_asset/", htmlData, "text/html", "UTF-8", null);
            } else error = true;
        }
        if (id == R.id.calendar) {
            SharedPreferences.Editor prefEditors = kq.edit();
            prefEditors.putInt("id", R.id.label1);
            prefEditors.apply();
            Intent intent = new Intent(naviny.this, MainActivity.class);
            startActivity(intent);
        }
        if (id == R.id.biblia) {
            SharedPreferences.Editor prefEditors = kq.edit();
            prefEditors.putInt("id", R.id.label8);
            prefEditors.apply();
            Intent intent = new Intent(naviny.this, MainActivity.class);
            startActivity(intent);
        }
        if (id == R.id.it) {
            if (MainActivity.isNetworkAvailable(this))
                searchHistory.add("https://m.carkva-gazeta.by/index.php?it=");
            textView.setText("Катэдральны пляц");//https://m.carkva-gazeta.by/index.php?it=
            File file = new File(getFilesDir() + "/Site/" + "http:__m.carkva-gazeta.by_index.php?it=");
            if (MainActivity.isNetworkAvailable(this)) {
                writeFile("https://m.carkva-gazeta.by/index.php?it=");
            }
            if (MainActivity.isNetworkAvailable(this)) {
                mviewWeb.loadUrl("https://m.carkva-gazeta.by/index.php?it=");
            } else if (file.exists()) {
                String htmlData = readerFile(file);
                htmlData = "<link rel=\"stylesheet\" type=\"text/css\" href=\"carkva.css\" /><script src=\"jquery-3.4.1.min.js\"></script>" + htmlData;
                mviewWeb.loadDataWithBaseURL("file:///android_asset/", htmlData, "text/html", "UTF-8", null);
            } else error = true;
        }
        if (id == R.id.ik) {
            if (MainActivity.isNetworkAvailable(this))
                searchHistory.add("https://m.carkva-gazeta.by/index.php?ik=");
            textView.setText("Відэа");//https://m.carkva-gazeta.by/index.php?ik=
            File file = new File(getFilesDir() + "/Site/" + "http:__m.carkva-gazeta.by_index.php?ik=");
            if (MainActivity.isNetworkAvailable(this)) {
                writeFile("https://m.carkva-gazeta.by/index.php?ik=");
            }
            if (MainActivity.isNetworkAvailable(this)) {
                mviewWeb.loadUrl("https://m.carkva-gazeta.by/index.php?ik=");
            } else if (file.exists()) {
                String htmlData = readerFile(file);
                htmlData = "<link rel=\"stylesheet\" type=\"text/css\" href=\"carkva.css\" /><script src=\"jquery-3.4.1.min.js\"></script>" + htmlData;
                mviewWeb.loadDataWithBaseURL("file:///android_asset/", htmlData, "text/html", "UTF-8", null);
            } else error = true;
        }
        if (id == R.id.bib) {
            if (MainActivity.checkModule_resources(this)) {
                if (MainActivity.checkModules_biblijateka(this)) {
                    try {
                        Intent intent = new Intent(this, Class.forName("by.carkva_gazeta.biblijateka.bibliotekaView"));
                        intent.putExtra("site", true);
                        startActivity(intent);
                    } catch (ClassNotFoundException ignored) {
                    }
                } else {
                    MainActivity.downloadDynamicModule(this);
                }
            } else {
                Dialog_install_Dadatak dadatak = new Dialog_install_Dadatak();
                dadatak.show(getSupportFragmentManager(), "dadatak");
            }
        }
        if (id == android.R.id.home) {
            onBackPressed();
            return true;
        }
        if (id == R.id.action_fullscreen) {
            if (kq.getBoolean("FullscreenHelp", true)) {
                Dialog_Help_Fullscreen dialog_help_fullscreen = new Dialog_Help_Fullscreen();
                dialog_help_fullscreen.show(getSupportFragmentManager(), "FullscreenHelp");
            }
            fullscreenPage = true;
            hide();
        }
        if (error) {
            error();
        }
        return super.onOptionsItemSelected(item);
    }

    private class MyWebViewClient extends WebViewClient {

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            String title = view.getTitle();
            textView.setText(title);
            if (MainActivity.isNetworkAvailable(naviny.this)) {
                writeFile(url);
            }
        }

        @SuppressWarnings("ResultOfMethodCallIgnored")
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            if (url.contains("https://malitounik.page.link/caliandar")) {
                SharedPreferences.Editor prefEditors = kq.edit();
                prefEditors.putInt("id", R.id.label1);
                prefEditors.apply();
                Intent intent = new Intent(naviny.this, MainActivity.class);
                startActivity(intent);
                return false;
            }
            if (url.contains("https://malitounik.page.link/biblija")) {
                SharedPreferences.Editor prefEditors = kq.edit();
                prefEditors.putInt("id", R.id.label8);
                prefEditors.apply();
                Intent intent = new Intent(naviny.this, MainActivity.class);
                startActivity(intent);
                return false;
            }
            if (url.contains("https://m.carkva-gazeta.by/index.php?bib=")) {
                if (MainActivity.checkModule_resources(naviny.this)) {
                    if (MainActivity.checkModules_biblijateka(naviny.this)) {
                        try {
                            Intent intent = new Intent(naviny.this, Class.forName("by.carkva_gazeta.biblijateka.bibliotekaView"));
                            intent.putExtra("site", true);
                            startActivity(intent);
                        } catch (ClassNotFoundException ignored) {
                        }
                    } else {
                        MainActivity.downloadDynamicModule(naviny.this);
                    }
                    return false;
                }
            }
            boolean error = false;
            try {
                if (!Objects.requireNonNull(Uri.parse(url).getHost()).endsWith("m.carkva-gazeta.by")) {
                    if (MainActivity.isNetworkAvailable(naviny.this)) {
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                        view.getContext().startActivity(intent);
                        return true;
                    } else error = true;
                }
                mUrl = url;
                if (MainActivity.isNetworkAvailable(naviny.this))
                    searchHistory.add(url);
                String filename = url;
                filename = filename.replace("/", "_");
                File file = new File(getFilesDir() + "/Site/" + filename);
                if (view.getUrl().equals("https://m.carkva-gazeta.by/")) {
                    if (MainActivity.isNetworkAvailable(naviny.this)) {
                        view.loadUrl(url);
                    } else if (file.exists()) {
                        String htmlData = readerFile(file);
                        htmlData = "<link rel=\"stylesheet\" type=\"text/css\" href=\"carkva.css\" /><script src=\"jquery-3.4.1.min.js\"></script>" + htmlData;
                        mviewWeb.loadDataWithBaseURL("file:///android_asset/", htmlData, "text/html", "UTF-8", null);
                    } else error = true;
                } else {
                    if (kq.getInt("trafic", 0) == 0) {
                        if (MainActivity.isNetworkAvailable(naviny.this)) {
                            view.loadUrl(url);
                        } else if (file.exists()) {
                            String htmlData = readerFile(file);
                            htmlData = "<link rel=\"stylesheet\" type=\"text/css\" href=\"carkva.css\" /><script src=\"jquery-3.4.1.min.js\"></script>" + htmlData;
                            mviewWeb.loadDataWithBaseURL("file:///android_asset/", htmlData, "text/html", "UTF-8", null);
                        } else error = true;
                    }
                    if (kq.getInt("trafic", 0) == 1) {
                        if (file.exists()) {
                            String htmlData = readerFile(file);
                            htmlData = "<link rel=\"stylesheet\" type=\"text/css\" href=\"carkva.css\" /><script src=\"jquery-3.4.1.min.js\"></script>" + htmlData;
                            mviewWeb.loadDataWithBaseURL("file:///android_asset/", htmlData, "text/html", "UTF-8", null);
                        } else if (MainActivity.isNetworkAvailable(naviny.this)) {
                            view.loadUrl(url);
                        } else error = true;
                    }
                }
                if (error) {
                    error();
                }
            } catch (ActivityNotFoundException e) {
                File dir1 = new File(getFilesDir() + "/Site");
                String[] dirContents1 = dir1.list();
                for (String aDirContents1 : Objects.requireNonNull(dirContents1)) {
                    new File(getFilesDir() + "/Site/" + aDirContents1).delete();
                }
            }
            return true;
        }
    }

    private void error() {
        errorInternet = true;
        supportInvalidateOptionsMenu();
        arrayList.clear();
        File dir = new File(getFilesDir() + "/Site");
        ArrayList<File> files = new ArrayList<>();
        for (int i = 0; i < Objects.requireNonNull(dir.list()).length; i++) {
            files.add(new File(getFilesDir() + "/Site/" + Objects.requireNonNull(dir.list())[i]));
        }
        Collections.sort(files, (o1, o2) -> Long.compare(o1.lastModified(), o2.lastModified()));
        Collections.reverse(files);
        for (File file1 : files) {
            String res;
            String htmlData = readerFile(file1);
            int seaN = htmlData.indexOf("<title>");
            int seaK = htmlData.indexOf("</title>");
            res = htmlData.substring(seaN + 7, seaK).trim();
            ArrayList<String> arrayList1 = new ArrayList<>();
            arrayList1.add(file1.getName());
            arrayList1.add(res);
            arrayList.add(arrayList1);
        }
        listView.setVisibility(View.VISIBLE);
        mviewWeb.setVisibility(View.GONE);
        textView.setText(R.string.keshstar);
        Dialog_no_internet dialog_no_internet = new Dialog_no_internet();
        dialog_no_internet.show(getSupportFragmentManager(), "no_internet");
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
            toolbar.setPopupTheme(by.carkva_gazeta.malitounik.R.style.AppCompatDark);
            toolbar.setBackgroundResource(by.carkva_gazeta.malitounik.R.color.colorprimary_material_dark);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (fullscreenPage) {
            fullscreenPage = false;
            show();
        } else {
            if ((keyCode == KeyEvent.KEYCODE_BACK) && searchHistory.size() > 0) {
                searchHistory.remove(searchHistory.size() - 1);
                if (searchHistory.size() > 0) {
                    String filenameUrl = searchHistory.get(searchHistory.size() - 1);
                    String filename = filenameUrl.replace("/", "_");
                    File file = new File(getFilesDir() + "/Site/" + filename);
                    if (file.exists()) {
                        String htmlData = readerFile(file);
                        htmlData = "<link rel=\"stylesheet\" type=\"text/css\" href=\"carkva.css\" /><script src=\"jquery-3.4.1.min.js\"></script>" + htmlData;
                        mviewWeb.loadDataWithBaseURL("file:///android_asset/", htmlData, "text/html", "UTF-8", null);
                    } else {
                        mviewWeb.loadUrl(mUrl);
                    }
                } else {
                    onBackPressed();
                }
            } else {
                onBackPressed();
            }
        }
        return true;
    }

    class navinyAdapter extends ArrayAdapter<ArrayList<String>> {

        navinyAdapter(Context context) {
            super(context, R.layout.simple_list_item_2, arrayList);
        }

        @NonNull
        @Override
        public View getView(int position, View convertView, @NonNull ViewGroup parent) {
            ViewHolder viewHolder;
            if (convertView == null) {
                convertView = naviny.this.getLayoutInflater().inflate(R.layout.simple_list_item_2, parent, false);
                viewHolder = new ViewHolder();
                convertView.setTag(viewHolder);
                viewHolder.text = convertView.findViewById(R.id.label);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            ArrayList<String> re = arrayList.get(position);
            ArrayMap<String, String> mnemonics = new ArrayMap<>();
            mnemonics.put("&amp;", "\u0026");
            mnemonics.put("&lt;", "\u003C");
            mnemonics.put("&gt;", "\u003E");
            mnemonics.put("&laquo;", "\u00AB");
            mnemonics.put("&raquo;", "\u00BB");
            mnemonics.put("&nbsp;", "\u0020");
            mnemonics.put("&mdash;", "\u0020-\u0020");

            String output = re.get(1);
            for (String key : mnemonics.keySet()) {
                Matcher matcher = Pattern.compile(key).matcher(output);
                output = matcher.replaceAll(Objects.requireNonNull(mnemonics.get(key)));
            }
            if (dzenNoch) viewHolder.text.setTextColor(ContextCompat.getColor(naviny.this, R.color.colorIcons));
            viewHolder.text.setTextSize(TypedValue.COMPLEX_UNIT_SP, SettingsActivity.GET_FONT_SIZE_MIN);
            viewHolder.text.setText(output);
            return convertView;
        }
    }

    private static class ViewHolder {
        TextView_Roboto_Condensed text;
    }
}
