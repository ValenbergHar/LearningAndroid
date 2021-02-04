package by.carkva_gazeta.biblijateka;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.provider.MediaStore;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.util.Base64;
import android.util.TypedValue;
import android.view.Display;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.Surface;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.listener.OnErrorListener;
import com.github.barteksc.pdfviewer.listener.OnLoadCompleteListener;
import com.github.barteksc.pdfviewer.listener.OnPageChangeListener;
import com.github.barteksc.pdfviewer.util.FitPolicy;
import com.google.android.play.core.splitcompat.SplitCompat;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.kursx.parser.fb2.Binary;
import com.kursx.parser.fb2.Element;
import com.kursx.parser.fb2.EmptyLine;
import com.kursx.parser.fb2.FictionBook;
import com.kursx.parser.fb2.Image;
import com.kursx.parser.fb2.P;
import com.kursx.parser.fb2.Section;
import com.kursx.parser.fb2.Title;
import com.shockwave.pdfium.PdfDocument;

import org.json.JSONArray;
import org.json.JSONObject;
import org.xml.sax.SAXException;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import javax.xml.parsers.ParserConfigurationException;

import by.carkva_gazeta.malitounik.Dialog_Help_Fullscreen;
import by.carkva_gazeta.malitounik.Dialog_brightness;
import by.carkva_gazeta.malitounik.Dialog_delite;
import by.carkva_gazeta.malitounik.Dialog_font_size;
import by.carkva_gazeta.malitounik.Dialog_no_internet;
import by.carkva_gazeta.malitounik.Dialog_pdf_error;
import by.carkva_gazeta.malitounik.MainActivity;
import by.carkva_gazeta.malitounik.SettingsActivity;
import by.carkva_gazeta.malitounik.TextView_Roboto_Condensed;
import by.carkva_gazeta.malitounik.WebViewCustom;

@SuppressWarnings("ResultOfMethodCallIgnored")
@SuppressLint("DefaultLocale")
public class bibliotekaView extends AppCompatActivity implements OnPageChangeListener, OnLoadCompleteListener, Dialog_set_page_biblioteka.Dialog_set_page_biblioteka_Listener, Dialog_title_biblioteka.Dialog_title_biblioteka_Listener, OnErrorListener, Dialog_file_explorer.Dialog_file_explorer_Listener, View.OnClickListener, Dialog_biblioteka_WI_FI.Dialog_biblioteka_WI_FI_Listener, Dialog_bibliateka.Dialog_bibliateka_Listener, Dialog_delite.Dialog_delite_Listener, Dialog_font_size.Dialog_font_size_Listener, WebViewCustom.OnScrollChangedCallback, WebViewCustom.OnBottomListener {

