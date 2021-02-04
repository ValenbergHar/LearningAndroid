package by.carkva_gazeta.resources;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.SystemClock;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupMenu;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import com.google.gson.Gson;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

import by.carkva_gazeta.malitounik.Dialog_context_menu;
import by.carkva_gazeta.malitounik.MainActivity;
import by.carkva_gazeta.malitounik.MaranAta_Global_List;
import by.carkva_gazeta.malitounik.SettingsActivity;
import by.carkva_gazeta.malitounik.TextView_Roboto_Condensed;

@SuppressWarnings("ResultOfMethodCallIgnored")
public class bible_natatki extends AppCompatActivity implements AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener, Dialog_zakladka_delite.ZakladkaDeliteListiner, Dialog_delite_all_zakladki_i_natatki.Dialog_delite_all_zakladki_i_natatki_Listener, Dialog_bible_natatka_edit.bible_natatka_edit_listiner, Dialog_context_menu.Dialog_context_menu_Listener {

    private ArrayList<ArrayList<String>> data;
    private ListAdaprer adapter;
    private int semuxa = 1;
    private boolean dzenNoch;
    private ListView listView;
    private TextView_Roboto_Condensed help;
    private long mLastClickTime = 0;

    @Override
    public void setEdit() {
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onDialogEditClick(int position) {
        Dialog_bible_natatka_edit natatka = Dialog_bible_natatka_edit.getInstance(semuxa, position);
        natatka.show(getSupportFragmentManager(), "bible_natatka_edit");
    }

    @Override
    public void onDialogDeliteClick(int position, String name) {
        Dialog_zakladka_delite delite = Dialog_zakladka_delite.getInstance(position, name, semuxa, false);
        delite.show(getSupportFragmentManager(), "zakladka_delite");
    }

    @Override
    public void file_all_natatki_albo_zakladki(int semuxa) {
        if (semuxa == 1) {
            MaranAta_Global_List.getNatatkiSemuxa().removeAll(MaranAta_Global_List.getNatatkiSemuxa());
            adapter.notifyDataSetChanged();
            File fileNatatki = new File(getFilesDir() + "/BibliaSemuxaNatatki.json");
            if (fileNatatki.exists()) {
                fileNatatki.delete();
            }
        }
        if (semuxa == 2) {
            MaranAta_Global_List.getNatatkiSinodal().removeAll(MaranAta_Global_List.getNatatkiSinodal());
            adapter.notifyDataSetChanged();
            File fileNatatki = new File(getFilesDir() + "/BibliaSinodalNatatki.json");
            if (fileNatatki.exists()) {
                fileNatatki.delete();
            }
        }
        /*if (semuxa == 3) {
            MaranAta_Global_List.getNatatkiPsalterNadsana().removeAll(MaranAta_Global_List.getNatatkiPsalterNadsana());
            adapter.notifyDataSetChanged();
            File fileNatatki = new File(getFilesDir() + "/PsalterNadsanNatatki.json");
            if (fileNatatki.exists()) {
                fileNatatki.delete();
            }
        }*/
        help.setVisibility(View.VISIBLE);
        listView.setVisibility(View.GONE);
        invalidateOptionsMenu();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!MainActivity.checkBrightness) {
            WindowManager.LayoutParams lp = getWindow().getAttributes();
            lp.screenBrightness = (float) MainActivity.brightness / 100;
            getWindow().setAttributes(lp);
        }
        SharedPreferences k = getSharedPreferences("biblia", MODE_PRIVATE);
        dzenNoch = k.getBoolean("dzen_noch", false);
        if (dzenNoch) setTheme(by.carkva_gazeta.malitounik.R.style.AppCompatDark);
        setContentView(by.carkva_gazeta.malitounik.R.layout.akafist_list);
        listView = findViewById(by.carkva_gazeta.malitounik.R.id.ListView);
        if (getIntent() != null) {
            semuxa = getIntent().getIntExtra("semuxa", 1);
        }
        if (semuxa == 1)
            data = MaranAta_Global_List.getNatatkiSemuxa();
        if (semuxa == 2)
            data = MaranAta_Global_List.getNatatkiSinodal();
        //if (semuxa == 3)
        //    data = MaranAta_Global_List.getNatatkiPsalterNadsana();
        adapter = new ListAdaprer(this, data);
        help = findViewById(by.carkva_gazeta.malitounik.R.id.help);
        if (data.size() == 0) {
            help.setVisibility(View.VISIBLE);
            listView.setVisibility(View.GONE);
        }
        if (dzenNoch)
            help.setTextColor(ContextCompat.getColor(this, by.carkva_gazeta.malitounik.R.color.colorIcons));
        listView.setAdapter(adapter);
        listView.setVerticalScrollBarEnabled(false);
        listView.setOnItemClickListener(this);
        listView.setOnItemLongClickListener(this);
    }

