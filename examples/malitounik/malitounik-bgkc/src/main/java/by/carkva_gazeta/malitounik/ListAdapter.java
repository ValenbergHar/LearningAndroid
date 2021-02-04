package by.carkva_gazeta.malitounik;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Created by oleg on 14.11.16
 */


class ListAdapter extends ArrayAdapter<Integer> {

    private final Activity mContext;
    private final ArrayList<Integer> itemsL;
    private final SharedPreferences k;
    private final int fontBiblia;
    private final GregorianCalendar gc;

    ListAdapter(Activity context, ArrayList<Integer> list) {
        super(context, R.layout.simple_list_item_1, list);
        mContext = context;
        itemsL = list;
        k = context.getSharedPreferences("biblia", Context.MODE_PRIVATE);
        fontBiblia = k.getInt("font_malitounik", SettingsActivity.GET_DEFAULT_FONT_SIZE);
        gc = (GregorianCalendar) Calendar.getInstance();
    }

    @NonNull
    @Override
    public View getView(int position, View mView, @NonNull ViewGroup parent) {
        ViewHolder viewHolder;
        if (mView == null) {
            mView = mContext.getLayoutInflater().inflate(R.layout.simple_list_item_1, parent, false);
            viewHolder = new ViewHolder();
            mView.setTag(viewHolder);
            viewHolder.text = mView.findViewById(R.id.text1);
        } else {
            viewHolder = (ViewHolder) mView.getTag();
        }

        boolean dzenNoch = k.getBoolean("dzen_noch", false);

        if (gc.get(Calendar.YEAR) == itemsL.get(position))
            viewHolder.text.setTypeface(null, Typeface.BOLD);
        else
            viewHolder.text.setTypeface(null, Typeface.NORMAL);
        viewHolder.text.setText(String.valueOf(itemsL.get(position)));

        viewHolder.text.setTextSize(TypedValue.COMPLEX_UNIT_SP, fontBiblia);
        if (dzenNoch) {
            viewHolder.text.setBackgroundResource(R.color.colorbackground_material_dark_ligte);
            viewHolder.text.setTextColor(ContextCompat.getColor(mContext, R.color.colorIcons));
        }
        return mView;
    }

    @Override
    public View getDropDownView(int position, View convertView, @NonNull ViewGroup parent) {
        View v = super.getDropDownView(position, convertView, parent);
        boolean dzenNoch = k.getBoolean("dzen_noch", false);
        TextView_Roboto_Condensed text = v.findViewById(R.id.text1);
        text.setTextSize(TypedValue.COMPLEX_UNIT_SP, fontBiblia);
        if (gc.get(Calendar.YEAR) == itemsL.get(position))
            text.setTypeface(null, Typeface.BOLD);
        else
            text.setTypeface(null, Typeface.NORMAL);
        text.setText(String.valueOf(itemsL.get(position)));
        if (dzenNoch) {
            text.setBackgroundResource(R.color.colorbackground_material_dark_ligte);
            text.setTextColor(ContextCompat.getColor(mContext, R.color.colorIcons));
        } else {
            text.setBackgroundResource(R.color.colorIcons);
        }
        return v;
    }

    private static class ViewHolder {
        TextView_Roboto_Condensed text;
    }
}