    private static boolean firstLiadSql = true;
    private static final int UI_ANIMATION_DELAY = 300;
    private final Handler mHideHandler = new Handler();
    @SuppressLint("InlinedApi")
    private final Runnable mHidePart2Runnable = () -> {
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
    };
    private final Runnable mShowPart2Runnable = () -> {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.show();
        }
    };

    private long mLastClickTime = 0;
    private final int MY_PERMISSIONS_WRITE_EXTERNAL_STORAGE = 39;
    private boolean fullscreenPage = false;
    private SharedPreferences k;
    private PDFView pdfView;
    private boolean dzenNoch;
    private String filePath = "", fileName = "";
    private TextView_Roboto_Condensed text_view_page;
    private TextView_Roboto_Condensed title_toolbar;
    private final ArrayList<String> bookTitle = new ArrayList<>();
    private boolean menu = false;
    private TextView_Roboto_Condensed label1;
    private TextView_Roboto_Condensed label2;
    private TextView_Roboto_Condensed label3;
    private TextView_Roboto_Condensed label4;
    private TextView_Roboto_Condensed label5;
    private TextView_Roboto_Condensed label6;
    private PopupMenu popup;
    private final ArrayList<ArrayList<String>> arrayList = new ArrayList<>();
    private int width;
    private String filename;
    private ProgressBar progress;
    private bibliotekaAdapter adapter;
    private ListView listView;
    private String nameRubrika = "";
    private int default_page = 0;
    private DrawerLayout drawer;
    private int idSelect = by.carkva_gazeta.malitounik.R.id.label1;
    private final ArrayList<ArrayList<String>> naidaunia = new ArrayList<>();
    private boolean saveindep = true;
    private boolean runSql = false;
    private View onClickView;
    private WebViewCustom webView;
    private bibliotekaEpub biblioteka;
    private int positionY = 0;
    private FictionBook fb2;
    private String fb2PageText;
    private int spid = 60;
    private Timer scrollTimer = null;
    private Timer procentTimer = null;
    private Timer toNextPageTimer = null;
    private TimerTask scrollerSchedule;
    private TimerTask procentSchedule;
    private TimerTask toNextPageSchedule;
    private Timer resetTimer = null;
    private TextView_Roboto_Condensed prog;
    private boolean autoscroll = false;
    private GregorianCalendar g;
    private Animation anim_in_right, anim_out_right, anim_in_left, anim_out_left;
    private boolean animationStoronaLeft = true;
    private boolean site = false;
    private boolean mActionDown;
    private final Animation.AnimationListener animationListenerOutRight = new Animation.AnimationListener() {
        @Override
        public void onAnimationStart(Animation animation) {
        }

        @Override
        public void onAnimationEnd(Animation animation) {
            if (biblioteka != null) {
                int t1 = fileName.lastIndexOf(".");
                String dirName = fileName.substring(0, t1);
                File dir = new File(getFilesDir() + "/Book/" + dirName + "/");
                if (default_page - 1 >= 0) {
                    default_page--;
                    webView.loadUrl("file://" + dir.getAbsolutePath() + "/" + biblioteka.getPageName(default_page));
                }
            } else {
                if (default_page - 1 >= 0) {
                    default_page--;
                    fb2PageText = getFB2Page();
                    webView.loadDataWithBaseURL(null, fb2PageText, "text/html; charset=utf-8", "utf-8", null);
                }
            }
            if (autoscroll)
                NextPageAutoScroll();
        }

        @Override
        public void onAnimationRepeat(Animation animation) {
        }
    };
    private final Animation.AnimationListener animationListenerOutLeft = new Animation.AnimationListener() {
        @Override
        public void onAnimationStart(Animation animation) {
        }

        @Override
        public void onAnimationEnd(Animation animation) {
            if (biblioteka != null) {
                int t1 = fileName.lastIndexOf(".");
                String dirName = fileName.substring(0, t1);
                File dir = new File(getFilesDir() + "/Book/" + dirName + "/");
                if (default_page + 1 < biblioteka.getContent().size()) {
                    default_page++;
                    webView.loadUrl("file://" + dir.getAbsolutePath() + "/" + biblioteka.getPageName(default_page));
                }
            } else {
                if (default_page + 1 < bookTitle.size()) {
                    default_page++;
                    fb2PageText = getFB2Page();
                    webView.loadDataWithBaseURL(null, fb2PageText, "text/html; charset=utf-8", "utf-8", null);
                }
            }
            if (autoscroll)
                NextPageAutoScroll();
        }

        @Override
        public void onAnimationRepeat(Animation animation) {
        }
    };

    @Override
    public void onScroll(int t) {
        positionY = t;
    }

    @Override
    public void onBottom() {
        stopAutoScroll();
        toNextPage();
    }

    @Override
    public void onDialogFontSizePositiveClick() {
        int fontBiblia = k.getInt("font_malitounik", SettingsActivity.GET_DEFAULT_FONT_SIZE);
        WebSettings webSettings = webView.getSettings();
        webSettings.setDefaultFontSize(fontBiblia);
    }

    @Override
    public void file_delite(int position, String filename) {
        File file = new File(getFilesDir() + "/Biblijateka/" + filename);
        if (file.exists()) {
            file.delete();
        }
        if (popup != null)
            popup.getMenu().getItem(2).setVisible(false);
    }

    @Override
    public void onDialogPositiveClick(String listPosition) {
        if (MainActivity.isIntNetworkAvailable(this) == 0) {
            Dialog_no_internet dialog_no_internet = new Dialog_no_internet();
            dialog_no_internet.show(getSupportFragmentManager(), "no_internet");
        } else {
            writeFile("https://carkva-gazeta.by/data/bibliateka/" + listPosition);
        }
    }

    @Override
    public void onDialogbibliatekaPositiveClick(String listPosition, String title) {
        File file = new File(this.getFilesDir() + "/Biblijateka/" + listPosition);
        if (file.exists()) {
            filePath = this.getFilesDir() + "/Biblijateka/" + listPosition;
            fileName = title;
            loadFilePDF();
            listView.setVisibility(View.GONE);
            webView.setVisibility(View.GONE);
            pdfView.setVisibility(View.VISIBLE);
            supportInvalidateOptionsMenu();
        } else {
            if (MainActivity.isIntNetworkAvailable(this) == 2) {
                Dialog_biblioteka_WI_FI biblioteka_wi_fi = Dialog_biblioteka_WI_FI.getInstance(listPosition);
                biblioteka_wi_fi.show(getSupportFragmentManager(), "biblioteka_WI_FI");
            } else {
                writeFile("https://carkva-gazeta.by/data/bibliateka/" + listPosition);
            }
        }
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    private void writeFile(String url) {
        progress.setVisibility(View.VISIBLE);
        new Thread(() -> {
            try {
                File dir = new File(this.getFilesDir() + "/Biblijateka");
                if (!dir.exists()) {
                    dir.mkdir();
                }
                URL myUrl = new URL(url);
                int last = url.lastIndexOf("/");
                filename = url.substring(last + 1);
                InputStream inpstr = myUrl.openStream();
                File file = new File(this.getFilesDir() + "/Biblijateka/" + filename);
                FileOutputStream outputStream = new FileOutputStream(file);
                byte[] buffer = new byte[1024];
                int bytesRead;
                while ((bytesRead = inpstr.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, bytesRead);
                }
                outputStream.close();
            } catch (IOException ignored) {
            }
            runOnUiThread(() -> {
                adapter.notifyDataSetChanged();
                progress.setVisibility(View.GONE);
                filePath = this.getFilesDir() + "/Biblijateka/" + filename;
                fileName = filename;
                loadFilePDF();
                listView.setVisibility(View.GONE);
                webView.setVisibility(View.GONE);
                pdfView.setVisibility(View.VISIBLE);
                supportInvalidateOptionsMenu();
            });
        }).start();
    }

    @Override
    public void onError(Throwable t) {
        Dialog_pdf_error pdf_error = Dialog_pdf_error.getInstance("PDF");
        pdf_error.show(getSupportFragmentManager(), "pdf_error");
    }

    private int getOrientation() {
        int rotation = getWindowManager().getDefaultDisplay().getRotation();
        int displayOrientation = getResources().getConfiguration().orientation;

        if (displayOrientation == Configuration.ORIENTATION_LANDSCAPE) {
            if (rotation == Surface.ROTATION_270 || rotation == Surface.ROTATION_180)
                return ActivityInfo.SCREEN_ORIENTATION_REVERSE_LANDSCAPE;

            return ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE;
        } else if (rotation == Surface.ROTATION_180 || rotation == Surface.ROTATION_90)
            return ActivityInfo.SCREEN_ORIENTATION_REVERSE_PORTRAIT;

        return ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;
    }

    @Override
    public void onDialogFile(File file) {
        onClick(label1);
        filePath = file.getAbsolutePath();
        fileName = file.getName();
        if (fileName.toLowerCase().contains(".fb2.zip")) {
            loadFileFB2ZIP();
        } else if (fileName.toLowerCase().contains(".fb2")) {
            loadFileFB2();
        } else if (fileName.toLowerCase().contains(".pdf")) {
            loadFilePDF();
            pdfView.setVisibility(View.VISIBLE);
        } else {
            loadFileEPUB();
        }
        listView.setVisibility(View.GONE);
        supportInvalidateOptionsMenu();
    }

    private void ajustCompoundDrawableSizeWithText(final TextView_Roboto_Condensed textView, final Drawable leftDrawable) {
        textView.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                if (leftDrawable != null) {
                    leftDrawable.setBounds(0, 0, (int) textView.getTextSize(), (int) textView.getTextSize());
                }
                textView.setCompoundDrawables(leftDrawable, null, null, null);
                textView.removeOnLayoutChangeListener(this);
            }
        });
    }

    @Override
    public void onDialogTitle(int page) {
        if (webView.getVisibility() == View.VISIBLE) {
            default_page = page;
            fb2PageText = getFB2Page();
            webView.loadDataWithBaseURL(null, fb2PageText, "text/html; charset=utf-8", "utf-8", null);
        } else {
            pdfView.jumpTo(page);
            text_view_page.setText(String.format("%d/%d", page + 1, pdfView.getPageCount()));
            text_view_page.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onDialogTitleString(String page) {
        int t1 = fileName.lastIndexOf(".");
        String dirName = fileName.substring(0, t1);
        File dir = new File(getFilesDir() + "/Book/" + dirName + "/");
        webView.loadUrl("file://" + dir.getAbsolutePath() + "/" + page);
        default_page = biblioteka.setPage(page);
        text_view_page.setVisibility(View.GONE);
    }

    @Override
    public void onDialogSetPage(int page) {
        if (pdfView.getVisibility() == View.VISIBLE) {
            pdfView.jumpTo(page - 1);
            text_view_page.setText(String.format("%d/%d", page, pdfView.getPageCount()));
        }
    }

    @Override
    public void onPageChanged(int page, int pageCount) {
        text_view_page.setText(String.format("%d/%d", page + 1, pageCount));
        text_view_page.setVisibility(View.VISIBLE);
    }

    @Override
    public void loadComplete(int nbPages) {
        bookTitle.clear();
        printBookmarksTree(pdfView.getTableOfContents());
        String title = pdfView.getDocumentMeta().getTitle();
        if (title.equals("")) {
            int t1 = filePath.lastIndexOf("/");
            title = filePath.substring(t1 + 1);
        }
        title_toolbar.setText(title);
        for (int i = 0; i < naidaunia.size(); i++) {
            if (naidaunia.get(i).get(1).contains(filePath)) {
                naidaunia.remove(i);
                break;
            }
        }
        Gson gson = new Gson();
        ArrayList<String> temp = new ArrayList<>();
        temp.add(title);
        temp.add(filePath);
        if (filePath.contains("/Biblijateka/")) {
            int t2 = filePath.lastIndexOf("/");
            String image = filePath.substring(t2 + 1);
            int t1 = image.lastIndexOf(".");
            //String image_local = getFilesDir() + "/image_temp/" + pdf.substring(0, t1) + ".png";
            temp.add(getFilesDir() + "/image_temp/" + image.substring(0, t1) + ".png");
        } else {
            temp.add("");
        }
        naidaunia.add(temp);
        SharedPreferences.Editor prefEditor;
        prefEditor = k.edit();
        prefEditor.putString("bibliateka_naidaunia", gson.toJson(naidaunia));
        prefEditor.apply();
        pdfView.setBackgroundResource(by.carkva_gazeta.malitounik.R.color.colorSecondary_text);
    }

    private void printBookmarksTree(List<PdfDocument.Bookmark> tree) {
        for (PdfDocument.Bookmark b : tree) {
            bookTitle.add(b.getPageIdx() + "<>" + b.getTitle());
            if (b.hasChildren()) {
                printBookmarksTree(b.getChildren());
            }
        }
        menu = bookTitle.size() != 0;
        supportInvalidateOptionsMenu();
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        width = size.x;
        if (!MainActivity.checkBrightness) {
            WindowManager.LayoutParams lp = getWindow().getAttributes();
            lp.screenBrightness = (float) MainActivity.brightness / 100;
            getWindow().setAttributes(lp);
        }
        k = getSharedPreferences("biblia", Context.MODE_PRIVATE);
        float fontBiblia = k.getInt("font_malitounik", SettingsActivity.GET_DEFAULT_FONT_SIZE);
        dzenNoch = k.getBoolean("dzen_noch", false);
        if (dzenNoch) setTheme(by.carkva_gazeta.malitounik.R.style.AppCompatDark);
        super.onCreate(savedInstanceState);
        setContentView(by.carkva_gazeta.malitounik.R.layout.biblioteka_view);

        prog = findViewById(by.carkva_gazeta.malitounik.R.id.progress);
        autoscroll = k.getBoolean("autoscroll", false);
        listView = findViewById(by.carkva_gazeta.malitounik.R.id.listView);
        adapter = new bibliotekaAdapter(this);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener((parent, view, position, id) -> {
            if (SystemClock.elapsedRealtime() - mLastClickTime < 1000) {
                return;
            }
            mLastClickTime = SystemClock.elapsedRealtime();
            File file;
            if (arrayList.get(position).size() == 3) {
                file = new File(arrayList.get(position).get(1));
                if (file.exists()) {
                    filePath = file.getAbsolutePath();
                    fileName = file.getName();
                    if (fileName.toLowerCase().contains(".pdf")) {
                        loadFilePDF();
                        pdfView.setVisibility(View.VISIBLE);
                    } else if (fileName.toLowerCase().contains(".fb2.zip")) {
                        loadFileFB2ZIP();
                    } else if (fileName.toLowerCase().contains(".fb2")) {
                        loadFileFB2();
                    } else {
                        loadFileEPUB();
                    }
                    if (!fileName.toLowerCase().contains(".pdf")) {
                        autoscroll = k.getBoolean("autoscroll", false);
                        spid = k.getInt("autoscrollSpid", 60);
                        if (autoscroll) {
                            startAutoScroll();
                        }
                    }
                    listView.setVisibility(View.GONE);
                    supportInvalidateOptionsMenu();
                } else {
                    if (arrayList.get(position).get(1).contains(".epub")) {
                        String res = arrayList.get(position).get(1);
                        int t1 = res.lastIndexOf(".epub");
                        int t2 = res.lastIndexOf("/");
                        File delite = new File(getFilesDir() + "/Book/" + res.substring(t2 + 1, t1) + "/");
                        if (delite.exists()) {
                            try {
                                delete(delite);
                            } catch (IOException ignored) {
                            }
                        }
                    }
                    arrayList.remove(position);
                    naidaunia.clear();
                    naidaunia.addAll(arrayList);

                    adapter.notifyDataSetChanged();
                    Gson gson = new Gson();
                    SharedPreferences.Editor prefEditor;
                    prefEditor = k.edit();
                    prefEditor.putString("bibliateka_naidaunia", gson.toJson(naidaunia));
                    prefEditor.apply();
                    LinearLayout layout = new LinearLayout(this);
                    if (dzenNoch)
                        layout.setBackgroundResource(by.carkva_gazeta.malitounik.R.color.colorPrimary_black);
                    else
                        layout.setBackgroundResource(by.carkva_gazeta.malitounik.R.color.colorPrimary);
                    float density = getResources().getDisplayMetrics().density;
                    int realpadding = (int) (10 * density);
                    TextView_Roboto_Condensed toast = new TextView_Roboto_Condensed(this);
                    toast.setTextColor(ContextCompat.getColor(this, by.carkva_gazeta.malitounik.R.color.colorIcons));
                    toast.setPadding(realpadding, realpadding, realpadding, realpadding);
                    toast.setText("Файл не існуе");
                    toast.setTextSize(TypedValue.COMPLEX_UNIT_SP, SettingsActivity.GET_FONT_SIZE_MIN - 2);
                    layout.addView(toast);
                    Toast mes = new Toast(this);
                    mes.setDuration(Toast.LENGTH_SHORT);
                    mes.setView(layout);
                    mes.show();
                }
            } else {
                file = new File(this.getFilesDir() + "/Biblijateka/" + arrayList.get(position).get(2));
                if (file.exists()) {
                    filePath = file.getAbsolutePath();
                    fileName = file.getName();
                    loadFilePDF();
                    listView.setVisibility(View.GONE);
                    webView.setVisibility(View.GONE);
                    pdfView.setVisibility(View.VISIBLE);
                    supportInvalidateOptionsMenu();
                } else {
                    Dialog_bibliateka dialog_bibliateka = Dialog_bibliateka.getInstance(arrayList.get(position).get(2), arrayList.get(position).get(1), arrayList.get(position).get(0), arrayList.get(position).get(3));
                    dialog_bibliateka.show(getSupportFragmentManager(), "dialog_bibliateka");
                }
            }
        });

        anim_in_right = AnimationUtils.loadAnimation(getBaseContext(), by.carkva_gazeta.malitounik.R.anim.slide_in_right);
        anim_out_right = AnimationUtils.loadAnimation(getBaseContext(), by.carkva_gazeta.malitounik.R.anim.slide_out_right);
        anim_in_left = AnimationUtils.loadAnimation(getBaseContext(), by.carkva_gazeta.malitounik.R.anim.slide_in_left);
        anim_out_left = AnimationUtils.loadAnimation(getBaseContext(), by.carkva_gazeta.malitounik.R.anim.slide_out_left);
        anim_out_right.setAnimationListener(animationListenerOutRight);
        anim_out_left.setAnimationListener(animationListenerOutLeft);
        positionY = k.getInt("webViewBibliotekaScroll", 0);
        webView = findViewById(by.carkva_gazeta.malitounik.R.id.webView);
        webView.setOnScrollChangedCallback(this);
        webView.setOnTouchListener(new OnSwipeTouchListener(this) {
            @Override
            public void onSwipeRight() {
                if (default_page - 1 >= 0) {
                    stopAutoScroll();
                    if (toNextPageTimer != null) {
                        toNextPageTimer.cancel();
                        toNextPageTimer = null;
                        toNextPageSchedule = null;
                    }
                    animationStoronaLeft = false;
                    webView.startAnimation(anim_out_right);
                }
            }

            @Override
            public void onSwipeLeft() {
                if (biblioteka != null) {
                    if (default_page + 1 < biblioteka.getContent().size()) {
                        stopAutoScroll();
                        if (toNextPageTimer != null) {
                            toNextPageTimer.cancel();
                            toNextPageTimer = null;
                            toNextPageSchedule = null;
                        }
                        animationStoronaLeft = true;
                        webView.startAnimation(anim_out_left);
                    }
                } else {
                    if (default_page + 1 < bookTitle.size()) {
                        stopAutoScroll();
                        if (toNextPageTimer != null) {
                            toNextPageTimer.cancel();
                            toNextPageTimer = null;
                            toNextPageSchedule = null;
                        }
                        animationStoronaLeft = true;
                        webView.startAnimation(anim_out_left);
                    }
                }
            }

            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (view.getId() == by.carkva_gazeta.malitounik.R.id.webView) {
                    switch (motionEvent.getAction()) {
                        case MotionEvent.ACTION_DOWN:
                            if (autoscroll)
                                mActionDown = true;
                            break;
                        case MotionEvent.ACTION_UP:
                        case MotionEvent.ACTION_CANCEL:
                            if (autoscroll)
                                mActionDown = false;
                            break;
                    }
                }
                return super.onTouch(view, motionEvent);
            }
        });
        webView.setOnLongClickListener((view) -> mActionDown);
        webView.setWebViewClient(new HelloWebViewClient());
        WebSettings webSettings = webView.getSettings();
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        webSettings.setStandardFontFamily("sans-serif-condensed");
        webSettings.setDefaultFontSize((int) fontBiblia);
        drawer = findViewById(by.carkva_gazeta.malitounik.R.id.drawer_layout);
        pdfView = findViewById(by.carkva_gazeta.malitounik.R.id.pdfView);
        progress = findViewById(by.carkva_gazeta.malitounik.R.id.progressBar2);
        title_toolbar = findViewById(by.carkva_gazeta.malitounik.R.id.title_toolbar);
        text_view_page = findViewById(by.carkva_gazeta.malitounik.R.id.page_toolbar);
        TextView_Roboto_Condensed title = findViewById(by.carkva_gazeta.malitounik.R.id.title);

        label1 = findViewById(by.carkva_gazeta.malitounik.R.id.label1);
        label1.setTextSize(TypedValue.COMPLEX_UNIT_SP, SettingsActivity.GET_FONT_SIZE_MIN);
        label2 = findViewById(by.carkva_gazeta.malitounik.R.id.label2);
        label2.setTextSize(TypedValue.COMPLEX_UNIT_SP, SettingsActivity.GET_FONT_SIZE_MIN);
        label3 = findViewById(by.carkva_gazeta.malitounik.R.id.label3);
        label3.setTextSize(TypedValue.COMPLEX_UNIT_SP, SettingsActivity.GET_FONT_SIZE_MIN);
        label4 = findViewById(by.carkva_gazeta.malitounik.R.id.label4);
        label4.setTextSize(TypedValue.COMPLEX_UNIT_SP, SettingsActivity.GET_FONT_SIZE_MIN);
        label5 = findViewById(by.carkva_gazeta.malitounik.R.id.label5);
        label5.setTextSize(TypedValue.COMPLEX_UNIT_SP, SettingsActivity.GET_FONT_SIZE_MIN);
        label6 = findViewById(by.carkva_gazeta.malitounik.R.id.label6);
        label6.setTextSize(TypedValue.COMPLEX_UNIT_SP, SettingsActivity.GET_FONT_SIZE_MIN);

        Drawable drawable = ContextCompat.getDrawable(this, by.carkva_gazeta.malitounik.R.drawable.krest);
        if (dzenNoch) {
            drawable = ContextCompat.getDrawable(this, by.carkva_gazeta.malitounik.R.drawable.krest_black);
            title.setTextColor(ContextCompat.getColor(this, by.carkva_gazeta.malitounik.R.color.colorPrimary_black));
            title.setBackgroundColor(ContextCompat.getColor(this, by.carkva_gazeta.malitounik.R.color.colorbackground_material_dark));
            label1.setBackgroundColor(ContextCompat.getColor(this, by.carkva_gazeta.malitounik.R.color.colorbackground_material_dark));
            label2.setBackgroundColor(ContextCompat.getColor(this, by.carkva_gazeta.malitounik.R.color.colorbackground_material_dark));
            label3.setBackgroundColor(ContextCompat.getColor(this, by.carkva_gazeta.malitounik.R.color.colorbackground_material_dark));
            label4.setBackgroundColor(ContextCompat.getColor(this, by.carkva_gazeta.malitounik.R.color.colorbackground_material_dark));
            label5.setBackgroundColor(ContextCompat.getColor(this, by.carkva_gazeta.malitounik.R.color.colorbackground_material_dark));
            label6.setBackgroundColor(ContextCompat.getColor(this, by.carkva_gazeta.malitounik.R.color.colorbackground_material_dark));
        } else {
            title.setBackgroundColor(ContextCompat.getColor(this, by.carkva_gazeta.malitounik.R.color.colorIcons));
            label1.setBackgroundColor(ContextCompat.getColor(this, by.carkva_gazeta.malitounik.R.color.colorIcons));
            label2.setBackgroundColor(ContextCompat.getColor(this, by.carkva_gazeta.malitounik.R.color.colorIcons));
            label3.setBackgroundColor(ContextCompat.getColor(this, by.carkva_gazeta.malitounik.R.color.colorIcons));
            label4.setBackgroundColor(ContextCompat.getColor(this, by.carkva_gazeta.malitounik.R.color.colorIcons));
            label5.setBackgroundColor(ContextCompat.getColor(this, by.carkva_gazeta.malitounik.R.color.colorIcons));
            label6.setBackgroundColor(ContextCompat.getColor(this, by.carkva_gazeta.malitounik.R.color.colorIcons));
        }
        ajustCompoundDrawableSizeWithText(label1, drawable);
        ajustCompoundDrawableSizeWithText(label2, drawable);
        ajustCompoundDrawableSizeWithText(label3, drawable);
        ajustCompoundDrawableSizeWithText(label4, drawable);
        ajustCompoundDrawableSizeWithText(label5, drawable);
        ajustCompoundDrawableSizeWithText(label6, drawable);
        label1.setOnClickListener(this);
        label2.setOnClickListener(this);
        label3.setOnClickListener(this);
        label4.setOnClickListener(this);
        label5.setOnClickListener(this);
        label6.setOnClickListener(this);
        onClickView = label1;

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

        if (k.getBoolean("orientation", false)) {
            setRequestedOrientation(getOrientation());
        } else {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);
        }
        setTollbarTheme();

        Gson gson = new Gson();
        String json = k.getString("bibliateka_naidaunia", "");
        int savedInstance = -1;
        if (savedInstanceState != null) {
            fullscreenPage = savedInstanceState.getBoolean("fullscreen");
            default_page = savedInstanceState.getInt("page");
            filePath = savedInstanceState.getString("filePath");
            fileName = savedInstanceState.getString("fileName");
            idSelect = savedInstanceState.getInt("idSelect");
            nameRubrika = savedInstanceState.getString("nameRubrika");
            if (savedInstanceState.getInt("pdfView", 0) == 1) {
                listView.setVisibility(View.GONE);
                pdfView.setVisibility(View.VISIBLE);
                webView.setVisibility(View.GONE);
                savedInstance = 1;
            } else if (savedInstanceState.getInt("pdfView", 0) == 2) {
                listView.setVisibility(View.GONE);
                webView.setVisibility(View.VISIBLE);
                pdfView.setVisibility(View.GONE);
                savedInstance = 2;
            } else {
                listView.setVisibility(View.VISIBLE);
                pdfView.setVisibility(View.GONE);
                webView.setVisibility(View.GONE);
                savedInstance = 0;
            }
            saveindep = false;
            if (!Objects.requireNonNull(json).equals("")) {
                Type type = new TypeToken<ArrayList<ArrayList<String>>>() {
                }.getType();
                naidaunia.addAll(gson.fromJson(json, type));
            }
            if (idSelect != by.carkva_gazeta.malitounik.R.id.label6)//if (!(idSelect == by.carkva_gazeta.malitounik.R.id.label6 || idSelect == by.carkva_gazeta.malitounik.R.id.label7))
                onClick(findViewById(idSelect));
            supportInvalidateOptionsMenu();
            if (fullscreenPage) hide();
        } else {
            if (getIntent() != null) {
                if (getIntent().getData() != null) {
                    String uri = getIntent().getData().toString();
                    if (uri.contains("root")) {
                        int t1 = uri.indexOf("root");
                        filePath = uri.substring(t1 + 4);
                    } else {
                        Cursor cursor = null;
                        try {
                            String[] proj = {MediaStore.Images.Media.DATA};
                            cursor = getContentResolver().query(getIntent().getData(), proj, null, null, null);
                            int column_index = Objects.requireNonNull(cursor).getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                            cursor.moveToFirst();
                            filePath = cursor.getString(column_index);
                        } catch (Exception ignored) {
                        } finally {
                            if (cursor != null) {
                                cursor.close();
                            }
                        }
                    }
                }
                if (!filePath.equals("")) {
                    int t1 = filePath.lastIndexOf("/");
                    fileName = filePath.substring(t1 + 1);
                    listView.setVisibility(View.GONE);
                    if (fileName.toLowerCase().contains(".pdf")) {
                        pdfView.setVisibility(View.VISIBLE);
                    } else {
                        webView.setVisibility(View.VISIBLE);
                    }
                    if (!Objects.requireNonNull(json).equals("")) {
                        Type type = new TypeToken<ArrayList<ArrayList<String>>>() {
                        }.getType();
                        naidaunia.addAll(gson.fromJson(json, type));
                    }
                } else {
                    if (!Objects.requireNonNull(json).equals("")) {
                        Type type = new TypeToken<ArrayList<ArrayList<String>>>() {
                        }.getType();
                        naidaunia.addAll(gson.fromJson(json, type));
                        if (naidaunia.size() == 0) {
                            drawer.openDrawer(GravityCompat.START);
                            listView.setVisibility(View.VISIBLE);
                        } else onClick(label1);
                    } else {
                        drawer.openDrawer(GravityCompat.START);
                        listView.setVisibility(View.VISIBLE);
                    }
                }
                site = getIntent().getBooleanExtra("site", false);
                if (site)
                    drawer.openDrawer(GravityCompat.START);
            }
        }
        if (!filePath.equals("") && savedInstance != 0) {
            if (filePath.contains("raw:")) {
                int t1 = filePath.indexOf("raw:");
                filePath = filePath.substring(t1 + 4);
            }
            int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
            if (PackageManager.PERMISSION_DENIED == permissionCheck) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, MY_PERMISSIONS_WRITE_EXTERNAL_STORAGE);
            } else {
                if (fileName.toLowerCase().contains(".pdf")) {
                    loadFilePDF();
                } else if (fileName.toLowerCase().contains(".fb2.zip")) {
                    loadFileFB2ZIP();
                } else if (fileName.toLowerCase().contains(".fb2")) {
                    loadFileFB2();
                } else {
                    loadFileEPUB();
                }
                listView.setVisibility(View.GONE);
            }
        }
    }

    private void loadFilePDF() {
        progress.setVisibility(View.GONE);
        Map<String, ?> allEntries = k.getAll();
        for (Map.Entry<String, ?> entry : allEntries.entrySet()) {
            if (entry.getKey().contains(fileName)) {
                default_page = k.getInt(fileName, 0);
                break;
            }
        }
        File file = new File(filePath);
        pdfView.fromFile(file)
                .enableAntialiasing(true)
                //.pages(0, 2, 1, 3, 3, 3) // all pages are displayed by default
                .enableSwipe(true) // allows to block changing pages using swipe
                .swipeHorizontal(false)
                .enableDoubletap(true)
                .defaultPage(default_page)
                // allows to draw something on the current page, usually visible in the middle of the screen
                //.onDraw(onDrawListener)
                // allows to draw something on all pages, separately for every page. Called only for visible pages
                //.onDrawAll(onDrawListener)
                .onLoad(this) // called after document is loaded and starts to be rendered
                .onPageChange(this)//.onPageChange(onPageChangeListener)
                //.onPageScroll(onPageScrollListener)
                .onError(this)//.onError(onErrorListener)
                //.onPageError(onPageErrorListener)
                //.onRender(onRenderListener) // called after document is rendered for the first time
                // called on single tap, return true if handled, false to toggle scroll handle visibility
                //.onTap(onTapListener)
                //.onLongPress(onLongPressListener)
                .enableAnnotationRendering(false) // render annotations (such as comments, colors or forms)
                .password(null)
                .scrollHandle(null)
                .enableAntialiasing(true) // improve rendering a little bit on low-res screens
                // spacing between pages in dp. To define spacing color, set view background
                .spacing(2)
                .autoSpacing(false) // add dynamic spacing to fit each page on its own on the screen
                //.linkHandler(DefaultLinkHandler)
                .pageFitPolicy(FitPolicy.WIDTH)
                .pageSnap(true) // snap pages to screen boundaries
                .pageFling(true) // make a fling change only a single page like ViewPager
                .nightMode(k.getBoolean("inversion", false)) // toggle night mode
                .load();
    }

    private void loadFileEPUB() {
        int naidauCount = naidaunia.size() - 1;
        if (biblioteka == null || naidaunia.size() <= 0 || !naidaunia.get(naidauCount).get(1).contains(filePath)) {
            File file = new File(filePath);
            int t1 = fileName.lastIndexOf(".");
            String dirName = fileName.substring(0, t1);

            File dir = new File(getFilesDir() + "/Book/" + dirName + "/");
            if (!dir.exists()) {
                progress.setVisibility(View.VISIBLE);
                dir.mkdirs();
                new Thread(() -> {
                    boolean error = false;
                    try {
                        unzip(file, dir);
                    } catch (IOException ignored) {
                        error = true;
                    }
                    if (!error) {
                        runOnUiThread(() -> {
                            loadFileEPUB(dir);
                            progress.setVisibility(View.GONE);
                        });
                    } else {
                        progress.setVisibility(View.GONE);
                    }
                }).start();
            } else {
                loadFileEPUB(dir);
            }
        } else {
            webView.setVisibility(View.VISIBLE);
            title_toolbar.setText(biblioteka.getBookTitle());
        }
    }

    private void loadFileEPUB(File dir) {
        animationStoronaLeft = true;
        Map<String, ?> allEntries = k.getAll();
        for (Map.Entry<String, ?> entry : allEntries.entrySet()) {
            if (entry.getKey().contains(fileName)) {
                default_page = k.getInt(fileName, 0);
                break;
            }
        }
        biblioteka = new bibliotekaEpub(dir.getAbsolutePath());
        webView.setVisibility(View.VISIBLE);
        if (default_page >= biblioteka.getContent().size()) {
            default_page = 0;
            positionY = 0;
        }
        String[] split = biblioteka.getContent().get(default_page).get(1).split("#");
        File epubfile = new File(dir.getAbsolutePath() + "/" + split[0]);
        StringBuilder builder = new StringBuilder();
        try {
            FileReader inputStream = new FileReader(epubfile);
            BufferedReader reader = new BufferedReader(inputStream);
            String line;
            while ((line = reader.readLine()) != null) {
                builder.append(line).append("\n");
            }
            inputStream.close();
        } catch (Throwable ignored) {
        }
        String ebubcontent = builder.toString().replace("</head>", "<style type=\"text/css\">::selection {background: #eb9b9a;}</style></head>");
        webView.loadDataWithBaseURL(null, ebubcontent, "text/html; charset=utf-8", "utf-8", null);
        //webView.loadUrl("file://" + dir.getAbsolutePath() + "/" + split[0]);
        webView.scrollTo(0, positionY);
        bookTitle.clear();
        bookTitle.addAll(biblioteka.getContentList());
        title_toolbar.setText(biblioteka.getBookTitle());
        text_view_page.setVisibility(View.GONE);
        for (int i = 0; i < naidaunia.size(); i++) {
            if (naidaunia.get(i).get(1).contains(filePath)) {
                naidaunia.remove(i);
                break;
            }
        }
        Gson gson = new Gson();
        ArrayList<String> temp = new ArrayList<>();
        temp.add(biblioteka.getBookTitle());
        temp.add(filePath);
        temp.add(biblioteka.getTitleImage());
        naidaunia.add(temp);
        SharedPreferences.Editor prefEditor;
        prefEditor = k.edit();
        prefEditor.putString("bibliateka_naidaunia", gson.toJson(naidaunia));
        prefEditor.apply();
    }

    private void loadFileFB2ZIP() {
        File dir = new File(getFilesDir() + "/Book");
        if (!dir.exists()) {
            dir.mkdir();
        }
        int naidauCount = naidaunia.size() - 1;
        if (fb2 == null || naidaunia.size() <= 0 || !naidaunia.get(naidauCount).get(1).contains(filePath)) {
            File file = new File(filePath);
            try {
                ZipInputStream zis = new ZipInputStream(new BufferedInputStream(new FileInputStream(file)));
                ZipEntry ze;
                int count;
                byte[] buffer = new byte[8192];
                while ((ze = zis.getNextEntry()) != null) {
                    file = new File(getFilesDir() + "/Book", ze.getName());
                    try (FileOutputStream fout = new FileOutputStream(file)) {
                        while ((count = zis.read(buffer)) != -1)
                            fout.write(buffer, 0, count);
                    }
                }
            } catch (IOException ignored) {
            }
            filePath = file.getAbsolutePath();
            loadFileFB2();
        } else {
            webView.setVisibility(View.VISIBLE);
            title_toolbar.setText(fb2.getTitle());
        }
    }

    private void loadFileFB2() {
        animationStoronaLeft = true;
        biblioteka = null;
        Map<String, ?> allEntries = k.getAll();
        for (Map.Entry<String, ?> entry : allEntries.entrySet()) {
            if (entry.getKey().contains(fileName)) {
                default_page = k.getInt(fileName, 0);
                break;
            }
        }
        try {
            fb2 = new FictionBook(new File(filePath));
        } catch (ParserConfigurationException | IOException | SAXException e) {
            Dialog_pdf_error pdf_error = Dialog_pdf_error.getInstance("FB2");
            pdf_error.show(getSupportFragmentManager(), "pdf_error");
            return;
        }
        File dir = new File(getFilesDir() + "/Book");
        if (!dir.exists()) {
            dir.mkdir();
        }
        File file = null;
        Map<String, Binary> map = fb2.getBinaries();
        for (Map.Entry<String, Binary> entry : map.entrySet()) {
            String key = entry.getKey();
            Binary value = entry.getValue();
            if (key.toLowerCase().contains("cover")) {
                file = new File(getFilesDir() + "/Book", new File(filePath).getName() + key);
                try {
                    String buffer = value.getBinary();
                    try (FileOutputStream fout = new FileOutputStream(file)) {
                        fout.write(Base64.decode(buffer, Base64.DEFAULT));
                    }
                } catch (IOException ignored) {
                }
            }
        }
        ArrayList<Section> section = fb2.getBody().getSections();
        ArrayList<String> content = new ArrayList<>();
        int count = 0;
        for (int i = 0; i < section.size(); i++) {
            ArrayList<Section> section2 = section.get(i).getSections();
            if (section2.size() > 0) {
                for (int q = 0; q < section2.size(); q++) {
                    ArrayList<Title> titles = section.get(i).getSections().get(q).getTitles();
                    if (titles.size() > 0) {
                        content.add(count + "<>" + titles.get(0).getParagraphs().get(0).getText());
                        count++;
                    }
                }
            } else {
                ArrayList<Title> titles = section.get(i).getTitles();
                if (titles.size() > 0) {
                    content.add(count + "<>" + titles.get(0).getParagraphs().get(0).getText());
                    count++;
                }
            }
        }
        bookTitle.clear();
        bookTitle.addAll(content);
        fb2PageText = getFB2Page();
        webView.loadDataWithBaseURL(null, fb2PageText, "text/html; charset=utf-8", "utf-8", null);
        webView.setVisibility(View.VISIBLE);
        if (default_page >= content.size()) {
            default_page = 0;
            positionY = 0;
        }
        webView.scrollTo(0, positionY);
        title_toolbar.setText(fb2.getTitle());
        text_view_page.setVisibility(View.GONE);
        for (int i = 0; i < naidaunia.size(); i++) {
            if (naidaunia.get(i).get(1).contains(filePath)) {
                naidaunia.remove(i);
                break;
            }
        }
        Gson gson = new Gson();
        ArrayList<String> temp = new ArrayList<>();
        temp.add(fb2.getTitle());
        temp.add(filePath);
        if (file != null)
            temp.add(file.getAbsolutePath());
        else
            temp.add("");
        naidaunia.add(temp);
        SharedPreferences.Editor prefEditor;
        prefEditor = k.edit();
        prefEditor.putString("bibliateka_naidaunia", gson.toJson(naidaunia));
        prefEditor.apply();
    }

    private String getFB2Page() {
        ArrayList<Section> section = fb2.getBody().getSections();
        StringBuilder sb = new StringBuilder();
        sb.append("<style>::selection {background: #eb9b9a;} img{display: inline;height: auto;max-width: 100%;} a{text-decoration: none;}</style>").append("\n");
        for (int i = 0; i < section.size(); i++) {
            ArrayList<Section> section2 = section.get(i).getSections();
            if (section2.size() > 0) {
                for (int q = 0; q < section2.size(); q++) {
                    ArrayList<Element> elements = section2.get(q).getElements();
                    ArrayList<Title> titles = section2.get(q).getTitles();
                    if (titles.size() > 0 && bookTitle.get(default_page).contains(titles.get(0).getParagraphs().get(0).getText())) {
                        ArrayList<String> notes = new ArrayList<>();
                        if (titles.size() > 0)
                            sb.append("<strong>").append(titles.get(0).getParagraphs().get(0).getText()).append("</strong>").append("<p>").append("\n");
                        for (int e = 0; e < elements.size(); e++) {
                            try {
                                if (elements.get(e) instanceof P) {
                                    P p = (P) elements.get(e);
                                    ArrayList<Image> images = p.getImages();
                                    if (images != null) {
                                        String img = images.get(0).getValue();
                                        Map<String, Binary> map = fb2.getBinaries();
                                        String imageRaw = "";
                                        for (Map.Entry<String, Binary> entry : map.entrySet()) {
                                            String key = entry.getKey();
                                            Binary value = entry.getValue();
                                            if (img.contains(key)) {
                                                imageRaw = value.getBinary();
                                            }
                                        }
                                        sb.append("<img src=\"data:image/jpeg;base64,").append(imageRaw).append("\" />").append("<p>").append("\n");
                                    } else if (elements.get(e) instanceof EmptyLine) {
                                        sb.append("<p>").append("\n");
                                    } else {
                                        String text = elements.get(e).getText();
                                        int t1 = text.indexOf("[");
                                        if (t1 != -1) {
                                            int t2 = text.indexOf("]", t1 + 1);
                                            notes.add(text.substring(t1 + 1, t2));
                                            text = text.substring(0, t1) + "<sup><a id=\"s_" + text.substring(t1 + 1, t2) + "\" href=\"#n_" + text.substring(t1 + 1, t2) + "\">" + text.substring(t1, t2 + 1) + "</a></sup>" + text.substring(t2 + 1);
                                        }
                                        sb.append(text).append("<p>").append("\n");
                                    }
                                }
                            } catch (NullPointerException ignored) {
                            }
                        }
                        if (notes.size() > 0) {
                            sb.append("<hr size=\"2\" color=\"#000000\">").append("<p>").append("\n");
                            ArrayList<Section> notesK = fb2.getNotes().getSections();
                            for (int r = 0; r < notesK.size(); r++) {
                                for (int w = 0; w < notes.size(); w++) {
                                    if (notesK.get(r).getTitles().get(0).getParagraphs().get(0).getText().contains(notes.get(w))) {
                                        sb.append("[").append(notes.get(w)).append("] ").append(notesK.get(r).getElements().get(0).getText()).append("<p>").append("\n").append(" <a id=\"n_").append(notes.get(w)).append("\" href=\"#s_").append(notes.get(w)).append("\">Назад</a>").append("<p>").append("\n");
                                    }
                                }
                            }
                        }
                        break;
                    }
                }
            } else {
                ArrayList<Element> elements = section.get(i).getElements();
                ArrayList<Title> titles = section.get(i).getTitles();
                int t1 = bookTitle.get(default_page).indexOf(">");
                if (titles.size() > 0 && titles.get(0).getParagraphs().get(0).getText().contains(bookTitle.get(default_page).substring(t1 + 1))) {
                    ArrayList<String> notes = new ArrayList<>();
                    sb.append("<strong>").append(titles.get(0).getParagraphs().get(0).getText()).append("</strong>").append("<p>").append("\n");
                    for (int e = 0; e < elements.size(); e++) {
                        try {
                            if (elements.get(e) instanceof P) {
                                P p = (P) elements.get(e);
                                ArrayList<Image> images = p.getImages();
                                if (images != null) {
                                    String img = images.get(0).getValue();
                                    Map<String, Binary> map = fb2.getBinaries();
                                    String imageRaw = "";
                                    for (Map.Entry<String, Binary> entry : map.entrySet()) {
                                        String key = entry.getKey();
                                        Binary value = entry.getValue();
                                        if (img.contains(key)) {
                                            imageRaw = value.getBinary();
                                        }
                                    }
                                    sb.append("<img src=\"data:image/jpeg;base64,").append(imageRaw).append("\" />").append("<p>").append("\n");
                                } else if (elements.get(e) instanceof EmptyLine) {
                                    sb.append("<p>").append("\n");
                                } else {
                                    String text = elements.get(e).getText();
                                    int t3 = text.indexOf("[");
                                    if (t3 != -1) {
                                        int t2 = text.indexOf("]", t3 + 1);
                                        notes.add(text.substring(t3 + 1, t2));
                                        text = text.substring(0, t3) + "<sup><a id=\"s_" + text.substring(t3 + 1, t2) + "\" href=\"#n_" + text.substring(t3 + 1, t2) + "\">" + text.substring(t3, t2 + 1) + "</a></sup>" + text.substring(t2 + 1);
                                    }
                                    sb.append(text).append("<p>").append("\n");
                                }
                            }
                        } catch (NullPointerException ignored) {
                        }
                    }
                    if (notes.size() > 0) {
                        sb.append("<hr size=\"2\" color=\"#000000\">").append("<p>").append("\n");
                        ArrayList<Section> notesK = fb2.getNotes().getSections();
                        for (int r = 0; r < notesK.size(); r++) {
                            for (int w = 0; w < notes.size(); w++) {
                                if (notesK.get(r).getTitles().get(0).getParagraphs().get(0).getText().contains(notes.get(w))) {
                                    sb.append("[").append(notes.get(w)).append("] ").append(notesK.get(r).getElements().get(0).getText()).append("<p>").append("\n").append(" <a id=\"n_").append(notes.get(w)).append("\" href=\"#s_").append(notes.get(w)).append("\">Назад</a>").append("<p>").append("\n");
                                }
                            }
                        }
                    }
                    break;
                }
            }
        }
        return sb.toString();
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        autoscroll = k.getBoolean("autoscroll", false);

        if (listView.getVisibility() == View.VISIBLE && idSelect == by.carkva_gazeta.malitounik.R.id.label1)
            menu.findItem(by.carkva_gazeta.malitounik.R.id.action_trash).setVisible(true);
        else
            menu.findItem(by.carkva_gazeta.malitounik.R.id.action_trash).setVisible(false);
        if (listView.getVisibility() == View.VISIBLE && (idSelect == by.carkva_gazeta.malitounik.R.id.label2 || idSelect == by.carkva_gazeta.malitounik.R.id.label3 || idSelect == by.carkva_gazeta.malitounik.R.id.label4 || idSelect == by.carkva_gazeta.malitounik.R.id.label5))
            menu.findItem(by.carkva_gazeta.malitounik.R.id.action_update).setVisible(true);
        else
            menu.findItem(by.carkva_gazeta.malitounik.R.id.action_update).setVisible(false);
        menu.findItem(by.carkva_gazeta.malitounik.R.id.action_auto).setVisible(false);
        if (listView.getVisibility() == View.GONE) {
            menu.findItem(by.carkva_gazeta.malitounik.R.id.action_fullscreen).setVisible(true);
            menu.findItem(by.carkva_gazeta.malitounik.R.id.action_set_page).setVisible(true);
            menu.findItem(by.carkva_gazeta.malitounik.R.id.action_bright).setVisible(true);
            if (pdfView.getVisibility() == View.VISIBLE) {
                menu.findItem(by.carkva_gazeta.malitounik.R.id.action_title).setVisible(false);
                menu.findItem(by.carkva_gazeta.malitounik.R.id.action_set_page).setVisible(true);
                menu.findItem(by.carkva_gazeta.malitounik.R.id.action_inversion).setVisible(true);
                menu.findItem(by.carkva_gazeta.malitounik.R.id.action_font).setVisible(false);
                if (this.menu)
                    menu.findItem(by.carkva_gazeta.malitounik.R.id.action_title).setVisible(true);
            } else {
                if (autoscroll) {
                    menu.findItem(by.carkva_gazeta.malitounik.R.id.action_plus).setVisible(true);
                    menu.findItem(by.carkva_gazeta.malitounik.R.id.action_minus).setVisible(true);
                    menu.findItem(by.carkva_gazeta.malitounik.R.id.action_auto).setTitle(getResources().getString(by.carkva_gazeta.malitounik.R.string.autoScrolloff));
                } else {
                    menu.findItem(by.carkva_gazeta.malitounik.R.id.action_plus).setVisible(false);
                    menu.findItem(by.carkva_gazeta.malitounik.R.id.action_minus).setVisible(false);
                    menu.findItem(by.carkva_gazeta.malitounik.R.id.action_auto).setTitle(getResources().getString(by.carkva_gazeta.malitounik.R.string.autoScrollon));
                }
                menu.findItem(by.carkva_gazeta.malitounik.R.id.action_title).setVisible(true);
                menu.findItem(by.carkva_gazeta.malitounik.R.id.action_auto).setVisible(true);
                menu.findItem(by.carkva_gazeta.malitounik.R.id.action_auto).setVisible(true);
                menu.findItem(by.carkva_gazeta.malitounik.R.id.action_set_page).setVisible(false);
                menu.findItem(by.carkva_gazeta.malitounik.R.id.action_inversion).setVisible(false);
                menu.findItem(by.carkva_gazeta.malitounik.R.id.action_font).setVisible(true);
            }
            menu.findItem(by.carkva_gazeta.malitounik.R.id.action_orientation).setVisible(true);
        } else {
            menu.findItem(by.carkva_gazeta.malitounik.R.id.action_title).setVisible(false);
            menu.findItem(by.carkva_gazeta.malitounik.R.id.action_fullscreen).setVisible(false);
            menu.findItem(by.carkva_gazeta.malitounik.R.id.action_set_page).setVisible(false);
            menu.findItem(by.carkva_gazeta.malitounik.R.id.action_bright).setVisible(false);
            menu.findItem(by.carkva_gazeta.malitounik.R.id.action_inversion).setVisible(false);
            menu.findItem(by.carkva_gazeta.malitounik.R.id.action_orientation).setVisible(false);
        }
        menu.findItem(by.carkva_gazeta.malitounik.R.id.action_orientation).setChecked(k.getBoolean("orientation", false));
        menu.findItem(by.carkva_gazeta.malitounik.R.id.action_inversion).setChecked(k.getBoolean("inversion", false));
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuInflater infl = getMenuInflater();
        infl.inflate(by.carkva_gazeta.malitounik.R.menu.bibliotekaview, menu);
        for (int i = 0; i < menu.size(); i++) {
            MenuItem item = menu.getItem(i);
            SpannableString spanString = new SpannableString(menu.getItem(i).getTitle().toString());
            int end = spanString.length();
            spanString.setSpan(new AbsoluteSizeSpan(SettingsActivity.GET_FONT_SIZE_MIN, true), 0, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            item.setTitle(spanString);
        }
        return true;
    }

    private void setTollbarTheme() {
        Toolbar toolbar = findViewById(by.carkva_gazeta.malitounik.R.id.toolbar);
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
        if (dzenNoch) {
            toolbar.setPopupTheme(by.carkva_gazeta.malitounik.R.style.AppCompatDark);
            toolbar.setBackgroundResource(by.carkva_gazeta.malitounik.R.color.colorprimary_material_dark);
        }
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, by.carkva_gazeta.malitounik.R.string.navigation_drawer_open, by.carkva_gazeta.malitounik.R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == MY_PERMISSIONS_WRITE_EXTERNAL_STORAGE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if (listView.getVisibility() == View.GONE) {
                    if (!fileName.equals("")) {
                        if (fileName.contains(".pdf")) {
                            loadFilePDF();
                        } else {
                            loadFileEPUB();
                        }
                    } else {
                        onClick(onClickView);
                    }
                } else {
                    onClick(onClickView);
                }
            } else {
                finish();
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (SystemClock.elapsedRealtime() - mLastClickTime < 1000) {
            return true;
        }
        mLastClickTime = SystemClock.elapsedRealtime();

        SharedPreferences.Editor prefEditor = k.edit();
        int id = item.getItemId();

        if (id == by.carkva_gazeta.malitounik.R.id.action_update) {
            if (runSql) return false;
            if (MainActivity.isIntNetworkAvailable(this) == 0) {
                Dialog_no_internet dialog_no_internet = new Dialog_no_internet();
                dialog_no_internet.show(getSupportFragmentManager(), "no_internet");
            } else {
                int rub = 1;
                switch (idSelect) {
                    case by.carkva_gazeta.malitounik.R.id.label2:
                        rub = 1;
                        break;
                    case by.carkva_gazeta.malitounik.R.id.label3:
                        rub = 2;
                        break;
                    case by.carkva_gazeta.malitounik.R.id.label4:
                        rub = 3;
                        break;
                    case by.carkva_gazeta.malitounik.R.id.label5:
                        rub = 4;
                        break;
                }
                getSql(rub);
            }
        }
        if (id == by.carkva_gazeta.malitounik.R.id.action_font) {
            Dialog_font_size dialog_font_size = new Dialog_font_size();
            dialog_font_size.show(getSupportFragmentManager(), "font");
        }
        if (id == by.carkva_gazeta.malitounik.R.id.action_trash) {
            naidaunia.clear();
            arrayList.clear();
            adapter.notifyDataSetChanged();
            Gson gson = new Gson();
            prefEditor.putString("bibliateka_naidaunia", gson.toJson(naidaunia));
            progress.setVisibility(View.VISIBLE);
            new Thread(() -> {
                File dir = new File(getFilesDir() + "/Book/");
                if (dir.exists()) {
                    try {
                        delete(dir);
                    } catch (IOException ignored) {
                    }
                }
                runOnUiThread(() -> progress.setVisibility(View.GONE));
            }).start();
        }
        if (id == by.carkva_gazeta.malitounik.R.id.action_title) {
            Dialog_title_biblioteka title_biblioteka = Dialog_title_biblioteka.getInstance(bookTitle);
            title_biblioteka.show(getSupportFragmentManager(), "title_biblioteka");
        }
        if (id == by.carkva_gazeta.malitounik.R.id.action_set_page) {
            Dialog_set_page_biblioteka biblioteka = Dialog_set_page_biblioteka.getInstance(pdfView.getCurrentPage(), pdfView.getPageCount());
            biblioteka.show(getSupportFragmentManager(), "set_page_biblioteka");
        }
        if (id == by.carkva_gazeta.malitounik.R.id.action_inversion) {
            item.setChecked(!item.isChecked());
            if (item.isChecked()) {
                pdfView.setNightMode(true);
                prefEditor.putBoolean("inversion", true);
            } else {
                pdfView.setNightMode(false);
                prefEditor.putBoolean("inversion", false);
            }
            pdfView.loadPages();
        }
        if (id == by.carkva_gazeta.malitounik.R.id.action_fullscreen) {
            if (k.getBoolean("FullscreenHelp", true)) {
                Dialog_Help_Fullscreen dialog_help_fullscreen = new Dialog_Help_Fullscreen();
                dialog_help_fullscreen.show(getSupportFragmentManager(), "FullscreenHelp");
            }
            fullscreenPage = true;
            hide();
        }
        if (id == by.carkva_gazeta.malitounik.R.id.action_orientation) {
            item.setChecked(!item.isChecked());
            if (item.isChecked()) {
                setRequestedOrientation(getOrientation());
                prefEditor.putBoolean("orientation", true);
            } else {
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);
                prefEditor.putBoolean("orientation", false);
            }
        }
        if (id == by.carkva_gazeta.malitounik.R.id.action_bright) {
            Dialog_brightness dialog_brightness = new Dialog_brightness();
            dialog_brightness.show(getSupportFragmentManager(), "brightness");
        }
        if (id == by.carkva_gazeta.malitounik.R.id.action_plus) {
            if (spid <= 235 && spid >= 20) {
                spid = spid - 5;
                int proc = 100 - (spid - 15) * 100 / 215;
                prog.setTextSize(TypedValue.COMPLEX_UNIT_SP, 50);
                prog.setText(getResources().getString(by.carkva_gazeta.malitounik.R.string.procent, proc));
                prog.setVisibility(View.VISIBLE);
                startProcent();
                stopAutoScroll();
                startAutoScroll();
                prefEditor.putInt("autoscrollSpid", spid);
            }
        }
        if (id == by.carkva_gazeta.malitounik.R.id.action_minus) {
            if (spid <= 225 && spid >= 10) {
                spid = spid + 5;
                int proc = 100 - (spid - 15) * 100 / 215;
                prog.setTextSize(TypedValue.COMPLEX_UNIT_SP, 50);
                prog.setText(getResources().getString(by.carkva_gazeta.malitounik.R.string.procent, proc));
                prog.setVisibility(View.VISIBLE);
                startProcent();
                stopAutoScroll();
                startAutoScroll();
                prefEditor.putInt("autoscrollSpid", spid);
            }
        }
        if (id == by.carkva_gazeta.malitounik.R.id.action_auto) {
            autoscroll = k.getBoolean("autoscroll", false);
            if (autoscroll) {
                stopAutoScroll();
                prefEditor.putBoolean("autoscroll", false);
            } else {
                startAutoScroll();
                prefEditor.putBoolean("autoscroll", true);
            }
            supportInvalidateOptionsMenu();
        }
        prefEditor.apply();
        return super.onOptionsItemSelected(item);
    }

    private void delete(File file) throws IOException {
        for (File childFile : Objects.requireNonNull(file.listFiles())) {
            if (childFile.isDirectory()) {
                delete(childFile);
            } else {
                if (!childFile.delete()) {
                    throw new IOException();
                }
            }
        }
        if (!file.delete()) {
            throw new IOException();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        SharedPreferences.Editor prefEditor = k.edit();
        if (pdfView.getVisibility() == View.VISIBLE) {
            prefEditor.putInt(fileName, pdfView.getCurrentPage());
        } else {
            prefEditor.putInt("webViewBibliotekaScroll", positionY);
            prefEditor.putInt(fileName, default_page);
        }
        prefEditor.apply();
        stopAutoScroll();
        scrollerSchedule = null;
        scrollTimer = null;
        if (resetTimer != null) {
            resetTimer.cancel();
            resetTimer = null;
        }
    }

    @Override
    public void onBackPressed() {
        SharedPreferences.Editor prefEditor;
        prefEditor = k.edit();
        if (pdfView.getVisibility() == View.VISIBLE) {
            prefEditor.putInt(fileName, pdfView.getCurrentPage());
        } else {
            prefEditor.putInt("webViewBibliotekaScroll", positionY);
            prefEditor.putInt(fileName, default_page);
        }
        prefEditor.apply();
        if (fullscreenPage) {
            fullscreenPage = false;
            show();
        } else if (drawer.isDrawerOpen(GravityCompat.START)) {
            super.onBackPressed();
        } else if (listView.getVisibility() == View.GONE) {
            supportInvalidateOptionsMenu();
            if (arrayList.size() == 0) {
                if (idSelect != by.carkva_gazeta.malitounik.R.id.label6) {
                    onClick(findViewById(idSelect));
                } else {
                    if (site) {
                        Intent intent = new Intent(this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        super.onBackPressed();
                    }
                }
            } else {
                if (idSelect == by.carkva_gazeta.malitounik.R.id.label1 || idSelect == by.carkva_gazeta.malitounik.R.id.label6) {
                    arrayList.clear();
                    arrayList.addAll(naidaunia);
                    Collections.reverse(arrayList);
                    listView.smoothScrollToPosition(0);
                    adapter.notifyDataSetChanged();
                }
                title_toolbar.setText(nameRubrika);
                text_view_page.setText("");
                listView.setVisibility(View.VISIBLE);
                pdfView.setVisibility(View.GONE);
                webView.setVisibility(View.GONE);
            }
            stopAutoScroll();
            scrollerSchedule = null;
            scrollTimer = null;
        } else {
            if (site) {
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                finish();
            } else {
                super.onBackPressed();
            }
        }
    }

    @Override
    public void onClick(View view) {
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        stopAutoScroll();
        if (toNextPageTimer != null) {
            toNextPageTimer.cancel();
            toNextPageTimer = null;
            toNextPageSchedule = null;
        }
        idSelect = view.getId();
        onClickView = view;
        int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (PackageManager.PERMISSION_DENIED == permissionCheck) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, MY_PERMISSIONS_WRITE_EXTERNAL_STORAGE);
        } else {
            int rub = -1;
            if (dzenNoch) {
                label1.setBackgroundColor(ContextCompat.getColor(this, by.carkva_gazeta.malitounik.R.color.colorbackground_material_dark));
                label2.setBackgroundColor(ContextCompat.getColor(this, by.carkva_gazeta.malitounik.R.color.colorbackground_material_dark));
                label3.setBackgroundColor(ContextCompat.getColor(this, by.carkva_gazeta.malitounik.R.color.colorbackground_material_dark));
                label4.setBackgroundColor(ContextCompat.getColor(this, by.carkva_gazeta.malitounik.R.color.colorbackground_material_dark));
                label5.setBackgroundColor(ContextCompat.getColor(this, by.carkva_gazeta.malitounik.R.color.colorbackground_material_dark));
                label6.setBackgroundColor(ContextCompat.getColor(this, by.carkva_gazeta.malitounik.R.color.colorbackground_material_dark));
            } else {
                label1.setBackgroundColor(ContextCompat.getColor(this, by.carkva_gazeta.malitounik.R.color.colorIcons));
                label2.setBackgroundColor(ContextCompat.getColor(this, by.carkva_gazeta.malitounik.R.color.colorIcons));
                label3.setBackgroundColor(ContextCompat.getColor(this, by.carkva_gazeta.malitounik.R.color.colorIcons));
                label4.setBackgroundColor(ContextCompat.getColor(this, by.carkva_gazeta.malitounik.R.color.colorIcons));
                label5.setBackgroundColor(ContextCompat.getColor(this, by.carkva_gazeta.malitounik.R.color.colorIcons));
                label6.setBackgroundColor(ContextCompat.getColor(this, by.carkva_gazeta.malitounik.R.color.colorIcons));
            }
            switch (idSelect) {
                case by.carkva_gazeta.malitounik.R.id.label1: {
                    progress.setVisibility(View.GONE);
                    arrayList.clear();
                    arrayList.addAll(naidaunia);
                    Collections.reverse(arrayList);
                    adapter.notifyDataSetChanged();
                    nameRubrika = "Нядаўнія кнігі";
                    title_toolbar.setText(nameRubrika);
                    text_view_page.setText("");
                    if (dzenNoch)
                        label1.setBackgroundColor(ContextCompat.getColor(this, by.carkva_gazeta.malitounik.R.color.colorprimary_material_dark));
                    else
                        label1.setBackgroundColor(ContextCompat.getColor(this, by.carkva_gazeta.malitounik.R.color.colorDivider));
                    break;
                }
                case by.carkva_gazeta.malitounik.R.id.label2: {
                    nameRubrika = "Гісторыя Царквы";
                    title_toolbar.setText(nameRubrika);
                    text_view_page.setText("");
                    rub = 1;
                    if (dzenNoch)
                        label2.setBackgroundColor(ContextCompat.getColor(this, by.carkva_gazeta.malitounik.R.color.colorprimary_material_dark));
                    else
                        label2.setBackgroundColor(ContextCompat.getColor(this, by.carkva_gazeta.malitounik.R.color.colorDivider));
                    break;
                }
                case by.carkva_gazeta.malitounik.R.id.label3: {
                    nameRubrika = "Малітоўнікі";
                    title_toolbar.setText(nameRubrika);
                    text_view_page.setText("");
                    rub = 2;
                    if (dzenNoch)
                        label3.setBackgroundColor(ContextCompat.getColor(this, by.carkva_gazeta.malitounik.R.color.colorprimary_material_dark));
                    else
                        label3.setBackgroundColor(ContextCompat.getColor(this, by.carkva_gazeta.malitounik.R.color.colorDivider));
                    break;
                }
                case by.carkva_gazeta.malitounik.R.id.label4: {
                    nameRubrika = "Сьпеўнікі";
                    title_toolbar.setText(nameRubrika);
                    text_view_page.setText("");
                    rub = 3;
                    if (dzenNoch)
                        label4.setBackgroundColor(ContextCompat.getColor(this, by.carkva_gazeta.malitounik.R.color.colorprimary_material_dark));
                    else
                        label4.setBackgroundColor(ContextCompat.getColor(this, by.carkva_gazeta.malitounik.R.color.colorDivider));
                    break;
                }
                case by.carkva_gazeta.malitounik.R.id.label5: {
                    nameRubrika = "Рэлігійная літаратура";
                    title_toolbar.setText(nameRubrika);
                    text_view_page.setText("");
                    rub = 4;
                    if (dzenNoch)
                        label5.setBackgroundColor(ContextCompat.getColor(this, by.carkva_gazeta.malitounik.R.color.colorprimary_material_dark));
                    else
                        label5.setBackgroundColor(ContextCompat.getColor(this, by.carkva_gazeta.malitounik.R.color.colorDivider));
                    break;
                }
                case by.carkva_gazeta.malitounik.R.id.label6: {
                    progress.setVisibility(View.GONE);
                    Dialog_file_explorer file_explorer = new Dialog_file_explorer();
                    file_explorer.show(getSupportFragmentManager(), "file_explorer");
                    break;
                }
            }
            if (saveindep) {
                listView.setVisibility(View.VISIBLE);
                pdfView.setVisibility(View.GONE);
                webView.setVisibility(View.GONE);
            }
            if (rub != -1 && listView.getVisibility() == View.VISIBLE) {
                arrayList.clear();
                if (MainActivity.isIntNetworkAvailable(this) == 1 || MainActivity.isIntNetworkAvailable(this) == 2) {
                    if (firstLiadSql) {
                        getSql(rub);
                        firstLiadSql = false;
                    } else {
                        Gson gson = new Gson();
                        String json = k.getString("Biblioteka", "");
                        if (!Objects.requireNonNull(json).equals("")) {
                            Type type = new TypeToken<ArrayList<ArrayList<String>>>() {
                            }.getType();
                            arrayList.addAll(gson.fromJson(json, type));
                        }
                        ArrayList<ArrayList<String>> temp = new ArrayList<>();
                        for (int i = 0; i < arrayList.size(); i++) {
                            int rtemp2 = Integer.parseInt(arrayList.get(i).get(4));
                            if (rtemp2 != rub) temp.add(arrayList.get(i));
                        }
                        arrayList.removeAll(temp);
                        adapter.notifyDataSetChanged();
                    }
                } else {
                    arrayList.clear();
                    adapter.notifyDataSetChanged();
                    Dialog_no_internet no_internet = new Dialog_no_internet();
                    no_internet.show(getSupportFragmentManager(), "no_internet");
                }

            }
            supportInvalidateOptionsMenu();
            drawer.closeDrawer(GravityCompat.START);
            saveindep = true;
        }
    }

    private void getSql(int rub) {
        runSql = true;
        arrayList.clear();
        adapter.notifyDataSetChanged();
        progress.setVisibility(View.VISIBLE);
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        String showUrl = "https://carkva-gazeta.by/biblioteka.php";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, showUrl, null, response -> new Thread(() -> {
            try {
                ArrayList<ArrayList<String>> temp = new ArrayList<>();
                JSONArray biblioteka = response.getJSONArray("biblioteka");
                Gson gson = new Gson();
                for (int i = 0; i < biblioteka.length(); i++) {
                    ArrayList<String> mySqlList = new ArrayList<>();
                    JSONObject kniga = biblioteka.getJSONObject(i);
                    int rubrika = Integer.parseInt(kniga.getString("rubryka"));
                    String link = kniga.getString("link");
                    String str = kniga.getString("str");
                    String pdf = kniga.getString("pdf");
                    String image = kniga.getString("image");
                    mySqlList.add(link);
                    int pos = str.indexOf("</span><br>");
                    str = str.substring(pos + 11);
                    mySqlList.add(str);
                    mySqlList.add(pdf);
                    URL url = new URL("https://carkva-gazeta.by/data/bibliateka/" + pdf);
                    String filesize = "0";
                    URLConnection conn = null;
                    try {
                        conn = url.openConnection();
                        if (conn instanceof HttpURLConnection) {
                            ((HttpURLConnection) conn).setRequestMethod("HEAD");
                        }
                        filesize = String.valueOf(conn.getContentLength());
                    } catch (IOException ignored) {
                    } finally {
                        if (conn instanceof HttpURLConnection) {
                            ((HttpURLConnection) conn).disconnect();
                        }
                    }
                    mySqlList.add(filesize);
                    mySqlList.add(String.valueOf(rubrika));
                    int im1 = image.indexOf("src=\"");
                    int im2 = image.indexOf("\"", im1 + 5);
                    image = "https://carkva-gazeta.by" + image.substring(im1 + 5, im2);
                    int t1 = pdf.lastIndexOf("."); //image.lastIndexOf("/");
                    String image_local = getFilesDir() + "/image_temp/" + pdf.substring(0, t1) + ".png"; //image.substring(t1 + 1);

                    mySqlList.add(image_local);
                    if (MainActivity.isIntNetworkAvailable(this) == 1 || MainActivity.isIntNetworkAvailable(this) == 2) {
                        File dir = new File(getFilesDir() + "/image_temp");
                        if (!dir.exists())
                            dir.mkdir();
                        Bitmap mIcon11;
                        File file = new File(image_local);
                        if (!file.exists()) {
                            try (FileOutputStream out = new FileOutputStream(getFilesDir() + "/image_temp/" + pdf.substring(0, t1) + ".png")) { //image.substring(t1 + 1))) {
                                InputStream in = new URL(image).openStream();
                                mIcon11 = BitmapFactory.decodeStream(in);
                                mIcon11.compress(Bitmap.CompressFormat.PNG, 90, out);
                            } catch (IOException ignored) {
                            }
                        }
                    }
                    if (rubrika == rub) {
                        arrayList.add(mySqlList);
                    }
                    temp.add(mySqlList);
                }
                String json = gson.toJson(temp);
                SharedPreferences.Editor prefEditors = k.edit();
                prefEditors.putString("Biblioteka", json);
                prefEditors.apply();
                runOnUiThread(() -> {
                    adapter.notifyDataSetChanged();
                    progress.setVisibility(View.GONE);
                });
            } catch (Exception ignored) {
            }
            runSql = false;
        }).start(), error -> {
        });
        requestQueue.add(jsonObjectRequest);
    }

    @Override
    public void onResume() {
        super.onResume();
        SplitCompat.install(this);
        autoscroll = k.getBoolean("autoscroll", false);
        spid = k.getInt("autoscrollSpid", 60);
        if (autoscroll) {
            startAutoScroll();
        }
        overridePendingTransition(by.carkva_gazeta.malitounik.R.anim.alphain, by.carkva_gazeta.malitounik.R.anim.alphaout);
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
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
        mHideHandler.removeCallbacks(mHidePart2Runnable);
        mHideHandler.postDelayed(mShowPart2Runnable, UI_ANIMATION_DELAY);
        recreate();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        SharedPreferences.Editor prefEditor;
        prefEditor = k.edit();
        if (pdfView.getVisibility() == View.VISIBLE)
            prefEditor.putInt(fileName, pdfView.getCurrentPage());
        else
            prefEditor.putInt(fileName, default_page);
        prefEditor.apply();
        outState.putBoolean("fullscreen", fullscreenPage);
        if (pdfView.getVisibility() == View.VISIBLE) {
            outState.putInt("page", pdfView.getCurrentPage());
            outState.putInt("pdfView", 1);
        } else if (webView.getVisibility() == View.VISIBLE) {
            prefEditor.putInt("webViewBibliotekaScroll", positionY);
            prefEditor.apply();
            outState.putInt("page", default_page);
            outState.putInt("pdfView", 2);
        } else {
            outState.putInt("pdfView", 0);
        }
        outState.putString("filePath", filePath);
        outState.putString("fileName", fileName);
        outState.putInt("idSelect", idSelect);
        outState.putString("nameRubrika", nameRubrika);
    }

    private void unzip(File zipFile, File targetDirectory) throws IOException {
        try (ZipInputStream zis = new ZipInputStream(new BufferedInputStream(new FileInputStream(zipFile)))) {
            ZipEntry ze;
            int count;
            byte[] buffer = new byte[8192];
            while ((ze = zis.getNextEntry()) != null) {
                File file = new File(targetDirectory, ze.getName());
                File dir = ze.isDirectory() ? file : file.getParentFile();
                if (!Objects.requireNonNull(dir).isDirectory() && !dir.mkdirs())
                    throw new FileNotFoundException("Failed to ensure directory: " + dir.getAbsolutePath());
                if (ze.isDirectory())
                    continue;
                try (FileOutputStream fout = new FileOutputStream(file)) {
                    while ((count = zis.read(buffer)) != -1)
                        fout.write(buffer, 0, count);
                }
            }
        }
    }

    private void NextPageAutoScroll() {
        Timer nextPageAutoScrollTimer = new Timer();
        TimerTask nextPageAutoScrollSchedule = new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(() -> startAutoScroll());
            }
        };
        nextPageAutoScrollTimer.schedule(nextPageAutoScrollSchedule, spid * 100);
    }

    private void toNextPage() {
        toNextPageTimer = new Timer();
        toNextPageSchedule = new TimerTask() {
            @Override
            public void run() {
                animationStoronaLeft = true;
                runOnUiThread(() -> webView.startAnimation(anim_out_left));
            }
        };
        toNextPageTimer.schedule(toNextPageSchedule, spid * 500);
    }

    private void stopAutoScroll() {
        webView.setOnBottomListener(null);
        if (scrollTimer != null) {
            scrollTimer.cancel();
            scrollTimer = null;
        }
        scrollerSchedule = null;
        if (resetTimer == null) {
            resetTimer = new Timer();
            TimerTask resetSchedule = new TimerTask() {
                @Override
                public void run() {
                    runOnUiThread(() -> getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON));
                }
            };
            resetTimer.schedule(resetSchedule, 60000);
        }
    }

    private void startAutoScroll() {
        if (resetTimer != null) {
            resetTimer.cancel();
            resetTimer = null;
        }
        webView.setOnBottomListener(this);
        if (scrollTimer == null) {
            scrollTimer = new Timer();
            if (scrollerSchedule != null) {
                scrollerSchedule.cancel();
                scrollerSchedule = null;
            }
            scrollerSchedule = new TimerTask() {
                @Override
                public void run() {
                    runOnUiThread(() -> {
                        if (!mActionDown && !drawer.isDrawerOpen(GravityCompat.START) && !MainActivity.dialogVisable) {
                            webView.scrollBy(0, 2);
                        }
                    });
                }
            };
            scrollTimer.schedule(scrollerSchedule, spid, spid);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        }
    }

    private void stopProcent() {
        if (procentTimer != null) {
            procentTimer.cancel();
            procentTimer = null;
        }
        procentSchedule = null;
    }

    private void startProcent() {
        g = (GregorianCalendar) Calendar.getInstance();
        if (procentTimer == null) {
            procentTimer = new Timer();
            if (procentSchedule != null) {
                procentSchedule.cancel();
                procentSchedule = null;
            }
            procentSchedule = new TimerTask() {
                @Override
                public void run() {
                    GregorianCalendar g2 = (GregorianCalendar) Calendar.getInstance();
                    if (g.getTimeInMillis() + 1000 <= g2.getTimeInMillis()) {
                        runOnUiThread(() -> {
                            prog.setVisibility(View.GONE);
                            stopProcent();
                        });
                    }
                }
            };
            procentTimer.schedule(procentSchedule, 20, 20);
        }
    }

    private void showPopupMenu(View view, int position, String name) {
        popup = new PopupMenu(this, view);
        MenuInflater infl = popup.getMenuInflater();
        infl.inflate(R.menu.popup_biblioteka, popup.getMenu());
        File file = new File(this.getFilesDir() + "/Biblijateka/" + arrayList.get(position).get(2));
        if (file.exists()) {
            popup.getMenu().getItem(1).setVisible(false);
        } else {
            popup.getMenu().getItem(2).setVisible(false);
            if (MainActivity.isIntNetworkAvailable(this) == 0)
                popup.getMenu().getItem(1).setVisible(false);
        }
        for (int i = 0; i < popup.getMenu().size(); i++) {
            MenuItem item = popup.getMenu().getItem(i);
            SpannableString spanString = new SpannableString(popup.getMenu().getItem(i).getTitle().toString());
            int end = spanString.length();
            spanString.setSpan(new AbsoluteSizeSpan(SettingsActivity.GET_FONT_SIZE_MIN, true), 0, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            item.setTitle(spanString);
        }
        popup.setOnMenuItemClickListener(menuItem -> {
            popup.dismiss();
            switch (menuItem.getItemId()) {
                case R.id.menu_opisanie:
                    Dialog_bibliateka dialog_bibliateka = Dialog_bibliateka.getInstance(arrayList.get(position).get(2), arrayList.get(position).get(1), arrayList.get(position).get(0), arrayList.get(position).get(3));
                    dialog_bibliateka.show(getSupportFragmentManager(), "dialog_bibliateka");
                    return true;
                case R.id.menu_download:
                    onDialogbibliatekaPositiveClick(arrayList.get(position).get(2), name);
                    return true;
                case R.id.menu_delite:
                    Dialog_delite dd = Dialog_delite.getInstance(0, arrayList.get(position).get(2), "з бібліятэкі", name);
                    dd.show(getSupportFragmentManager(), "dialog_delite");
                    return true;
            }
            return false;
        });
        popup.show();
    }

    class HelloWebViewClient extends WebViewClient {
        @Override
        public void onLoadResource(WebView view, String url) {
            view.setVisibility(View.GONE);
            progress.setVisibility(View.VISIBLE);
            super.onLoadResource(view, url);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            progress.setVisibility(View.GONE);
            view.setVisibility(View.VISIBLE);
            if (animationStoronaLeft)
                webView.startAnimation(anim_in_left);
            else
                webView.startAnimation(anim_in_right);
            super.onPageFinished(view, url);
        }
    }

    class bibliotekaAdapter extends ArrayAdapter<ArrayList<String>> {

        bibliotekaAdapter(Context context) {
            super(context, R.layout.simple_list_item_biblioteka, arrayList);
            k = context.getSharedPreferences("biblia", Context.MODE_PRIVATE);
        }

        @NonNull
        @Override
        public View getView(int position, View convertView, @NonNull ViewGroup parent) {
            ViewHolder viewHolder;
            if (convertView == null) {
                convertView = bibliotekaView.this.getLayoutInflater().inflate(R.layout.simple_list_item_biblioteka, parent, false);
                viewHolder = new ViewHolder();
                convertView.setTag(viewHolder);
                viewHolder.text = convertView.findViewById(R.id.label);
                viewHolder.imageView = convertView.findViewById(R.id.imageView2);
                viewHolder.imageView.setBackgroundResource(R.drawable.frame_image_biblioteka);
                viewHolder.imageView.getLayoutParams().width = width / 2;
                viewHolder.imageView.getLayoutParams().height = (int) (width / 2 * 1.4F);
                viewHolder.imageView.requestLayout();
                viewHolder.buttonPopup = convertView.findViewById(R.id.button_popup);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            if (arrayList.get(position).size() == 3) {
                viewHolder.buttonPopup.setVisibility(View.GONE);
                if (!arrayList.get(position).get(2).equals("")) {
                    new Thread(() -> {
                        BitmapFactory.Options options = new BitmapFactory.Options();
                        options.inPreferredConfig = Bitmap.Config.ARGB_8888;
                        Bitmap bitmap = BitmapFactory.decodeFile(arrayList.get(position).get(2), options);
                        runOnUiThread(() -> {
                            viewHolder.imageView.setImageBitmap(bitmap);
                            viewHolder.imageView.setVisibility(View.VISIBLE);
                        });
                    }).start();
                } else {
                    viewHolder.imageView.setVisibility(View.GONE);
                }
            } else {
                viewHolder.buttonPopup.setVisibility(View.VISIBLE);
                viewHolder.buttonPopup.setOnClickListener((v) -> showPopupMenu(viewHolder.buttonPopup, position, arrayList.get(position).get(0)));
                int t1 = arrayList.get(position).get(5).lastIndexOf("/");
                File file = new File(getFilesDir() + "/image_temp/" + arrayList.get(position).get(5).substring(t1 + 1));
                if (file.exists()) {
                    new Thread(() -> {
                        BitmapFactory.Options options = new BitmapFactory.Options();
                        options.inPreferredConfig = Bitmap.Config.ARGB_8888;
                        Bitmap bitmap = BitmapFactory.decodeFile(getFilesDir() + "/image_temp/" + arrayList.get(position).get(5).substring(t1 + 1), options);
                        runOnUiThread(() -> {
                            viewHolder.imageView.setImageBitmap(bitmap);
                            viewHolder.imageView.setVisibility(View.VISIBLE);
                        });
                    }).start();
                }
            }
            boolean dzenNoch = k.getBoolean("dzen_noch", false);
            if (dzenNoch) {
                viewHolder.text.setBackgroundResource(by.carkva_gazeta.malitounik.R.color.colorbackground_material_dark);
                viewHolder.text.setTextColor(ContextCompat.getColor(bibliotekaView.this, by.carkva_gazeta.malitounik.R.color.colorIcons));
                viewHolder.text.setCompoundDrawablesWithIntrinsicBounds(by.carkva_gazeta.malitounik.R.drawable.stiker_black, 0, 0, 0);
            }
            viewHolder.text.setTextSize(TypedValue.COMPLEX_UNIT_SP, SettingsActivity.GET_FONT_SIZE_MIN);
            viewHolder.text.setText(arrayList.get(position).get(0));
            return convertView;
        }
    }

    private static class ViewHolder {
        TextView_Roboto_Condensed text;
        ImageView imageView;
        ImageView buttonPopup;
    }
}
