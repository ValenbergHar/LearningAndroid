package by.carkva_gazeta.resources;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Objects;

import by.carkva_gazeta.malitounik.EditText_Roboto_Condensed;
import by.carkva_gazeta.malitounik.MainActivity;
import by.carkva_gazeta.malitounik.SettingsActivity;
import by.carkva_gazeta.malitounik.TextView_Roboto_Condensed;

/**
 * Created by oleg on 14.6.16
 */
public class My_natatki_add extends AppCompatActivity {

    private EditText_Roboto_Condensed editText;
    private EditText_Roboto_Condensed editTextFull;
    private String filename = "";
    private Boolean redak;
    private boolean dzenNoch;
    private String md5sum;

    @Override
    public void onPause() {
        super.onPause();
        write();
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        intent.putExtra("savefile", true);
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!MainActivity.checkBrightness) {
            WindowManager.LayoutParams lp = getWindow().getAttributes();
            lp.screenBrightness = (float) MainActivity.brightness / 100;
            getWindow().setAttributes(lp);
        }
        md5sum = md5Sum("<MEMA></MEMA>");
        SharedPreferences k = getSharedPreferences("biblia", MODE_PRIVATE);
        dzenNoch = k.getBoolean("dzen_noch", false);
        if (dzenNoch) setTheme(by.carkva_gazeta.malitounik.R.style.AppCompatDark);
        setContentView(R.layout.my_malitva_add);
        String title = getResources().getString(by.carkva_gazeta.malitounik.R.string.MALITVA_ADD);
        int fontBiblia = k.getInt("font_malitounik", SettingsActivity.GET_DEFAULT_FONT_SIZE);
        // Показываем клавиатуру
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        Objects.requireNonNull(imm).toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
        if (dzenNoch) {
            //getSupportActionBar().setBackgroundDrawable(getResources().getDrawable(R.color.colorprimary_material_dark));
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                Window window = getWindow();
                window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.setStatusBarColor(getResources().getColor(by.carkva_gazeta.malitounik.R.color.colorPrimary_text));
                window.setNavigationBarColor(getResources().getColor(by.carkva_gazeta.malitounik.R.color.colorPrimary_text));
            }
        }
        editTextFull = findViewById(R.id.EditText);
        editTextFull.setTextSize(TypedValue.COMPLEX_UNIT_SP, fontBiblia);
        if (savedInstanceState != null) {
            filename = savedInstanceState.getString("filename");
            redak = savedInstanceState.getBoolean("redak", false);
        } else {
            filename = getIntent().getStringExtra("filename");
            redak = getIntent().getBooleanExtra("redak", false);
        }
        editText = findViewById(R.id.file);
        editText.setTextSize(TypedValue.COMPLEX_UNIT_SP, fontBiblia);
        if (dzenNoch) {
            editTextFull.setTextColor(getResources().getColor(by.carkva_gazeta.malitounik.R.color.colorIcons));
            editText.setTextColor(getResources().getColor(by.carkva_gazeta.malitounik.R.color.colorIcons));
        } else {
            editText.setTextColor(getResources().getColor(by.carkva_gazeta.malitounik.R.color.colorPrimary_text));
        }
        if (redak) {
            title = getResources().getString(by.carkva_gazeta.malitounik.R.string.malitva_edit);
            try {
                File file = new File(getFilesDir() + "/Malitva/" + filename);
                FileReader inputStream = new FileReader(file);
                BufferedReader reader = new BufferedReader(inputStream);
                String line;
                StringBuilder builder = new StringBuilder();
                while ((line = reader.readLine()) != null) {
                    builder.append(line).append("\n");
                }
                inputStream.close();
                String[] res = builder.toString().split("<MEMA></MEMA>");
                if (res[1].contains("<RTE></RTE>")) {
                    int start = res[1].indexOf("<RTE></RTE>");
                    res[1] = res[1].substring(0, start);
                    md5sum = md5Sum(res[0] + "<MEMA></MEMA>" + res[1].substring(0, start));
                } else {
                    md5sum = md5Sum(res[0] + "<MEMA></MEMA>" + res[1]);
                }
                editTextFull.setText(res[1]);
                editText.setText(res[0]);
            } catch (Throwable ignored) {
            }
        }
        editText.setSelection(Objects.requireNonNull(editText.getText()).toString().length());
        setTollbarTheme(title);
    }

    private void setTollbarTheme(String title) {
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
        title_toolbar.setText(title);
        if (dzenNoch) {
            toolbar.setBackgroundResource(by.carkva_gazeta.malitounik.R.color.colorprimary_material_dark);
        }
    }

    private void write() {
        String string;
        String filename = Objects.requireNonNull(editText.getText()).toString();
        String imiafile = "Mae_malitvy";
        string = Objects.requireNonNull(editTextFull.getText()).toString();
        GregorianCalendar gc = (GregorianCalendar) Calendar.getInstance();
        String editMd5 = md5Sum(filename + "<MEMA></MEMA>" + string);
        if (!md5sum.equals(editMd5)) {
            if (!redak) {
                int i = 1;
                while (true) {
                    imiafile = "Mae_malitvy_" + i;
                    File fileN = new File(this.getFilesDir() + "/Malitva/" + imiafile);
                    if (fileN.exists()) {
                        i++;
                    } else {
                        break;
                    }
                }
            }
            try {
                if (filename.equals("")) {
                    String[] mun = {"студзеня", "лютага", "сакавіка", "красавіка", "траўня", "чэрвеня", "ліпеня", "жніўня", "верасьня", "кастрычніка", "лістапада", "сьнежня"};
                    filename = gc.get(Calendar.DATE) + " " + mun[gc.get(Calendar.MONTH)] + " " + gc.get(Calendar.YEAR) + " " + gc.get(Calendar.HOUR_OF_DAY) + ":" + gc.get(Calendar.MINUTE);
                }
                File file;
                if (redak) {
                    file = new File(this.getFilesDir() + "/Malitva/" + this.filename);
                } else {
                    file = new File(this.getFilesDir() + "/Malitva/" + imiafile);
                }
                FileWriter outputStream;
                outputStream = new FileWriter(file);
                outputStream.write(filename + "<MEMA></MEMA>" + string + "<RTE></RTE>" + gc.getTimeInMillis());
                outputStream.close();
                // Скрываем клавиатуру
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                Objects.requireNonNull(imm).hideSoftInputFromWindow(editTextFull.getWindowToken(), 0);
                redak = true;
                //MyBackupAgent.requestBackup(this);
            } catch (Exception ignored) {
            }
        }
    }

    private String md5Sum(String st) {
        MessageDigest messageDigest;
        byte[] digest;
        try {
            messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.reset();
            messageDigest.update(st.getBytes());
            digest = messageDigest.digest();
        } catch (NoSuchAlgorithmException ignored) {
            return "";
        }
        BigInteger bigInt = new BigInteger(1, digest);
        StringBuilder md5Hex = new StringBuilder(bigInt.toString(16));
        while (md5Hex.length() < 32) {
            md5Hex.insert(0, "0");
        }
        return md5Hex.toString();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("filename", filename);
        outState.putBoolean("redak", redak);
    }

    @Override
    protected void onResume() {
        super.onResume();
        overridePendingTransition(by.carkva_gazeta.malitounik.R.anim.alphain, by.carkva_gazeta.malitounik.R.anim.alphaout);
    }
}