    private void setTollbarTheme() {
        Toolbar toolbar = findViewById(by.carkva_gazeta.malitounik.R.id.toolbar);
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
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        title_toolbar.setText(by.carkva_gazeta.malitounik.R.string.natatki_biblii);
        if (dzenNoch) {
            toolbar.setPopupTheme(by.carkva_gazeta.malitounik.R.style.AppCompatDark);
            toolbar.setBackgroundResource(by.carkva_gazeta.malitounik.R.color.colorprimary_material_dark);
        }
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        if (data.size() == 0) {
            menu.findItem(by.carkva_gazeta.malitounik.R.id.trash).setVisible(false);
        } else {
            menu.findItem(by.carkva_gazeta.malitounik.R.id.trash).setVisible(true);
        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuInflater infl = getMenuInflater();
        infl.inflate(by.carkva_gazeta.malitounik.R.menu.zakladki_i_natatki, menu);
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
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            onBackPressed();
            return true;
        }
        if (id == by.carkva_gazeta.malitounik.R.id.trash) {
            Dialog_delite_all_zakladki_i_natatki natatki = Dialog_delite_all_zakladki_i_natatki.getInstance(getResources().getString(by.carkva_gazeta.malitounik.R.string.natatki_biblii).toLowerCase(), semuxa);
            natatki.show(getSupportFragmentManager(), "delite_all_zakladki_i_natatki");
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onResume() {
        super.onResume();
        setTollbarTheme();
        overridePendingTransition(by.carkva_gazeta.malitounik.R.anim.alphain, by.carkva_gazeta.malitounik.R.anim.alphaout);
    }

    @Override
    public void zakladkadiliteItem(int position, int semuxa) {
    }

    @Override
    public void natatkidiliteItem(int position, int semuxa) {
        if (semuxa == 1) {
            MaranAta_Global_List.getNatatkiSemuxa().remove(position);
            adapter.notifyDataSetChanged();
            File fileNatatki = new File(getFilesDir() + "/BibliaSemuxaNatatki.json");
            if (MaranAta_Global_List.getNatatkiSemuxa().size() == 0) {
                if (fileNatatki.exists()) {
                    fileNatatki.delete();
                }
                help.setVisibility(View.VISIBLE);
                listView.setVisibility(View.GONE);
            } else {
                Gson gson = new Gson();
                try {
                    FileWriter outputStream = new FileWriter(fileNatatki);
                    outputStream.write(gson.toJson(MaranAta_Global_List.getNatatkiSemuxa()));
                    outputStream.close();
                } catch (IOException ignored) {
                }
            }
        }
        if (semuxa == 2) {
            MaranAta_Global_List.getNatatkiSinodal().remove(position);
            adapter.notifyDataSetChanged();
            File fileNatatki = new File(getFilesDir() + "/BibliaSinodalNatatki.json");
            if (MaranAta_Global_List.getNatatkiSinodal().size() == 0) {
                if (fileNatatki.exists()) {
                    fileNatatki.delete();
                }
                help.setVisibility(View.VISIBLE);
                listView.setVisibility(View.GONE);
            } else {
                Gson gson = new Gson();
                try {
                    FileWriter outputStream = new FileWriter(fileNatatki);
                    outputStream.write(gson.toJson(MaranAta_Global_List.getNatatkiSinodal()));
                    outputStream.close();
                } catch (IOException ignored) {
                }
            }
        }
        /*if (semuxa == 3) {
            MaranAta_Global_List.getNatatkiPsalterNadsana().remove(position);
            adapter.notifyDataSetChanged();
            File fileNatatki = new File(getFilesDir() + "/PsalterNadsanNatatki.json");
            if (MaranAta_Global_List.getNatatkiPsalterNadsana().size() == 0) {
                if (fileNatatki.exists()) {
                    fileNatatki.delete();
                }
                help.setVisibility(View.VISIBLE);
                listView.setVisibility(View.GONE);
            } else {
                Gson gson = new Gson();
                try {
                    FileWriter outputStream = new FileWriter(fileNatatki);
                    outputStream.write(gson.toJson(MaranAta_Global_List.getNatatkiPsalterNadsana()));
                    outputStream.close();
                } catch (IOException ignored) {
                }
            }
        }*/
        invalidateOptionsMenu();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (SystemClock.elapsedRealtime() - mLastClickTime < 1000){
            return;
        }
        mLastClickTime = SystemClock.elapsedRealtime();
        int kniga = -1;
        int knigaS = -1;
        if (data.get(position).get(0).contains("1"))
            kniga = Integer.parseInt(data.get(position).get(1));
        else
            knigaS = Integer.parseInt(data.get(position).get(1));
        Intent intent = null;
        /*if (semuxa == 3) {
            intent = new Intent(this, nadsanContentActivity.class);
        } else {*/
        if (kniga != -1) {
            if (semuxa == 1) {
                intent = new Intent(this, novy_zapaviet3.class);
            }
            if (semuxa == 2) {
                intent = new Intent(this, novy_zapaviet_sinaidal3.class);
            }
            Objects.requireNonNull(intent).putExtra("kniga", kniga);
        }
        if (knigaS != -1) {
            if (semuxa == 1) {
                intent = new Intent(this, stary_zapaviet3.class);
                switch (knigaS) {
                    case 19:
                        knigaS = 16;
                        break;
                    case 20:
                        knigaS = 17;
                        break;
                    case 21:
                        knigaS = 18;
                        break;
                    case 22:
                        knigaS = 19;
                        break;
                    case 23:
                        knigaS = 20;
                        break;
                    case 24:
                        knigaS = 21;
                        break;
                    case 27:
                        knigaS = 22;
                        break;
                    case 28:
                        knigaS = 23;
                        break;
                    case 29:
                        knigaS = 24;
                        break;
                    case 32:
                        knigaS = 25;
                        break;
                    case 33:
                        knigaS = 26;
                        break;
                    case 34:
                        knigaS = 27;
                        break;
                    case 35:
                        knigaS = 28;
                        break;
                    case 36:
                        knigaS = 29;
                        break;
                    case 37:
                        knigaS = 30;
                        break;
                    case 38:
                        knigaS = 31;
                        break;
                    case 39:
                        knigaS = 32;
                        break;
                    case 40:
                        knigaS = 33;
                        break;
                    case 41:
                        knigaS = 34;
                        break;
                    case 42:
                        knigaS = 35;
                        break;
                    case 43:
                        knigaS = 36;
                        break;
                    case 44:
                        knigaS = 37;
                        break;
                    case 45:
                        knigaS = 38;
                        break;
                }
            }
            if (semuxa == 2) {
                intent = new Intent(this, stary_zapaviet_sinaidal3.class);
            }
            Objects.requireNonNull(intent).putExtra("kniga", knigaS);
        }
        //}
        Objects.requireNonNull(intent).putExtra("glava", Integer.valueOf(data.get(position).get(2)));
        intent.putExtra("stix", Integer.valueOf(data.get(position).get(3)));
        startActivityForResult(intent, 500);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if (requestCode == 500) {
            if (data.size() == 0) {
                help.setVisibility(View.VISIBLE);
                listView.setVisibility(View.GONE);
            }
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        Dialog_context_menu context_menu = Dialog_context_menu.getInstance(position, data.get(position).get(5));
        context_menu.show(getSupportFragmentManager(), "context_menu");
        return true;
    }

    private class ListAdaprer extends ArrayAdapter<ArrayList<String>> {

        private final Activity mContext;
        private final ArrayList<ArrayList<String>> itemsL;
        private final SharedPreferences k;

        ListAdaprer(@NonNull Activity context, @NonNull ArrayList<ArrayList<String>> strings) {
            super(context, by.carkva_gazeta.malitounik.R.layout.simple_list_item_3, by.carkva_gazeta.malitounik.R.id.label, strings);
            mContext = context;
            itemsL = strings;
            k = context.getSharedPreferences("biblia", Context.MODE_PRIVATE);
        }

        @Override
        public void add(@Nullable ArrayList<String> string) {
            super.add(string);
            itemsL.add(string);
        }

        @Override
        public void remove(@Nullable ArrayList<String> string) {
            super.remove(string);
            itemsL.remove(string);
        }

        @Override
        public void clear() {
            super.clear();
            itemsL.clear();
        }

        @SuppressLint("SetTextI18n")
        @NonNull
        @Override
        public View getView(int position, @Nullable View mView, @NonNull ViewGroup parent) {
            ViewHolder viewHolder;
            if (mView == null) {
                mView = mContext.getLayoutInflater().inflate(by.carkva_gazeta.malitounik.R.layout.simple_list_item_3, parent, false);
                viewHolder = new ViewHolder();
                mView.setTag(viewHolder);
                viewHolder.text = mView.findViewById(by.carkva_gazeta.malitounik.R.id.label);
                viewHolder.button_popup = mView.findViewById(by.carkva_gazeta.malitounik.R.id.button_popup);
            } else {
                viewHolder = (ViewHolder) mView.getTag();
            }

            boolean dzenNoch = k.getBoolean("dzen_noch", false);

            viewHolder.button_popup.setOnClickListener((v -> showPopupMenu(viewHolder.button_popup, position, itemsL.get(position).get(5))));

            viewHolder.text.setText(itemsL.get(position).get(4) + "\n\n" + itemsL.get(position).get(5));

            viewHolder.text.setTextSize(TypedValue.COMPLEX_UNIT_SP, SettingsActivity.GET_FONT_SIZE_MIN);

            if (dzenNoch) {
                viewHolder.text.setBackgroundDrawable(ContextCompat.getDrawable(mContext, by.carkva_gazeta.malitounik.R.drawable.selector_dark));
                viewHolder.text.setTextColor(ContextCompat.getColor(mContext, by.carkva_gazeta.malitounik.R.color.colorIcons));
                viewHolder.text.setCompoundDrawablesWithIntrinsicBounds(by.carkva_gazeta.malitounik.R.drawable.stiker_black, 0, 0, 0);
            }
            return mView;
        }
    }

    private void showPopupMenu(View view, int position, String name) {
        PopupMenu popup = new PopupMenu(this, view);
        MenuInflater infl = popup.getMenuInflater();
        infl.inflate(by.carkva_gazeta.malitounik.R.menu.popup, popup.getMenu());
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
                case by.carkva_gazeta.malitounik.R.id.menu_redoktor:
                    Dialog_bible_natatka_edit natatka = Dialog_bible_natatka_edit.getInstance(semuxa, position);
                    natatka.show(getSupportFragmentManager(), "bible_natatka_edit");
                    return true;
                case by.carkva_gazeta.malitounik.R.id.menu_remove:
                    Dialog_zakladka_delite delite = Dialog_zakladka_delite.getInstance(position, name, semuxa, false);
                    delite.show(getSupportFragmentManager(), "zakladka_delite");
                    return true;
            }
            return false;
        });
        popup.show();
    }

    private static class ViewHolder {
        TextView_Roboto_Condensed text;
        ImageView button_popup;
    }
}
