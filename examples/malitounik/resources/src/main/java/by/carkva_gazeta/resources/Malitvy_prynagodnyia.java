package by.carkva_gazeta.resources;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.SystemClock;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.style.AbsoluteSizeSpan;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;

import by.carkva_gazeta.malitounik.EditText_Roboto_Condensed;
import by.carkva_gazeta.malitounik.MainActivity;
import by.carkva_gazeta.malitounik.Menu_list_data;
import by.carkva_gazeta.malitounik.Menu_list_data_sort;
import by.carkva_gazeta.malitounik.SettingsActivity;
import by.carkva_gazeta.malitounik.TextView_Roboto_Condensed;

/**
 * Created by oleg on 30.5.16
 */
public class Malitvy_prynagodnyia extends AppCompatActivity {

    private final ArrayList<Menu_list_data> data = new ArrayList<>();
    private EditText_Roboto_Condensed editText;
    private TextView_Roboto_Condensed textView;
    private Menu_ListAdaprer adapter;
    private long mLastClickTime = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        SharedPreferences chin = getSharedPreferences("biblia", MODE_PRIVATE);
        boolean dzenNoch = chin.getBoolean("dzen_noch", false);
        if (dzenNoch) setTheme(by.carkva_gazeta.malitounik.R.style.AppCompatDark);
        super.onCreate(savedInstanceState);

