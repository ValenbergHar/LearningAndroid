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

import java.util.ArrayList;
import java.util.Objects;

import by.carkva_gazeta.malitounik.SettingsActivity;
import by.carkva_gazeta.malitounik.TextView_Roboto_Condensed;

public class Dialog_title_biblioteka extends DialogFragment {

    private ArrayList<String> bookmarks;
    private Dialog_title_biblioteka_Listener mListener;
    private SharedPreferences chin;
    
    static Dialog_title_biblioteka getInstance(ArrayList<String> bookmarks) {
        Dialog_title_biblioteka Instance = new Dialog_title_biblioteka();
        Bundle args = new Bundle();
        args.putStringArrayList("bookmarks", bookmarks);
        Instance.setArguments(args);
        return Instance;
    }

    interface Dialog_title_biblioteka_Listener {
        void onDialogTitle(int page);
        void onDialogTitleString(String page);
    }
    
    @Override
    public void onAttach(@NonNull Context activity) {
        super.onAttach(activity);
        if (activity instanceof Activity) {
            try {
                mListener = (Dialog_title_biblioteka_Listener) activity;
            } catch (ClassCastException e) {
                throw new ClassCastException(activity.toString() + " must implement Dialog_title_biblioteka_Listener");
            }
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bookmarks = Objects.requireNonNull(getArguments()).getStringArrayList("bookmarks");
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        chin = Objects.requireNonNull(getActivity()).getSharedPreferences("biblia", Context.MODE_PRIVATE);
        boolean dzenNoch = chin.getBoolean("dzen_noch", false);
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
        textViewZaglavie.setText(getResources().getString(by.carkva_gazeta.malitounik.R.string.zmest).toUpperCase());
        textViewZaglavie.setTextSize(TypedValue.COMPLEX_UNIT_SP, SettingsActivity.GET_FONT_SIZE_MIN);
        textViewZaglavie.setTypeface(null, Typeface.BOLD);
        textViewZaglavie.setTextColor(ContextCompat.getColor(getActivity(), by.carkva_gazeta.malitounik.R.color.colorIcons));
        linear.addView(textViewZaglavie);
        ListView listViewCompat = new ListView(getActivity());
        listViewCompat.setAdapter(new Title_ListAdaprer(getActivity()));
        linear.addView(listViewCompat);
        builder.setView(linear);

        builder.setPositiveButton(getString(by.carkva_gazeta.malitounik.R.string.CANCEL), (dialog, whichButton) -> dialog.cancel());

        AlertDialog alert = builder.create();
        alert.setOnShowListener(dialog -> {
            Button btnPositive = alert.getButton(Dialog.BUTTON_POSITIVE);
            btnPositive.setTextSize(TypedValue.COMPLEX_UNIT_SP, SettingsActivity.GET_FONT_SIZE_MIN - 2);
        });
        listViewCompat.setOnItemClickListener((adapterView, view, i, l) -> {
            int t1 = bookmarks.get(i).indexOf("<>");
            if (t1 != -1) {
                int t2 = Integer.parseInt(bookmarks.get(i).substring(0, t1));
                mListener.onDialogTitle(t2);
            } else {
                int t2 = bookmarks.get(i).indexOf("<str>");
                mListener.onDialogTitleString(bookmarks.get(i).substring(0, t2));
            }
            alert.cancel();
        });
        return alert;
    }

    class Title_ListAdaprer extends ArrayAdapter<String> {

        private final Activity mContext;

        Title_ListAdaprer(@NonNull Activity context) {
            super(context, by.carkva_gazeta.malitounik.R.layout.simple_list_item_2, by.carkva_gazeta.malitounik.R.id.label, bookmarks);
            mContext = context;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View mView, @NonNull ViewGroup parent) {
            ViewHolder viewHolder;
            if (mView == null) {
                mView = mContext.getLayoutInflater().inflate(by.carkva_gazeta.malitounik.R.layout.simple_list_item_2, parent, false);
                viewHolder = new ViewHolder();
                mView.setTag(viewHolder);
                viewHolder.text = mView.findViewById(by.carkva_gazeta.malitounik.R.id.label);
            } else {
                viewHolder = (ViewHolder) mView.getTag();
            }

            boolean dzenNoch = chin.getBoolean("dzen_noch", false);

            int t1 = bookmarks.get(position).indexOf("<>");
            if (t1 == -1) {
                t1 = bookmarks.get(position).indexOf("<str>");
                viewHolder.text.setText(bookmarks.get(position).substring(t1 + 5));
            } else {
                viewHolder.text.setText(bookmarks.get(position).substring(t1 + 2));
            }

            viewHolder.text.setTextSize(TypedValue.COMPLEX_UNIT_SP, SettingsActivity.GET_FONT_SIZE_MIN);

            if (dzenNoch) {
                viewHolder.text.setBackgroundResource(by.carkva_gazeta.malitounik.R.color.colorbackground_material_dark_ligte);
                viewHolder.text.setTextColor(ContextCompat.getColor(mContext, by.carkva_gazeta.malitounik.R.color.colorIcons));
                viewHolder.text.setCompoundDrawablesWithIntrinsicBounds(by.carkva_gazeta.malitounik.R.drawable.stiker_black, 0, 0, 0);
            }
            return mView;
        }
    }

    private static class ViewHolder {
        TextView_Roboto_Condensed text;
    }
}
