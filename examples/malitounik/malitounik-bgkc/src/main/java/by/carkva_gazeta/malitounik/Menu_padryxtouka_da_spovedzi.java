package by.carkva_gazeta.malitounik;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.ListFragment;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Objects;

public class Menu_padryxtouka_da_spovedzi extends ListFragment {

    private ArrayAdapter<String> adapter;
    private SharedPreferences k;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    void onDialogFontSizePositiveClick() {
        adapter.notifyDataSetChanged();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_font) {
            Dialog_font_size dialog_font_size = new Dialog_font_size();
            dialog_font_size.show(Objects.requireNonNull(getFragmentManager()), "font");
        }
        if (id == R.id.action_bright) {
            Dialog_brightness dialog_brightness = new Dialog_brightness();
            dialog_brightness.show(Objects.requireNonNull(getFragmentManager()), "brightness");
        }
        if (id == R.id.action_dzen_noch) {
            item.setChecked(!item.isChecked());
            SharedPreferences.Editor prefEditor = k.edit();
            if (item.isChecked()) {
                prefEditor.putBoolean("dzen_noch", true);
            } else {
                prefEditor.putBoolean("dzen_noch", false);
            }
            prefEditor.apply();
            Objects.requireNonNull(getActivity()).recreate();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        k = Objects.requireNonNull(getActivity()).getSharedPreferences("biblia", Context.MODE_PRIVATE);
        boolean dzenNoch = k.getBoolean("dzen_noch", false);
        ListView listView = getListView();
        listView.setVerticalScrollBarEnabled(false);
        listView.setHorizontalScrollBarEnabled(false);
        ArrayList<String> data = new ArrayList<>();
        try {
            Resources r = getActivity().getResources();
            InputStream inputStream = r.openRawResource(R.raw.padryxtouka_da_spovedzi);
            InputStreamReader isr = new InputStreamReader(inputStream);
            BufferedReader reader = new BufferedReader(isr);
            String line;
            while ((line = reader.readLine()) != null) {
                if (dzenNoch)
                    line = line.replace("#d00505", "#f44336");
                data.add(line);
            }
            inputStream.close();
        } catch (Throwable ignored) {
        }
        adapter = new MyArrayAdapter(getActivity(), data);
        setListAdapter(adapter);
        getListView().setDivider(null);
        int pad = (int) (10 * getResources().getDisplayMetrics().density);
        getListView().setPadding(pad,pad,pad,pad);
    }

    private static class MyArrayAdapter extends ArrayAdapter<String> {

        private final ArrayList<String> list;
        private final Activity activity;

        MyArrayAdapter(Activity activity, @NonNull ArrayList<String> objects) {
            super(activity, R.layout.simple_list_item_maranata, objects);
            list = objects;
            this.activity = activity;
        }

        @Override
        public boolean isEnabled(int position) {
            return false;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable android.view.View convertView, @NonNull ViewGroup parent) {
            SharedPreferences k = activity.getSharedPreferences("biblia", Context.MODE_PRIVATE);
            ViewHolder ea;
            if (convertView == null) {
                ea = new ViewHolder();
                convertView = activity.getLayoutInflater().inflate(R.layout.simple_list_item_maranata, parent, false);
                ea.textView = convertView.findViewById(R.id.label);
                convertView.setTag(ea);
            } else {
                ea = (ViewHolder) convertView.getTag();
            }
            ea.textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, k.getInt("font_malitounik", SettingsActivity.GET_DEFAULT_FONT_SIZE));
            ea.textView.setText(MainActivity.fromHtml(list.get(position)));
            if (k.getBoolean("dzen_noch", false)) {
                ea.textView.setTextColor(ContextCompat.getColor(activity, R.color.colorIcons));
            }
            return convertView;
        }
    }

    private static class ViewHolder {
        TextView_Roboto_Condensed textView;
    }
}
