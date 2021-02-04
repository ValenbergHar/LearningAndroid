package by.carkva_gazeta.malitounik;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.SystemClock;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.SparseIntArray;
import android.util.TypedValue;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by oleg on 21.10.18
 */

public class search_sviatyia extends AppCompatActivity {

    private TextView_Roboto_Condensed akafist;
    private EditText_Roboto_Condensed editText;
    private search_ListAdapter adapter;
    private boolean dzenNoch;
    private Timer PosukPesenTimer = null;
    private TimerTask PosukPesenSchedule;
    private ArrayList<ArrayList<String>> arrayLists;
    private ArrayList<String> arrayRes;
    private SharedPreferences chin;
    private GregorianCalendar c;
    private long mLastClickTime = 0;
    private final String[] munName = {"студзеня", "лютага", "сакавіка", "красавіка", "траўня", "чэрвеня", "ліпеня", "жніўня", "верасьня", "кастрычніка", "лістапада", "сьнежня"};

    @Override
    protected void onResume() {
        super.onResume();
        overridePendingTransition(R.anim.alphain, R.anim.alphaout);
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
        if (!MainActivity.checkBrightness) {
            WindowManager.LayoutParams lp = getWindow().getAttributes();
            lp.screenBrightness = (float) MainActivity.brightness / 100;
            getWindow().setAttributes(lp);
        }
        chin = getSharedPreferences("biblia", MODE_PRIVATE);
        c = (GregorianCalendar) Calendar.getInstance();
        dzenNoch = chin.getBoolean("dzen_noch", false);
        super.onCreate(savedInstanceState);
        if (dzenNoch) setTheme(R.style.AppCompatDark);
        setContentView(R.layout.search_biblia);
        akafist = findViewById(R.id.TextView);
        ListView listView = findViewById(R.id.ListView);
        editText = findViewById(R.id.editText);
        ImageView buttonx = findViewById(R.id.buttonx);
        if (dzenNoch) {
            buttonx.setImageResource(R.drawable.cancel);
        }
        buttonx.setOnClickListener(v -> {
            if (SystemClock.elapsedRealtime() - mLastClickTime < 1000){
                return;
            }
            mLastClickTime = SystemClock.elapsedRealtime();
            editText.setText("");
            adapter.clear();
            akafist.setText(getResources().getString(R.string.seash, 0));
            SharedPreferences.Editor prefEditors = chin.edit();
            prefEditors.putString("search_svityx_string", "");
            prefEditors.apply();
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            Objects.requireNonNull(imm).toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
        });
        akafist.setTextSize(TypedValue.COMPLEX_UNIT_SP, SettingsActivity.GET_FONT_SIZE_MIN - 2);
        arrayRes = new ArrayList<>();
        if (!Objects.requireNonNull(chin.getString("search_svityx_string", "")).equals("")) {
            if (savedInstanceState == null) {
                Gson gson = new Gson();
                String json = chin.getString("search_svityx_array", "");
                Type type = new TypeToken<ArrayList<String>>() {
                }.getType();
                arrayRes.addAll(Objects.requireNonNull(gson.fromJson(json, type)));
                akafist.setText(getResources().getString(R.string.seash, arrayRes.size()));
                for (int i = 0; i < arrayRes.size(); i++) {
                    if (dzenNoch)
                        arrayRes.set(i, arrayRes.get(i).replace("#d00505", "#f44336"));
                    else
                        arrayRes.set(i, arrayRes.get(i).replace("#f44336", "#d00505"));
                }
                editText.setText(chin.getString("search_svityx_string", ""));
                int editPosition = Objects.requireNonNull(editText.getText()).length();
                editText.setSelection(editPosition);
            }
        }
        adapter = new search_ListAdapter(this, arrayRes);
        listView.setAdapter(adapter);
        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int i) {
                if (i == 1) {
                    // Скрываем клавиатуру
                    InputMethodManager imm1 = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    Objects.requireNonNull(imm1).hideSoftInputFromWindow(editText.getWindowToken(), 0);
                }
            }

