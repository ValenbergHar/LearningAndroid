package by.carkva_gazeta.malitounik;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.SystemClock;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.AbsoluteSizeSpan;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.PopupMenu;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.ListFragment;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;

/**
 * Created by oleg on 30.5.16
 */

public class Menu_vybranoe extends ListFragment {
    
    private MyVybranoeAdapter adapter;
    public static ArrayList<Vybranoe_data> vybranoe;
    private long mLastClickTime = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    void file_delite(int position) {
        vybranoe.remove(position);
        Gson gson = new Gson();
        File file = new File(Objects.requireNonNull(getActivity()).getFilesDir() + "/Vybranoe.json");
        try {
            FileWriter outputStream = new FileWriter(file);
            outputStream.write(gson.toJson(vybranoe));
            outputStream.close();
        } catch (IOException ignored) {
        }
        adapter.notifyDataSetChanged();
        //MyBackupAgent.requestBackup(getActivity());
    }

    void onDialogDeliteVybranoeClick(int position, String name) {
        Dialog_delite dd = Dialog_delite.getInstance(position, "", "з выбранага", name);
        dd.show(Objects.requireNonNull(getFragmentManager()), "dialog_dilite");
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Gson gson = new Gson();
        File file = new File(Objects.requireNonNull(getActivity()).getFilesDir() + "/Vybranoe.json");
        String sb = "";
        if (file.exists()) {
            try {
                FileReader inputStream = new FileReader(file);
                BufferedReader reader = new BufferedReader(inputStream);
                String line;
                while ((line = reader.readLine()) != null) {
                    sb = line;
                }
                inputStream.close();
            } catch (IOException ignored) {
            }
            Type type = new TypeToken<ArrayList<Vybranoe_data>>() {
            }.getType();
            vybranoe = gson.fromJson(sb, type);
        } else {
            vybranoe = new ArrayList<>();
        }
        Collections.sort(vybranoe, new Vybranoe_data_sort());
        adapter = new MyVybranoeAdapter(getActivity());
        setListAdapter(adapter);
        getListView().setVerticalScrollBarEnabled(false);

        getListView().setOnItemLongClickListener((parent, view, position, id) -> {
            Dialog_context_menu_vybranoe context_menu_vybranoe = Dialog_context_menu_vybranoe.getInstance(position, vybranoe.get(position).data);
            context_menu_vybranoe.show(Objects.requireNonNull(getFragmentManager()), "context_menu_vybranoe");
            return true;
        });
    }

