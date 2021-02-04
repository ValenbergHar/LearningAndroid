package by.carkva_gazeta.malitounik;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.SystemClock;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.AbsoluteSizeSpan;
import android.util.TypedValue;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.PopupMenu;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.ListFragment;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;

/**
 * Created by oleg on 13.6.16
 */
@SuppressWarnings({"ResultOfMethodCallIgnored"})
public class Menu_natatki extends ListFragment {

    private MyNatatkiAdapter adapter;
    private final ArrayList<my_natatki_files> my_natatki_files = new ArrayList<>();
    private long mLastClickTime = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        // Скрываем клавиатуру
        Objects.requireNonNull(getActivity()).getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        File dir = new File(Objects.requireNonNull(getActivity()).getFilesDir() + "/Malitva");
        File[] dirContents = dir.listFiles();
        for (File f : Objects.requireNonNull(dirContents)) {
            StringBuilder builder = new StringBuilder();
            try {
                FileReader inputStream = new FileReader(f);
                BufferedReader reader = new BufferedReader(inputStream);
                String line;
                while ((line = reader.readLine()) != null) {
                    builder.append(line).append("\n");
                }
                inputStream.close();
            } catch (Throwable ignored) {
            }
            String[] res = builder.toString().split("<MEMA></MEMA>");
            long lRTE = 1;
            if (res[1].contains("<RTE></RTE>")) {
                int start = res[1].indexOf("<RTE></RTE>");
                int end = res[1].lastIndexOf("\n");
                lRTE = Long.parseLong(res[1].substring(start + 11, end));
            }
            if (lRTE <= 1) {
                lRTE = f.lastModified();
            }
            my_natatki_files.add(new my_natatki_files(lRTE, res[0], f.getAbsoluteFile()));

        }
        Collections.sort(my_natatki_files, new my_natatki_files_sort(getActivity()));
        adapter = new MyNatatkiAdapter(getActivity());
        setListAdapter(adapter);
        getListView().setVerticalScrollBarEnabled(false);

        getListView().setOnItemLongClickListener((parent, view, position, id) -> {
            Dialog_context_menu context_menu = Dialog_context_menu.getInstance(position, my_natatki_files.get(position).name);
            context_menu.show(Objects.requireNonNull(getFragmentManager()), "context_menu");
            return true;
        });
    }

    void sortAlfavit() {
        Collections.sort(my_natatki_files, new my_natatki_files_sort(Objects.requireNonNull(getActivity())));
        adapter.notifyDataSetChanged();
    }

    void file_delite(int position) {
        my_natatki_files f = my_natatki_files.get(position);
        my_natatki_files.remove(position);
        f.file.delete();
        Collections.sort(my_natatki_files, new my_natatki_files_sort(Objects.requireNonNull(getActivity())));
        adapter.notifyDataSetChanged();
    }

    void onDialogEditClick(int position) {
        if (MainActivity.checkModule_resources(getActivity())) {
            try {
                my_natatki_files f = my_natatki_files.get(position);
                Intent intent = new Intent(getActivity(), Class.forName("by.carkva_gazeta.resources.My_natatki_add"));
                intent.putExtra("filename", f.file.getName());
                intent.putExtra("redak", true);
                startActivityForResult(intent, 104);
            } catch (ClassNotFoundException ignored) {
            }
        } else {
            Dialog_install_Dadatak dadatak = new Dialog_install_Dadatak();
            dadatak.show(Objects.requireNonNull(getFragmentManager()), "dadatak");
        }
    }

    void onDialogDeliteClick(int position, String name) {
        Dialog_delite dd = Dialog_delite.getInstance(position, "", "нататку", name);
        dd.show(Objects.requireNonNull(getFragmentManager()), "dialog_delite");
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        boolean save = false;
        if (data != null) {
            save = data.getBooleanExtra("savefile", false);
        }
        if (requestCode == 103 || requestCode == 104) {
            if (save) {
                my_natatki_files.clear();
                File dir = new File(Objects.requireNonNull(getActivity()).getFilesDir() + "/Malitva");
                File[] dirContents = dir.listFiles();
                for (File f : Objects.requireNonNull(dirContents)) {
                    StringBuilder builder = new StringBuilder();
                    try {
                        FileReader inputStream = new FileReader(f);
                        BufferedReader reader = new BufferedReader(inputStream);
                        String line;
                        while ((line = reader.readLine()) != null) {
                            builder.append(line).append("\n");
                        }
                        inputStream.close();
                    } catch (Throwable ignored) {
                    }
                    String[] res = builder.toString().split("<MEMA></MEMA>");
                    long lRTE = 1;
                    if (res[1].contains("<RTE></RTE>")) {
                        int start = res[1].indexOf("<RTE></RTE>");
                        int end = res[1].lastIndexOf("\n");
                        lRTE = Long.parseLong(res[1].substring(start + 11, end));
                        res[1] = res[1].substring(0, start);
                    }
                    if (lRTE <= 1) {
                        lRTE = f.lastModified();
                    }
                    my_natatki_files.add(new my_natatki_files(lRTE, res[0], f.getAbsoluteFile()));
                }
                Collections.sort(my_natatki_files, new my_natatki_files_sort(getActivity()));
                adapter.notifyDataSetChanged();
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (SystemClock.elapsedRealtime() - mLastClickTime < 1000){
            return true;
        }
        mLastClickTime = SystemClock.elapsedRealtime();

        int id = item.getItemId();

        if (id == R.id.action_add) {
            if (MainActivity.checkModule_resources(getActivity())) {
                try {
                    Intent intent = new Intent(getActivity(), Class.forName("by.carkva_gazeta.resources.My_natatki_add"));
                    intent.putExtra("redak", false);
                    intent.putExtra("filename", "");
                    startActivityForResult(intent, 103);
                } catch (ClassNotFoundException ignored) {
                }
            } else {
                Dialog_install_Dadatak dadatak = new Dialog_install_Dadatak();
                dadatak.show(Objects.requireNonNull(getFragmentManager()), "dadatak");
            }
        }
        return super.onOptionsItemSelected(item);
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
                my_natatki_files f = my_natatki_files.get(position);
                Intent intent = new Intent(getActivity(), Class.forName("by.carkva_gazeta.resources.My_natatki_view"));
                intent.putExtra("filename", f.file.getName());
                startActivity(intent);
            } catch (ClassNotFoundException ignored) {
            }
        } else {
            Dialog_install_Dadatak dadatak = new Dialog_install_Dadatak();
            dadatak.show(Objects.requireNonNull(getFragmentManager()), "dadatak");
        }
    }

    class MyNatatkiAdapter extends ArrayAdapter<my_natatki_files> {

        private final SharedPreferences k;
        private final Activity activity;

        MyNatatkiAdapter(Activity activity) {
            super(activity, R.layout.simple_list_item_3, R.id.label, my_natatki_files);
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

            viewHolder.button_popup.setOnClickListener((v -> showPopupMenu(viewHolder.button_popup, position, my_natatki_files.get(position).name)));

            viewHolder.text.setText(my_natatki_files.get(position).name);
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
                    case R.id.menu_redoktor:
                        onDialogEditClick(position);
                        return true;
                    case R.id.menu_remove:
                        onDialogDeliteClick(position, name);
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