            @Override
            public void onScroll(AbsListView absListView, int i, int i1, int i2) {
            }
        });
        akafist.setText(getResources().getString(R.string.seash, adapter.getCount()));
        if (dzenNoch) {
            akafist.setTextColor(ContextCompat.getColor(this, R.color.colorIcons));
        }
        listView.setOnItemClickListener((adapterView, view, position, l) -> {
            if (SystemClock.elapsedRealtime() - mLastClickTime < 1000){
                return;
            }
            mLastClickTime = SystemClock.elapsedRealtime();
            String result = arrayRes.get(position);
            int t1 = result.indexOf("<!--");
            int t2 = result.indexOf(":");
            int t3 = result.indexOf("-->");
            GregorianCalendar g = new GregorianCalendar(c.get(Calendar.YEAR), Integer.parseInt(result.substring(t2 + 1, t3)), Integer.parseInt(result.substring(t1 + 4, t2)));
            Intent intent = new Intent();
            intent.putExtra("data", g.get(Calendar.DAY_OF_YEAR) - 1);
            setResult(140, intent);
            finish();
        });

        try {
            getFile();
        } catch (IOException ignored) {
        }
        Menu_cviaty.getPrazdnik(this, c.get(Calendar.YEAR));
        editText.addTextChangedListener(new MyTextWatcher());

        setTollbarTheme();
    }

    private void getFile() throws IOException {
        SparseIntArray sparseArray = new SparseIntArray();
        sparseArray.append(0, R.raw.caliandar0);
        sparseArray.append(1, R.raw.caliandar1);
        sparseArray.append(2, R.raw.caliandar2);
        sparseArray.append(3, R.raw.caliandar3);
        sparseArray.append(4, R.raw.caliandar4);
        sparseArray.append(5, R.raw.caliandar5);
        sparseArray.append(6, R.raw.caliandar6);
        sparseArray.append(7, R.raw.caliandar7);
        sparseArray.append(8, R.raw.caliandar8);
        sparseArray.append(9, R.raw.caliandar9);
        sparseArray.append(10, R.raw.caliandar10);
        sparseArray.append(11, R.raw.caliandar11);
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < sparseArray.size(); i++) {
            InputStream inputStream = getResources().openRawResource(sparseArray.get(i));
            InputStreamReader isr = new InputStreamReader(inputStream);
            BufferedReader reader = new BufferedReader(isr);
            String line;
            while ((line = reader.readLine()) != null) {
                if (sparseArray.keyAt(i) > 0) {
                    int t1 = line.indexOf("[");
                    line = line.substring(t1 + 1);
                }
                if (sparseArray.keyAt(i) < 11) {
                    int t1 = line.lastIndexOf("]");
                    line = line.substring(0, t1) + ",";
                }
                builder.append(line).append("\n");
            }
            inputStream.close();
        }
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<ArrayList<String>>>() {
        }.getType();
        arrayLists = gson.fromJson(builder.toString(), type);
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
        title_toolbar.setText(getResources().getText(R.string.search_svityia));
        if (dzenNoch) {
            toolbar.setPopupTheme(R.style.AppCompatDark);
            toolbar.setBackgroundResource(R.color.colorprimary_material_dark);
            title_toolbar.setBackgroundResource(R.color.colorprimary_material_dark);
            title_toolbar.setTextColor(ContextCompat.getColor(this, R.color.colorIcons));
        } else {
            toolbar.setBackgroundResource(R.color.colorPrimary);
            title_toolbar.setBackgroundResource(R.color.colorPrimary);
        }
    }

    private void stopPosukPesen() {
        if (PosukPesenTimer != null) {
            PosukPesenTimer.cancel();
            PosukPesenTimer = null;
        }
        PosukPesenSchedule = null;
    }

    private void startPosukPesen(String poshuk) {
        if (PosukPesenTimer == null) {
            PosukPesenTimer = new Timer();
            if (PosukPesenSchedule != null) {
                PosukPesenSchedule.cancel();
                PosukPesenSchedule = null;
            }
            PosukPesenSchedule = new TimerTask() {
                @Override
                public void run() {
                    runOnUiThread(() -> RawAsset(poshuk));
                }
            };
            PosukPesenTimer.schedule(PosukPesenSchedule, 0);
        }
    }

    private void RawAsset(String poshuk) {
        String posukOrig = poshuk;
        arrayRes.clear();
        adapter.notifyDataSetChanged();
        if (!poshuk.equals("")) {
            poshuk = poshuk.toLowerCase();
            poshuk = poshuk.replace("ё", "е");
            poshuk = poshuk.replace("сві", "сьві");
            poshuk = poshuk.replace("свя", "сьвя");
            poshuk = poshuk.replace("зме", "зьме");
            poshuk = poshuk.replace("змі", "зьмі");
            poshuk = poshuk.replace("змя", "зьмя");
            poshuk = poshuk.replace("зня", "зьня");
            poshuk = poshuk.replace("сле", "сьле");
            poshuk = poshuk.replace("слі", "сьлі");
            poshuk = poshuk.replace("сль", "сьль");
            poshuk = poshuk.replace("слю", "сьлю");
            poshuk = poshuk.replace("сля", "сьля");
            poshuk = poshuk.replace("сне", "сьне");
            poshuk = poshuk.replace("сні", "сьні");
            poshuk = poshuk.replace("сню", "сьню");
            poshuk = poshuk.replace("сня", "сьня");
            poshuk = poshuk.replace("спе", "сьпе");
            poshuk = poshuk.replace("спі", "сьпі");
            poshuk = poshuk.replace("спя", "сьпя");
            poshuk = poshuk.replace("сце", "сьце");
            poshuk = poshuk.replace("сці", "сьці");
            poshuk = poshuk.replace("сць", "сьць");
            poshuk = poshuk.replace("сцю", "сьцю");
            poshuk = poshuk.replace("сця", "сьця");
            poshuk = poshuk.replace("цце", "цьце");
            poshuk = poshuk.replace("цці", "цьці");
            poshuk = poshuk.replace("ццю", "цьцю");
            poshuk = poshuk.replace("ззе", "зьзе");
            poshuk = poshuk.replace("ззі", "зьзі");
            poshuk = poshuk.replace("ззю", "зьзю");
            poshuk = poshuk.replace("ззя", "зьзя");
            poshuk = poshuk.replace("зле", "зьле");
            poshuk = poshuk.replace("злі", "зьлі");
            poshuk = poshuk.replace("злю", "зьлю");
            poshuk = poshuk.replace("зля", "зьля");
            poshuk = poshuk.replace("збе", "зьбе");
            poshuk = poshuk.replace("збі", "зьбі");
            poshuk = poshuk.replace("збя", "зьбя");
            poshuk = poshuk.replace("нне", "ньне");
            poshuk = poshuk.replace("нні", "ньні");
            poshuk = poshuk.replace("нню", "ньню");
            poshuk = poshuk.replace("ння", "ньня");
            poshuk = poshuk.replace("лле", "льле");
            poshuk = poshuk.replace("ллі", "льлі");
            poshuk = poshuk.replace("ллю", "льлю");
            poshuk = poshuk.replace("лля", "льля");
            poshuk = poshuk.replace("дск", "дзк");

            char[] m = {'у', 'е', 'а', 'о', 'э', 'я', 'і', 'ю', 'ў', 'ь', 'ы'};
            for (char aM : m) {
                int r = poshuk.length() - 1;
                if (r >= 3) {
                    if (poshuk.charAt(r) == aM) {
                        poshuk = poshuk.replace(poshuk, poshuk.substring(0, r));
                    }
                }
            }
            String color = "<font color=#d00505>";
            if (dzenNoch)
                color = "<font color=#f44336>";
            for (int e = 0; e < arrayLists.size(); e++) {
                String[] sviatyia = arrayLists.get(e).get(4).split("<br>");
                for (String aSviatyia : sviatyia) {
                    if (aSviatyia.toLowerCase().replace("ё", "е").contains(poshuk.toLowerCase())) {
                        aSviatyia = aSviatyia.replace("<font color=#d00505>", "");
                        aSviatyia = aSviatyia.replace("</font>", "");
                        aSviatyia = aSviatyia.replace("<strong>", "");
                        aSviatyia = aSviatyia.replace("</strong>", "");
                        int t1 = aSviatyia.toLowerCase().replace("ё", "е").indexOf(poshuk.toLowerCase());
                        int t2 = poshuk.toLowerCase().length();
                        aSviatyia = aSviatyia.substring(0, t1) + color + aSviatyia.substring(t1, t1 + t2) + "</font>" + aSviatyia.substring(t1 + t2);
                        GregorianCalendar g = new GregorianCalendar(Integer.parseInt(arrayLists.get(e).get(3)), Integer.parseInt(arrayLists.get(e).get(2)), Integer.parseInt(arrayLists.get(e).get(1)));
                        String res = "<!--" + g.get(Calendar.DAY_OF_MONTH) + ":" + g.get(Calendar.MONTH) + "--><em>" + arrayLists.get(e).get(1) + " " + munName[Integer.parseInt(arrayLists.get(e).get(2))] + "</em><br>" + aSviatyia;
                        arrayRes.add(res);
                    }
                }
            }
            for (int e = 0; e < Menu_cviaty.opisanie.size(); e++) {
                if (Menu_cviaty.opisanie.get(e).toLowerCase().replace("ё", "е").contains(poshuk.toLowerCase())) {
                    String result = Menu_cviaty.opisanie.get(e);
                    int t1 = result.indexOf("<!--");
                    int t2 = result.indexOf(":");
                    int t3 = result.indexOf("-->");
                    String bold = "", boldEnd = "", em = "", emEnd = "";
                    int t6 = result.indexOf("<!--", t3);
                    if (t6 != -1) {
                        int t7 = result.indexOf("-->", t6);
                        String res1 = result.substring(t6 + 4, t7);
                        if (res1.contains("1")) {
                            bold = color + "<strong>";
                            boldEnd = "</strong></font>";
                        }
                        if (res1.contains("2")) {
                            bold = color;
                            boldEnd = "</font>";
                        }
                        if (res1.contains("3")) {
                            em = "<em>";
                            emEnd = "</em>";
                        }
                    }
                    GregorianCalendar g = new GregorianCalendar(c.get(Calendar.YEAR), Integer.parseInt(result.substring(t2 + 1, t3)), Integer.parseInt(result.substring(t1 + 4, t2)));
                    String aSviatyia = result.substring(t3 + 3);
                    int t4 = aSviatyia.toLowerCase().replace("ё", "е").indexOf(poshuk.toLowerCase());
                    int t5 = poshuk.toLowerCase().length();
                    aSviatyia = aSviatyia.substring(0, t4) + color + aSviatyia.substring(t4, t4 + t5) + "</font>" + aSviatyia.substring(t4 + t5);
                    String res = result.substring(t1, t3 + 3) + "<em>" + bold + g.get(Calendar.DATE) + " " + munName[g.get(Calendar.MONTH)] + "</em>" + boldEnd + "<br>" + em + aSviatyia + emEnd;
                    arrayRes.add(res);
                }
            }
        }
        akafist.setText(getResources().getString(R.string.seash, arrayRes.size()));

        Gson gson = new Gson();
        String json = gson.toJson(arrayRes);
        SharedPreferences.Editor prefEditors = chin.edit();
        prefEditors.putString("search_svityx_array", json);
        prefEditors.putString("search_svityx_string", posukOrig);
        prefEditors.apply();
        adapter.notifyDataSetChanged();
    }

    private class MyTextWatcher implements TextWatcher {

        private int editPosition;
        private int check = 0;
        private boolean editch = true;

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            editch = count != after;
            check = after;
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            editPosition = start + count;
        }

        @Override
        public void afterTextChanged(Editable s) {
            if (editch) {
                String edit = s.toString();
                edit = edit.replace("и", "і");
                edit = edit.replace("щ", "ў");
                edit = edit.replace("ъ", "'");
                if (edit.length() >= 3) {
                    stopPosukPesen();
                    startPosukPesen(edit);
                } else {
                    arrayRes.clear();
                    adapter.notifyDataSetChanged();
                    akafist.setText(getResources().getString(R.string.seash, 0));
                }
                if (check != 0) {
                    editText.removeTextChangedListener(this);
                    editText.setText(edit);
                    editText.setSelection(editPosition);
                    editText.addTextChangedListener(this);
                }
            }
        }
    }

    private static class search_ListAdapter extends ArrayAdapter<String> {

        private final Activity mContext;
        private final SharedPreferences k;
        private final ArrayList<String> adapterList;

        search_ListAdapter(@NonNull Activity context, ArrayList<String> adapterList) {
            super(context, R.layout.simple_list_item_2, R.id.label, adapterList);
            this.adapterList = adapterList;
            mContext = context;
            k = context.getSharedPreferences("biblia", Context.MODE_PRIVATE);
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View mView, @NonNull ViewGroup parent) {
            ViewHolder viewHolder;
            if (mView == null) {
                mView = mContext.getLayoutInflater().inflate(R.layout.simple_list_item_2, parent, false);
                viewHolder = new ViewHolder();
                mView.setTag(viewHolder);
                viewHolder.text = mView.findViewById(R.id.label);
            } else {
                viewHolder = (ViewHolder) mView.getTag();
            }
            boolean dzenNoch = k.getBoolean("dzen_noch", false);

            viewHolder.text.setText(MainActivity.fromHtml(adapterList.get(position)));
            viewHolder.text.setTextSize(TypedValue.COMPLEX_UNIT_SP, SettingsActivity.GET_FONT_SIZE_MIN);

            if (dzenNoch) {
                viewHolder.text.setBackgroundDrawable(ContextCompat.getDrawable(mContext, R.drawable.selector_dark));
                viewHolder.text.setTextColor(ContextCompat.getColor(mContext, R.color.colorIcons));
                viewHolder.text.setCompoundDrawablesWithIntrinsicBounds(R.drawable.stiker_black, 0, 0, 0);
            }
            return mView;
        }
    }

    private static class ViewHolder {
        TextView_Roboto_Condensed text;
    }
}