    @Override
    public void onListItemClick(@NonNull ListView l, @NonNull View v, int position, long id) {
        if (SystemClock.elapsedRealtime() - mLastClickTime < 1000){
            return;
        }
        mLastClickTime = SystemClock.elapsedRealtime();
        super.onListItemClick(l, v, position, id);
        if (MainActivity.checkModule_resources(getActivity())) {
            try {
                Intent intent = new Intent(getActivity(), Class.forName("by.carkva_gazeta.resources.vybranoe_view"));
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                intent.putExtra("resurs", vybranoe.get(position).resurs);
                intent.putExtra("title", vybranoe.get(position).data);
                startActivity(intent);
            } catch (ClassNotFoundException ignored) {
            }
        } else {
            Dialog_install_Dadatak dadatak = new Dialog_install_Dadatak();
            dadatak.show(Objects.requireNonNull(getFragmentManager()), "dadatak");
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (SystemClock.elapsedRealtime() - mLastClickTime < 1000){
            return true;
        }
        mLastClickTime = SystemClock.elapsedRealtime();
        int id = item.getItemId();
        if (id == R.id.trash) {
            if (Menu_vybranoe.vybranoe.size() > 0) {
                SharedPreferences chin = Objects.requireNonNull(getActivity()).getSharedPreferences("biblia", Context.MODE_PRIVATE);
                boolean dzenNoch = chin.getBoolean("dzen_noch", false);
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                LinearLayout linearLayout = new LinearLayout(getActivity());
                linearLayout.setOrientation(LinearLayout.VERTICAL);
                TextView_Roboto_Condensed textViewZaglavie = new TextView_Roboto_Condensed(getActivity());
                if (dzenNoch)
                    textViewZaglavie.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.colorPrimary_black));
                else
                    textViewZaglavie.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.colorPrimary));
                float density = getResources().getDisplayMetrics().density;
                int realpadding = (int) (10 * density);
                textViewZaglavie.setPadding(realpadding, realpadding, realpadding, realpadding);
                textViewZaglavie.setText(getResources().getString(R.string.remove));
                textViewZaglavie.setTextSize(TypedValue.COMPLEX_UNIT_SP, SettingsActivity.GET_FONT_SIZE_MIN);
                textViewZaglavie.setTypeface(null, Typeface.BOLD);
                textViewZaglavie.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorIcons));
                linearLayout.addView(textViewZaglavie);
                TextView_Roboto_Condensed textView = new TextView_Roboto_Condensed(getActivity());
                textView.setText("Вы сапраўды жадаеце выдаліць усё Выбранае?");
                textView.setPadding(realpadding, realpadding, realpadding, realpadding);
                textView.setGravity(Gravity.CENTER_HORIZONTAL);
                textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, SettingsActivity.GET_FONT_SIZE_MIN);
                if (dzenNoch) textView.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorIcons));
                else textView.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorPrimary_text));
                linearLayout.addView(textView);
                builder.setView(linearLayout);
                builder.setPositiveButton(getResources().getString(R.string.ok), (dialog, arg1) -> {
                    vybranoe.clear();
                    Gson gson = new Gson();
                    File file = new File(getActivity().getFilesDir() + "/Vybranoe.json");
                    try {
                        FileWriter outputStream = new FileWriter(file);
                        outputStream.write(gson.toJson(vybranoe));
                        outputStream.close();
                    } catch (IOException ignored) {
                    }
                    adapter.notifyDataSetChanged();
                    //MyBackupAgent.requestBackup(getActivity());
                });
                builder.setNegativeButton(getResources().getString(R.string.CANCEL), (dialog, arg1) -> dialog.cancel());
                AlertDialog alert = builder.create();
                alert.setOnShowListener(dialog -> {
                    Button btnPositive = alert.getButton(Dialog.BUTTON_POSITIVE);
                    btnPositive.setTextSize(TypedValue.COMPLEX_UNIT_SP, SettingsActivity.GET_FONT_SIZE_MIN - 2);
                    Button btnNegative = alert.getButton(Dialog.BUTTON_NEGATIVE);
                    btnNegative.setTextSize(TypedValue.COMPLEX_UNIT_SP, SettingsActivity.GET_FONT_SIZE_MIN - 2);
                });
                alert.show();
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        onDialogDeliteVybranoeClick(id, vybranoe.get(id).data);
        return super.onContextItemSelected(item);
    }

    class MyVybranoeAdapter extends ArrayAdapter<Vybranoe_data> {

        private final SharedPreferences k;
        private final Activity activity;

        MyVybranoeAdapter(Activity activity) {
            super(activity, R.layout.simple_list_item_3, R.id.label, vybranoe);
            k = activity.getSharedPreferences("biblia", Context.MODE_PRIVATE);
            this.activity = activity;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View mView, @NonNull ViewGroup parent) {
            ViewHolder viewHolder;
            if (mView == null) {
                mView = activity.getLayoutInflater().inflate(R.layout.simple_list_item_3, parent, false);
                viewHolder = new ViewHolder();
                mView.setTag(viewHolder);
                viewHolder.text = mView.findViewById(R.id.label);
                viewHolder.button_popup = mView.findViewById(R.id.button_popup);
            } else {
                viewHolder = (ViewHolder) mView.getTag();
            }

            boolean dzenNoch = k.getBoolean("dzen_noch", false);

            viewHolder.button_popup.setOnClickListener((v -> showPopupMenu(viewHolder.button_popup, position, vybranoe.get(position).data)));

            viewHolder.text.setText(vybranoe.get(position).data);
            viewHolder.text.setTextSize(TypedValue.COMPLEX_UNIT_SP, SettingsActivity.GET_FONT_SIZE_MIN);

            if (dzenNoch) {
                viewHolder.text.setBackgroundDrawable(ContextCompat.getDrawable(activity, R.drawable.selector_dark));
                viewHolder.text.setTextColor(ContextCompat.getColor(activity, R.color.colorIcons));
                viewHolder.text.setCompoundDrawablesWithIntrinsicBounds(R.drawable.stiker_black, 0, 0, 0);
            }
            return mView;
        }

        private void showPopupMenu(View view, int position, String name) {
            PopupMenu popup = new PopupMenu(activity, view);
            MenuInflater infl = popup.getMenuInflater();
            infl.inflate(R.menu.popup, popup.getMenu());
            popup.getMenu().getItem(0).setVisible(false);
            for (int i = 0; i < popup.getMenu().size(); i++) {
                MenuItem item = popup.getMenu().getItem(i);
                SpannableString spanString = new SpannableString(popup.getMenu().getItem(i).getTitle().toString());
                int end = spanString.length();
                spanString.setSpan(new AbsoluteSizeSpan(SettingsActivity.GET_FONT_SIZE_MIN, true), 0, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                item.setTitle(spanString);
            }
            popup.setOnMenuItemClickListener(menuItem -> {
                popup.dismiss();
                if (menuItem.getItemId() == R.id.menu_remove) {
                    onDialogDeliteVybranoeClick(position, name);
                    return true;
                }
                return false;
            });
            popup.show();
        }
    }

    private static class ViewHolder {
        TextView_Roboto_Condensed text;
        ImageView button_popup;
    }
}
