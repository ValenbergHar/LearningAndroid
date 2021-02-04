package by.carkva_gazeta.biblijateka;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

import by.carkva_gazeta.malitounik.SettingsActivity;
import by.carkva_gazeta.malitounik.TextView_Roboto_Condensed;

public class Dialog_file_explorer extends DialogFragment {

    private final ArrayList<String> str = new ArrayList<>();
    private Boolean firstLvl = true;
    private final ArrayList<ArrayList<String>> fileList = new ArrayList<>();
    private File path;
    private String chosenFile;
    private Dialog_file_explorer_Listener mListener;
    private SharedPreferences chin;
    private Boolean sdCard = true, sdCard2 = false;

    interface Dialog_file_explorer_Listener {
        void onDialogFile(File file);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof Activity) {
            try {
                mListener = (Dialog_file_explorer_Listener) context;
            } catch (ClassCastException e) {
                throw new ClassCastException(context.toString() + " must implement Dialog_file_explorer_Listener");
            }
        }
    }

    private void loadFileList() {
        boolean dzenNoch = chin.getBoolean("dzen_noch", false);
        fileList.clear();
        if (path.exists()) {
            FilenameFilter filterDir = (dir, filename) -> {
                File sel = new File(dir, filename);
                return sel.isDirectory() && !sel.isHidden();
            };
            FilenameFilter filterFile = (dir, filename) -> {
                File sel = new File(dir, filename);
                return sel.isFile() && !sel.isHidden() && (sel.getName().toLowerCase().contains(".pdf") || sel.getName().toLowerCase().contains(".epub") || sel.getName().toLowerCase().contains(".fb2"));
            };

            if (!firstLvl) {
                ArrayList<String> temp = new ArrayList<>();
                temp.add("Верх");
                if (dzenNoch)
                    temp.add(String.valueOf(R.drawable.directory_up_black));
                else
                    temp.add(String.valueOf(R.drawable.directory_up));
                fileList.add(temp);
            } else {
                sdCard2 = true;
                sdCard = false;
                ArrayList<String> temp = new ArrayList<>();
                temp.add("Верх");
                if (dzenNoch)
                    temp.add(String.valueOf(R.drawable.directory_up_black));
                else
                    temp.add(String.valueOf(R.drawable.directory_up));
                fileList.add(temp);
            }
            String[] dList = path.list(filterDir);
            Arrays.sort(Objects.requireNonNull(dList));
            for (String aFList : dList) {
                ArrayList<String> temp = new ArrayList<>();
                temp.add(aFList);
                if (dzenNoch)
                    temp.add(String.valueOf(R.drawable.directory_icon_black));
                else
                    temp.add(String.valueOf(R.drawable.directory_icon));
                fileList.add(temp);
            }
            String[] fList = path.list(filterFile);
            Arrays.sort(Objects.requireNonNull(fList));
            for (String aFList : fList) {
                ArrayList<String> temp = new ArrayList<>();
                temp.add(aFList);
                if (aFList.toLowerCase().contains(".pdf")) {
                    if (dzenNoch)
                        temp.add(String.valueOf(R.drawable.file_icon_black));
                    else
                        temp.add(String.valueOf(R.drawable.file_icon));
                } else if (aFList.toLowerCase().contains(".fb2")) {
                    if (dzenNoch)
                        temp.add(String.valueOf(R.drawable.file_fb2_icon_black));
                    else
                        temp.add(String.valueOf(R.drawable.file_fb2_icon));
                } else {
                    if (dzenNoch)
                        temp.add(String.valueOf(R.drawable.file_epub_icon_black));
                    else
                        temp.add(String.valueOf(R.drawable.file_epub_icon));
                }
                fileList.add(temp);
            }
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        chin = Objects.requireNonNull(getActivity()).getSharedPreferences("biblia", Context.MODE_PRIVATE);
        boolean dzenNoch = chin.getBoolean("dzen_noch", false);
        File[] files = ContextCompat.getExternalFilesDirs(getActivity(), null);
        ArrayList<String> tempZ = new ArrayList<>();
        tempZ.add("Унутраная памяць");
        if (dzenNoch)
            tempZ.add(String.valueOf(R.drawable.directory_icon_black));
        else
            tempZ.add(String.valueOf(R.drawable.directory_icon));
        fileList.add(tempZ);
        if (files.length > 1) {
            tempZ = new ArrayList<>();
            tempZ.add("Карта SD");
            if (dzenNoch)
                tempZ.add(String.valueOf(R.drawable.directory_icon_black));
            else
                tempZ.add(String.valueOf(R.drawable.directory_icon));
            fileList.add(tempZ);
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LinearLayout linear = new LinearLayout(getActivity());
        linear.setOrientation(LinearLayout.VERTICAL);
        TextView_Roboto_Condensed textViewZaglavie = new TextView_Roboto_Condensed(getActivity());
        if (dzenNoch)
            textViewZaglavie.setBackgroundColor(ContextCompat.getColor(getActivity(), by.carkva_gazeta.malitounik.R.color.colorPrimary_black));
        else textViewZaglavie.setBackgroundColor(ContextCompat.getColor(getActivity(), by.carkva_gazeta.malitounik.R.color.colorPrimary));
        float density = getResources().getDisplayMetrics().density;
        int realpadding = (int) (10 * density);
        textViewZaglavie.setPadding(realpadding, realpadding, realpadding, realpadding);
        textViewZaglavie.setText("ВЫБЕРЫЦЕ ФАЙЛ");
        textViewZaglavie.setTextSize(TypedValue.COMPLEX_UNIT_SP, SettingsActivity.GET_FONT_SIZE_MIN);
        textViewZaglavie.setTypeface(null, Typeface.BOLD);
        textViewZaglavie.setTextColor(ContextCompat.getColor(getActivity(), by.carkva_gazeta.malitounik.R.color.colorIcons));
        linear.addView(textViewZaglavie);
        ListView listViewCompat = new ListView(getActivity());
        titleListAdaprer listAdaprer = new titleListAdaprer(getActivity());
        listViewCompat.setAdapter(listAdaprer);
        linear.addView(listViewCompat);
        builder.setView(linear);

        builder.setPositiveButton(getString(by.carkva_gazeta.malitounik.R.string.CANCEL), (dialog, whichButton) -> dialog.cancel());

        AlertDialog alert = builder.create();
        alert.setOnShowListener(dialog -> {
            Button btnPositive = alert.getButton(Dialog.BUTTON_POSITIVE);
            btnPositive.setTextSize(TypedValue.COMPLEX_UNIT_SP, SettingsActivity.GET_FONT_SIZE_MIN - 2);
        });
        listViewCompat.setOnItemClickListener((adapterView, view, i, l) -> {
            if (sdCard) {
                String dir = ContextCompat.getExternalFilesDirs(getActivity(), null)[i].getAbsolutePath();
                int t1 = dir.indexOf("/Android/data/");
                if (t1 != -1)
                    dir = dir.substring(0, t1);
                path = new File(dir);
                loadFileList();
                listAdaprer.notifyDataSetChanged();
            } else if (sdCard2 && i == 0) {
                fileList.clear();
                sdCard2 = false;
                sdCard = true;
                ArrayList<String> temp = new ArrayList<>();
                temp.add("Унутраная памяць");
                if (dzenNoch)
                    temp.add(String.valueOf(R.drawable.directory_icon_black));
                else
                    temp.add(String.valueOf(R.drawable.directory_icon));
                fileList.add(temp);
                if (files.length > 1) {
                    temp = new ArrayList<>();
                    temp.add("Карта SD");
                    if (dzenNoch)
                        temp.add(String.valueOf(R.drawable.directory_icon_black));
                    else
                        temp.add(String.valueOf(R.drawable.directory_icon));
                    fileList.add(temp);
                }
                listAdaprer.notifyDataSetChanged();
            } else {
                sdCard2 = false;
                chosenFile = fileList.get(i).get(0);
                File sel = new File(path + "/" + chosenFile);
                if (sel.isDirectory()) {
                    firstLvl = false;
                    str.add(chosenFile);
                    path = new File(sel + "");
                    loadFileList();
                    listAdaprer.notifyDataSetChanged();
                } else if (chosenFile.equals("Верх") && !sel.exists()) {
                    String s = str.remove(str.size() - 1);

                    path = new File(path.toString().substring(0, path.toString().lastIndexOf(s)));
                    if (str.isEmpty()) {
                        firstLvl = true;
                    }
                    loadFileList();

                    listAdaprer.notifyDataSetChanged();

                } else {
                    mListener.onDialogFile(sel);
                    alert.cancel();
                }
            }
        });
        return alert;
    }

    class titleListAdaprer extends ArrayAdapter<ArrayList<String>> {

        private final Activity mContext;

        titleListAdaprer(@NonNull Activity context) {
            super(context, R.layout.biblijateka_simple_list_item, fileList);
            mContext = context;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View mView, @NonNull ViewGroup parent) {
            ViewHolder viewHolder;
            if (mView == null) {
                mView = mContext.getLayoutInflater().inflate(R.layout.biblijateka_simple_list_item, parent, false);
                viewHolder = new ViewHolder();
                mView.setTag(viewHolder);
                viewHolder.text = mView.findViewById(R.id.label);
            } else {
                viewHolder = (ViewHolder) mView.getTag();
            }

            boolean dzenNoch = chin.getBoolean("dzen_noch", false);

            viewHolder.text.setText(fileList.get(position).get(0));
            viewHolder.text.setTextSize(TypedValue.COMPLEX_UNIT_SP, SettingsActivity.GET_FONT_SIZE_MIN);
            viewHolder.text.setCompoundDrawablesWithIntrinsicBounds(Integer.parseInt(fileList.get(position).get(1)), 0, 0, 0);

            if (dzenNoch) {
                viewHolder.text.setBackgroundResource(by.carkva_gazeta.malitounik.R.color.colorbackground_material_dark_ligte);
                viewHolder.text.setTextColor(ContextCompat.getColor(mContext, by.carkva_gazeta.malitounik.R.color.colorIcons));
                viewHolder.text.setCompoundDrawablesWithIntrinsicBounds(Integer.parseInt(fileList.get(position).get(1)), 0, 0, 0);
            }
            return mView;
        }
    }

    private static class ViewHolder {
        TextView_Roboto_Condensed text;
    }
}