        setContentView(by.carkva_gazeta.malitounik.R.layout.akafist_list);
        textView = findViewById(by.carkva_gazeta.malitounik.R.id.count);
        editText = findViewById(by.carkva_gazeta.malitounik.R.id.textSearch);
        editText.addTextChangedListener(new TextWatcher() {

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
                String edit = s.toString();
                if (editch) {
                    edit = edit.replace("и", "і");
                    edit = edit.replace("щ", "ў");
                    edit = edit.replace("ъ", "'");
                    edit = edit.replace("И", "І");
                    edit = edit.replace("Щ", "Ў");
                    edit = edit.replace("Ъ", "'");
                    if (check != 0) {
                        editText.removeTextChangedListener(this);
                        editText.setText(edit);
                        editText.setSelection(editPosition);
                        editText.addTextChangedListener(this);
                    }
                }
                adapter.getFilter().filter(edit);
            }
        });
        if (savedInstanceState != null && savedInstanceState.getBoolean("edittext")) {
            editText.setVisibility(View.VISIBLE);
        }
        Toolbar toolbar = findViewById(by.carkva_gazeta.malitounik.R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        TextView_Roboto_Condensed title_toolbar = findViewById(by.carkva_gazeta.malitounik.R.id.title_toolbar);
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
        title_toolbar.setText(getResources().getText(by.carkva_gazeta.malitounik.R.string.prynagodnyia));
        if (dzenNoch) {
            toolbar.setPopupTheme(by.carkva_gazeta.malitounik.R.style.AppCompatDark);
            toolbar.setBackgroundResource(by.carkva_gazeta.malitounik.R.color.colorprimary_material_dark);
        }
        ListView listView = findViewById(by.carkva_gazeta.malitounik.R.id.ListView);
        data.add(new Menu_list_data(R.raw.prynagodnyia_0, "Малітва аб блаславеньні"));
        data.add(new Menu_list_data(R.raw.prynagodnyia_1, "Малітва аб дапамозе ў выбары жыцьцёвай дарогі дзіцяці"));
        data.add(new Menu_list_data(R.raw.prynagodnyia_2, "Малітва аб еднасьці"));
        data.add(new Menu_list_data(R.raw.prynagodnyia_3, "Малітва бацькоў за дзяцей 2"));
        data.add(new Menu_list_data(R.raw.prynagodnyia_4, "Малітва бацькоў за дзяцей"));
        data.add(new Menu_list_data(R.raw.prynagodnyia_5, "Малітва вадзіцеля"));
        data.add(new Menu_list_data(R.raw.prynagodnyia_6, "Малітва вучня"));
        data.add(new Menu_list_data(R.raw.prynagodnyia_7, "Малітва да Маці Божай Браслаўскай, Валадаркі Азёраў"));
        data.add(new Menu_list_data(R.raw.prynagodnyia_8, "Малітва да Маці Божай Будслаўскай, Апякункі Беларусі"));
        data.add(new Menu_list_data(R.raw.prynagodnyia_9, "Малітва да Маці Божай Нястомнай Дапамогі"));
        data.add(new Menu_list_data(R.raw.prynagodnyia_10, "Малітва за Беларусь"));
        data.add(new Menu_list_data(R.raw.prynagodnyia_11, "Малітва за дарослых дзяцей"));
        data.add(new Menu_list_data(R.raw.prynagodnyia_12, "Малітва за дзяцей перад пачаткам навукі"));
        data.add(new Menu_list_data(R.raw.prynagodnyia_13, "Малітва за парафію"));
        data.add(new Menu_list_data(R.raw.prynagodnyia_14, "Малітва за хворага"));
        data.add(new Menu_list_data(R.raw.prynagodnyia_15, "Малітва за хворае дзіця"));
        data.add(new Menu_list_data(R.raw.prynagodnyia_16, "Малітва за хрысьціянскую еднасьць"));
        data.add(new Menu_list_data(R.raw.prynagodnyia_17, "Малітва за ўмацаваньне ў любові"));
        data.add(new Menu_list_data(R.raw.prynagodnyia_18, "Малітва маладога чалавека"));
        data.add(new Menu_list_data(R.raw.prynagodnyia_19, "Малітва на ўсякую патрэбу"));
        data.add(new Menu_list_data(R.raw.prynagodnyia_20, "Малітва падзякі за атрыманыя дабрадзействы"));
        data.add(new Menu_list_data(R.raw.prynagodnyia_21, "Малітва перад пачаткам навучаньня"));
        data.add(new Menu_list_data(R.raw.prynagodnyia_22, "Малітва перад іспытамі"));
        data.add(new Menu_list_data(R.raw.prynagodnyia_23, "Малітва ранішняга намеру (Опціных старцаў)"));
        data.add(new Menu_list_data(R.raw.prynagodnyia_24, "Малітвы за сьвятароў і сьвятарскія пакліканьні"));
        data.add(new Menu_list_data(R.raw.prynagodnyia_25, "Малітвы ў часе хваробы і за хворых"));
        data.add(new Menu_list_data(R.raw.prynagodnyia_26, "Намер ісьці за Хрыстом"));
        data.add(new Menu_list_data(R.raw.prynagodnyia_27, "Цябе, Бога, хвалім"));
        Collections.sort(data, new Menu_list_data_sort());
        adapter = new Menu_ListAdaprer(this);
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
        listView.setOnItemClickListener((adapterView, view, position, id) -> {
            if (SystemClock.elapsedRealtime() - mLastClickTime < 1000) {
                return;
            }
            mLastClickTime = SystemClock.elapsedRealtime();
            Intent intent = new Intent(Malitvy_prynagodnyia.this, prynagodnyia.class);
            intent.putExtra("prynagodnyia", data.get(position).data);
            intent.putExtra("prynagodnyiaID", data.get(position).id);
            startActivity(intent);
            InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
            Objects.requireNonNull(imm).hideSoftInputFromWindow(editText.getWindowToken(), 0);
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuInflater infl = getMenuInflater();
        infl.inflate(by.carkva_gazeta.malitounik.R.menu.malitvy_prynagodnyia, menu);
        for (int i = 0; i < menu.size(); i++) {
            MenuItem item = menu.getItem(i);
            SpannableString spanString = new SpannableString(menu.getItem(i).getTitle().toString());
            int end = spanString.length();
            spanString.setSpan(new AbsoluteSizeSpan(SettingsActivity.GET_FONT_SIZE_MIN, true), 0, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            item.setTitle(spanString);
        }
        return true;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == by.carkva_gazeta.malitounik.R.id.action_seashe_text) {
            textView.setVisibility(View.VISIBLE);
            textView.setText("(" + data.size() + ")");
            editText.setVisibility(View.VISIBLE);
            editText.requestFocus();
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            Objects.requireNonNull(imm).toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (editText.getVisibility() == View.VISIBLE) {
            editText.setText("");
            editText.setVisibility(View.GONE);
            textView.setVisibility(View.GONE);
            InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
            Objects.requireNonNull(imm).hideSoftInputFromWindow(editText.getWindowToken(), 0);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        if (editText.getVisibility() == View.VISIBLE) {
            outState.putBoolean("edittext", true);
        } else {
            outState.putBoolean("edittext", false);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!MainActivity.checkBrightness) {
            WindowManager.LayoutParams lp = getWindow().getAttributes();
            lp.screenBrightness = (float) MainActivity.brightness / 100;
            getWindow().setAttributes(lp);
        }
        overridePendingTransition(by.carkva_gazeta.malitounik.R.anim.alphain, by.carkva_gazeta.malitounik.R.anim.alphaout);
    }

    class Menu_ListAdaprer extends ArrayAdapter<Menu_list_data> {

        private final SharedPreferences k;
        private final ArrayList<Menu_list_data> origData;
        private final Activity context;

        Menu_ListAdaprer(@NonNull Activity context) {
            super(context, by.carkva_gazeta.malitounik.R.layout.simple_list_item_2, by.carkva_gazeta.malitounik.R.id.label, data);
            k = context.getSharedPreferences("biblia", Context.MODE_PRIVATE);
            origData = new ArrayList<>(data);
            this.context = context;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View mView, @NonNull ViewGroup parent) {
            ViewHolder viewHolder;
            if (mView == null) {
                mView = context.getLayoutInflater().inflate(by.carkva_gazeta.malitounik.R.layout.simple_list_item_2, parent, false);
                viewHolder = new ViewHolder();
                mView.setTag(viewHolder);
                viewHolder.text = mView.findViewById(by.carkva_gazeta.malitounik.R.id.label);
            } else {
                viewHolder = (ViewHolder) mView.getTag();
            }

            boolean dzenNoch = k.getBoolean("dzen_noch", false);

            viewHolder.text.setText(data.get(position).data);

            viewHolder.text.setTextSize(TypedValue.COMPLEX_UNIT_SP, SettingsActivity.GET_FONT_SIZE_MIN);

            if (dzenNoch) {
                viewHolder.text.setBackgroundDrawable(ContextCompat.getDrawable(context, by.carkva_gazeta.malitounik.R.drawable.selector_dark));
                viewHolder.text.setTextColor(ContextCompat.getColor(context, by.carkva_gazeta.malitounik.R.color.colorIcons));
                viewHolder.text.setCompoundDrawablesWithIntrinsicBounds(by.carkva_gazeta.malitounik.R.drawable.stiker_black, 0, 0, 0);
            }
            return mView;
        }

        @NonNull
        @Override
        public Filter getFilter() {
            return new Filter() {

                @Override
                protected FilterResults performFiltering(CharSequence constraint) {
                    constraint = constraint.toString().toLowerCase();
                    FilterResults result = new FilterResults();

                    if (constraint.toString().length() > 0) {
                        ArrayList<Menu_list_data> founded = new ArrayList<>();
                        for (Menu_list_data item : origData) {
                            if (item.data.toLowerCase().contains(constraint)) {
                                founded.add(item);
                            }
                        }

                        result.values = founded;
                        result.count = founded.size();
                    } else {
                        result.values = origData;
                        result.count = origData.size();
                    }
                    return result;
                }

                @SuppressWarnings("unchecked")
                @SuppressLint("SetTextI18n")
                @Override
                protected void publishResults(CharSequence constraint, FilterResults results) {
                    clear();
                    for (Menu_list_data item : (ArrayList<Menu_list_data>) results.values) {
                        add(item);
                    }
                    if (results.count > 0)
                        textView.setText("(" + results.count + ")");
                    else
                        textView.setText(by.carkva_gazeta.malitounik.R.string.niama);
                    notifyDataSetChanged();
                }
            };
        }
    }

    private static class ViewHolder {
        TextView_Roboto_Condensed text;
    }
}
