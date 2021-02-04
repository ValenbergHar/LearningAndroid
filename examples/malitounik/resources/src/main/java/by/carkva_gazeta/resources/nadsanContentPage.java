package by.carkva_gazeta.resources;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.ListFragment;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

import by.carkva_gazeta.malitounik.MainActivity;
import by.carkva_gazeta.malitounik.SettingsActivity;
import by.carkva_gazeta.malitounik.TextView_Roboto_Condensed;

public class nadsanContentPage extends ListFragment implements ListView.OnItemLongClickListener {
    
    private int page;
    private int pazicia;
    private ListPosition listPosition;
    private ArrayList<String> bible;

    public interface ListPosition {
        void getListPosition(int position);
    }

    public static nadsanContentPage newInstance(int page, int pos) {
        nadsanContentPage fragmentFirst = new nadsanContentPage();
        Bundle args = new Bundle();
        args.putInt("page", page);
        args.putInt("pos", pos);
        fragmentFirst.setArguments(args);
        return fragmentFirst;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof Activity) {
            listPosition = (ListPosition) context;
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        page = Objects.requireNonNull(getArguments()).getInt("page");
        pazicia = getArguments().getInt("pos");
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        ClipboardManager clipboard = (ClipboardManager) Objects.requireNonNull(getActivity()).getSystemService(Context.CLIPBOARD_SERVICE);
        String copy = MainActivity.fromHtml(String.valueOf(parent.getAdapter().getItem(position))).toString();
        ClipData clip = ClipData.newPlainText("", MainActivity.fromHtml(copy));//MaranAta_Global_List.getBible().get(MaranAta_Global_List.getListPosition())).toString()
        Objects.requireNonNull(clipboard).setPrimaryClip(clip);
        LinearLayout layout = new LinearLayout(getActivity());
        SharedPreferences chin = getActivity().getSharedPreferences("biblia", Context.MODE_PRIVATE);
        boolean dzenNoch = chin.getBoolean("dzen_noch", false);
        if (dzenNoch) layout.setBackgroundResource(by.carkva_gazeta.malitounik.R.color.colorPrimary_black);
        else layout.setBackgroundResource(by.carkva_gazeta.malitounik.R.color.colorPrimary);
        float density = getResources().getDisplayMetrics().density;
        int realpadding = (int) (10 * density);
        TextView_Roboto_Condensed toast = new TextView_Roboto_Condensed(getActivity());
        toast.setTextColor(ContextCompat.getColor(getActivity(), by.carkva_gazeta.malitounik.R.color.colorIcons));
        toast.setPadding(realpadding, realpadding, realpadding, realpadding);
        toast.setText(getString(by.carkva_gazeta.malitounik.R.string.copynadsan, copy));
        toast.setTextSize(TypedValue.COMPLEX_UNIT_SP, SettingsActivity.GET_FONT_SIZE_MIN - 2);
        layout.addView(toast);
        Toast mes = new Toast(getActivity());
        mes.setDuration(Toast.LENGTH_LONG);
        mes.setView(layout);
        mes.show();
        return true;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        getListView().setSelection(nadsanContentActivity.fierstPosition);
        getListView().setOnItemLongClickListener(this);
        getListView().setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if (listPosition != null) {
                    listPosition.getListPosition(view.getFirstVisiblePosition());
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
            }
        });
        bible = new ArrayList<>();
        InputStream inputStream = getResources().openRawResource(R.raw.nadsan_psaltyr);
        try {
            InputStreamReader isr = new InputStreamReader(inputStream);
            BufferedReader reader = new BufferedReader(isr);
            String line;
            StringBuilder builder = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                /*if (line.contains("//")) {
                    int t1 = line.indexOf("//");
                    line = line.substring(0, t1).trim();
                    if (!line.equals(""))
                        builder.append(line).append("\n");
                    continue;
                }*/
                builder.append(line).append("\n");
            }
            inputStream.close();
            String[] split = builder.toString().split("===");
            String[] bibleline = split[page + 1].split("\n");
            bible.addAll(Arrays.asList(bibleline).subList(1, bibleline.length));
        } catch (Throwable ignored) {
        }
        ListAdaprer adapter = new ListAdaprer(Objects.requireNonNull(getActivity()));
        getListView().setDivider(null);
        setListAdapter(adapter);
        getListView().setSelection(pazicia);
        getListView().setVerticalScrollBarEnabled(false);
        SharedPreferences k = getActivity().getSharedPreferences("biblia", Context.MODE_PRIVATE);
        if (k.getBoolean("dzen_noch", false))
            getListView().setBackgroundResource(by.carkva_gazeta.malitounik.R.color.colorbackground_material_dark);
        //float scale = getResources().getDisplayMetrics().density;
        //int dpAsPixels = (int) (scale * 10f);
        //getListView().setPadding(dpAsPixels, 0, dpAsPixels, dpAsPixels);
    }

    class ListAdaprer extends ArrayAdapter<String> {

        private final Activity mContext;
        private final SharedPreferences k;

        ListAdaprer(@NonNull Activity context) {
            super(context, by.carkva_gazeta.malitounik.R.layout.simple_list_item_bible, bible);
            mContext = context;
            k = context.getSharedPreferences("biblia", Context.MODE_PRIVATE);
        }

        @Override
        public boolean isEnabled(int position) {
            return false;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View mView, @NonNull ViewGroup parent) {
            ViewHolder viewHolder;
            if (mView == null) {
                mView = mContext.getLayoutInflater().inflate(by.carkva_gazeta.malitounik.R.layout.simple_list_item_bible, parent, false);
                viewHolder = new ViewHolder();
                mView.setTag(viewHolder);
                viewHolder.text = mView.findViewById(by.carkva_gazeta.malitounik.R.id.label);
                viewHolder.text.setTextSize(TypedValue.COMPLEX_UNIT_SP, k.getInt("font_malitounik", 18));
            } else {
                viewHolder = (ViewHolder) mView.getTag();
            }

            boolean dzenNoch = k.getBoolean("dzen_noch", false);
            /*String br = "";
            if (!bible.get(position).equals(""))
                br = "<br>";*/

            viewHolder.text.setText(MainActivity.fromHtml(bible.get(position)));// + br

            if (dzenNoch) {
                viewHolder.text.setBackgroundResource(by.carkva_gazeta.malitounik.R.color.colorbackground_material_dark);
                viewHolder.text.setTextColor(ContextCompat.getColor(mContext, by.carkva_gazeta.malitounik.R.color.colorIcons));
            }
            return mView;
        }
    }

    private static class ViewHolder {
        TextView_Roboto_Condensed text;
    }
}



